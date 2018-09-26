package org.androidtown.foodtruckgram.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidtown.foodtruckgram.Adapter.CustomerMyOrderAdapter;
import org.androidtown.foodtruckgram.Info.MenuInfo;
import org.androidtown.foodtruckgram.Info.OrderInfo;
import org.androidtown.foodtruckgram.Info.UserInfo;
import org.androidtown.foodtruckgram.R;
import org.androidtown.foodtruckgram.Server.HttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerOrderIdentifyDialog extends AppCompatActivity {

    Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_identify_dialog);

        Intent intent = getIntent();
        params = (Map<String, String>) intent.getSerializableExtra("params");

        TextView orderIdentifyMenuName = (TextView)findViewById(R.id.orderIdentifyMenuName);
        orderIdentifyMenuName.setText("메뉴 : "+params.get("menuName"));
        TextView orderIdentifyMenuPrice = (TextView)findViewById(R.id.orderIdentifyMenuPrice);
        orderIdentifyMenuPrice.setText("가격 : "+params.get("price"));
        TextView orderCancelBtn = (TextView)findViewById(R.id.orderCancelBtn);
        orderCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);

                finish();
            }
        });
        TextView orderIdentifyBtn = (TextView)findViewById(R.id.orderIdentifyBtn);
        orderIdentifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderMenuDB orderMenuDB = new OrderMenuDB();
                orderMenuDB.execute(params);
            }
        });

    }

    private String serverURL = "http://" + HttpClient.ipAdress + HttpClient.serverPort + HttpClient.urlBase + "/c/insertOrder";
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

            OrderDB orderDB = new OrderDB();
            Map<String, String> param = new HashMap<>();
            param.put("userId", UserInfo.getUserInfo().getUserId());
            orderDB.execute(param);
        }
    }


    String serverURL_myorder = "http://" + HttpClient.ipAdress + HttpClient.serverPort + HttpClient.urlBase + "/c/getOrderListByUserId";

    class OrderDB extends AsyncTask<Map<String, String>, Integer, String> {

        @Override
        protected String doInBackground(Map<String, String>... maps) {

            HttpClient.Builder http = new HttpClient.Builder("POST", serverURL_myorder);
            http.addAllParameters(maps[0]);

            HttpClient post = http.create();
            post.request();

            int statusCode = post.getHttpStatusCode();

            //Log.i(TAG, "응답코드"+statusCode);

            String body = post.getBody();

            //Log.i(TAG, "body : "+body);

            return body;

        }


        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            Gson gson = new Gson();

            ArrayList<OrderInfo> infos = gson.fromJson(aVoid, new TypeToken<List<OrderInfo>>() {
            }.getType());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("orderList",infos);
            setResult(RESULT_OK, resultIntent);

            Log.i("Order","OrderList"+infos.size());

            finish();
        }
    }

}
