package com.example.zheweizhang.yunbuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class Withdraw2 extends AppCompatActivity {
    String syunbi,stsuid,stspw;
    TextInputEditText edYunbi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw2);

        Intent intent = this.getIntent();
        stsuid = intent.getStringExtra("UID");
        stspw = intent.getStringExtra("SPW");
    }

    public void subnit(View view){
        edYunbi = findViewById(R.id.tyunbi);
        String syunbi = edYunbi.getText().toString();

        Intent intent = new Intent(this,Withdraw3.class);
        intent.putExtra("yunbi",syunbi);
        intent.putExtra("UID",stsuid);
        intent.putExtra("SPW",stspw);
        startActivity(intent);
    }


}
