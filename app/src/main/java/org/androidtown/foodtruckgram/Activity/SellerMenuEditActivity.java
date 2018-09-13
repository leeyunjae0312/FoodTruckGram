package org.androidtown.foodtruckgram.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.androidtown.foodtruckgram.Adapter.SellerMenuListAdapter;
import org.androidtown.foodtruckgram.Info.FoodTruckInfo;
import org.androidtown.foodtruckgram.Info.MenuInfo;
import org.androidtown.foodtruckgram.R;
import org.androidtown.foodtruckgram.Server.HttpClient;

import java.util.HashMap;
import java.util.Map;

public class SellerMenuEditActivity extends AppCompatActivity {

    FoodTruckInfo foodTruckInfo;
    String menuName,menuPrice,menuIntroduce,menuImage;
    EditText menuEdit_name,menuEdit_price,menuEdit_introduce;
    String serverURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_menu_edit);

        Intent intent = getIntent();
        foodTruckInfo = (FoodTruckInfo) intent.getSerializableExtra("foodTruckInfo");
        menuName = intent.getStringExtra("menuName");
        menuPrice = intent.getStringExtra("menuPrice");
        menuIntroduce = intent.getStringExtra("menuIntroduce");
        menuImage = intent.getStringExtra("menuImage");
        serverURL = intent.getStringExtra("serverURL");

        menuEdit_name = findViewById(R.id.menuEdit_name);
        if(menuName!=null) {
            //메뉴 수정시 이름은 수정 불가
            menuEdit_name.setEnabled(false);
            menuEdit_name.setBackground(null);
        }
        menuEdit_name.setText(menuName);

        menuEdit_price = findViewById(R.id.menuEdit_price);
        menuEdit_price.setText(menuPrice);

        menuEdit_introduce = findViewById(R.id.menuEdit_introduce);
        menuEdit_introduce.setText(menuIntroduce);

        TextView menuImageEditBtn = (TextView)findViewById(R.id.menuEdit_image);
        menuImageEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Image Change

            }
        });

        final TextView menuEditBtn = (TextView)findViewById(R.id.menuEditBtn);
        menuEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String menuName = menuEdit_name.getText().toString();
                String menuPrice = menuEdit_price.getText().toString();
                String menuIntroduce = menuEdit_introduce.getText().toString();

                if(menuName == null || menuPrice==null)
                    Toast.makeText(getApplicationContext(),"메뉴 이름과 가격은 필수 입력입니다.",Toast.LENGTH_SHORT).show();
                else {
                    //Edit Completion
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("menuName",menuName);
                    params.put("menuImage","menuImageURL");
                    params.put("menuPrice",menuPrice);
                    params.put("menuIntroduce",menuIntroduce);
                    params.put("storeName",foodTruckInfo.getStoreName());

                    MenuUpdateAndInsetDB menuUpdateAndInsetDB = new MenuUpdateAndInsetDB();
                    menuUpdateAndInsetDB.execute(params);
                }

            }
        });

    }

    class MenuUpdateAndInsetDB extends AsyncTask<Map<String, String>, Integer, String> {

        @Override
        protected String doInBackground(Map<String, String>...maps) {
            HttpClient.Builder http = new HttpClient.Builder("POST",serverURL);
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

            MenuInfo menuInfo = new MenuInfo(menuEdit_name.getText().toString(),menuEdit_price.getText().toString(),menuImage,menuEdit_introduce.getText().toString());
            if(foodTruckInfo.editMenu(menuInfo)==-1)
                foodTruckInfo.getMenuList().add(menuInfo);
            Toast.makeText(getApplicationContext(),"메뉴 수정/추가 완료",Toast.LENGTH_SHORT).show();

            Log.i("Edit","메뉴 수정/추가 완료");
            Log.i("Edit","foodTruckInfo.getMenuList().size()"+foodTruckInfo.getMenuList().size());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("foodTruckInfo",foodTruckInfo);
            setResult(RESULT_OK,resultIntent);



            finish();
        }
    }

}
