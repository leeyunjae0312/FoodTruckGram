package org.androidtown.foodtruckgram.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.Info.MenuInfo;
import org.androidtown.foodtruckgram.Info.UserInfo;
import org.androidtown.foodtruckgram.R;
import org.androidtown.foodtruckgram.Server.HttpClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 이예지 on 2018-09-19.
 */

public class CustomerOrderMenuAdapter extends RecyclerView.Adapter<CustomerOrderMenuAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView menuName, menuPrice,orderBtn;
        public ImageView menuImage;
        private CustomerOrderMenuAdapter adapter;
        private Context context;

        public ViewHolder(final Context context, View itemView, final CustomerOrderMenuAdapter adapter) {
            super(itemView);
            this.context = context;
            this.adapter = adapter;

            menuName = (TextView)itemView.findViewById(R.id.customerOrderMenuName);
            menuPrice = (TextView)itemView.findViewById(R.id.customerOrderMenuPrice);
            orderBtn = (TextView)itemView.findViewById(R.id.customerOrderMenuBtn);
            menuImage = (ImageView)itemView.findViewById(R.id.customerOrderMenuImg);

        }
        @Override
        public void onClick(View v) {

        }
    }

    FoodTruckInfo foodTruckInfo;
    ArrayList<MenuInfo> menuInfos;

    public CustomerOrderMenuAdapter(FoodTruckInfo foodTruckInfo){
        this.foodTruckInfo = foodTruckInfo;
        menuInfos = foodTruckInfo.getMenuList();
    }

    @Override
    public CustomerOrderMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_customer_order_menu, parent, false);

        // Return a new holder instance
        CustomerOrderMenuAdapter.ViewHolder viewHolder = new CustomerOrderMenuAdapter.ViewHolder(context, contactView, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomerOrderMenuAdapter.ViewHolder holder, final int position) {
        holder.menuName.setText(menuInfos.get(position).getMenuName());
        holder.menuPrice.setText(menuInfos.get(position).getMenuPrice());
        //holder.menuImage.setImageResource(menuInfos.get(position).getMenuImage());
        String base64 = null;
        if(menuInfos.size() > 0) {
            base64 = menuInfos.get(position).getMenuImage();
        }

        if(base64 != null && base64 != "") {
            byte[] decodedString = Base64.decode(base64, Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.menuImage.setImageBitmap(bitmap);
        }
        else {
            holder.menuImage.setImageResource(R.drawable.burger);
        }

        holder.orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //주문 내용 확인 및 서버에 전달(Dialog)
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.context);
                builder.setIcon(R.drawable.foodtruckgram);
                builder.setTitle("주문 확인");
                builder.setMessage("\n주문 내역 : "+menuInfos.get(position).getMenuName()+" : "+menuInfos.get(position).getMenuPrice());
                builder.setPositiveButton("완료",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(holder.context,"주문이 완료되었습니다.",Toast.LENGTH_LONG).show();

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("userId", UserInfo.getUserInfo().getUserId());
                                params.put("tel", UserInfo.getUserInfo().getTel());
                                Date date = new Date(System.currentTimeMillis());
                                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
                                String new_date = date_format.format(date);
                                params.put("date",new_date);
                                params.put("storeName", foodTruckInfo.getStoreName());
                                Log.i("Order",foodTruckInfo.getStoreName());
                                params.put("menuName", menuInfos.get(position).getMenuName());
                                params.put("price", menuInfos.get(position).getMenuPrice());

                                OrderMenuDB orderMenuDB = new OrderMenuDB();
                                orderMenuDB.execute(params);
                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(holder.context,"주문이 취소되었습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(menuInfos!=null)
            return menuInfos.size();
        else {
            Log.i("Order",foodTruckInfo.getStoreName()+"메뉴 없음");
            return 0;
        }
    }

    private String serverURL = "http://" + HttpClient.ipAdress + ":8080" + HttpClient.urlBase + "/c/insertOrder";

    class OrderMenuDB extends AsyncTask<Map<String, String>, Integer, String> {

        @Override
        protected String doInBackground(Map<String, String>...maps) {

            HttpClient.Builder http = new HttpClient.Builder("POST", serverURL);
            http.addAllParameters(maps[0]);

            HttpClient post = http.create();
            post.request();

            int statusCode = post.getHttpStatusCode();

            Log.i("yunjae", "응답코드"+statusCode);

            String body = post.getBody();

            Log.i("yunjae", "body : "+body);

            return body;

        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            Log.i("yunjae", aVoid);

            Gson gson = new Gson();


        }
    }
}
