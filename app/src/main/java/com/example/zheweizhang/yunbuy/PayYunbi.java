package com.example.zheweizhang.yunbuy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class PayYunbi extends AppCompatActivity {

    String dr,stsuid,stspw,suid,spw;;
    String syunbi;
    TextView t_suid,t_spw,scan_content,t_productname,t_yunbi,t_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_yunbi);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1);
        }
        t_suid = findViewById(R.id.tsuid);
        t_spw = findViewById(R.id.tspw);
        t_productname = findViewById(R.id.productname);
        t_yunbi = findViewById(R.id.yunbi);
        t_detail = findViewById(R.id.detail);

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
            Toast.makeText(PayYunbi.this,"掃描成功",Toast.LENGTH_SHORT).show();
            Log.d("HJSON",scanContent);
            parseJSON(scanContent);
        }
        else {
            Toast.makeText(PayYunbi.this,"掃描失敗",Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void parseJSON(String scanContent) {
        try {
                JSONObject jsonObject = new JSONObject(scanContent);
                String jproductname = jsonObject.getString("ProductName");
                String jyunbi = jsonObject.getString("Yunbi");
                String jdetail = jsonObject.getString("Detail");

                t_productname.setText(jproductname);
                t_productname.getText();

                t_yunbi.setText(jyunbi);
                t_yunbi.getText();

                syunbi = jyunbi;

                t_detail.setText(jdetail);
                t_detail.getText();


            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
    public void next(View view){
        Intent intent = new Intent(this, Security.class);
        intent.putExtra("yunbi",syunbi);
        intent.putExtra("UID",stsuid);
        intent.putExtra("SPW",stspw);
        startActivity(intent);
    }

}
