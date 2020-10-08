package com.example.zheweizhang.yunbuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Successful extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successful);
    }
    public void home(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
