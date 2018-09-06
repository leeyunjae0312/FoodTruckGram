package org.androidtown.foodtruckgram.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.foodtruckgram.R;

public class LoginActivity extends AppCompatActivity {

    private String userid, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void onButtonLogin(View v){
        userid = ((EditText) findViewById(R.id.etId)).getText().toString();
        password = ((EditText) findViewById(R.id.etPassword)).getText().toString();

        //로그인 확인 작업
        //Seller페이지 Customer페이지 구분해서 넘기기

        Intent intent = new Intent(getApplicationContext(),CustomerHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
