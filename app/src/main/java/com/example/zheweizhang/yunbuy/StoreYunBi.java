package com.example.zheweizhang.yunbuy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class StoreYunBi extends AppCompatActivity {

    String syunbi,stsuid,stspw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_yun_bi);

        Intent intent = this.getIntent();
        stsuid = intent.getStringExtra("UID");
        stspw = intent.getStringExtra("SPW");
    }

    public void ScanQRcode (View view){
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            Toast.makeText(StoreYunBi.this,"掃描成功",Toast.LENGTH_SHORT).show();
            Log.d("HJSON",scanContent);
            parseJSON(scanContent);
        }
        else {
            Toast.makeText(StoreYunBi.this,"掃描失敗",Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void parseJSON(String scanContent) {
        try {
            JSONObject jsonObject = new JSONObject(scanContent);
            String jyunbi = jsonObject.getString("Yunbi");

            syunbi = jyunbi;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void next (View view){
        Intent intent = new Intent(this, StoredYunBI.class);
        intent.putExtra("yunbi",syunbi);
        intent.putExtra("UID",stsuid);
        intent.putExtra("SPW",stspw);
        startActivity(intent);
    }
}
