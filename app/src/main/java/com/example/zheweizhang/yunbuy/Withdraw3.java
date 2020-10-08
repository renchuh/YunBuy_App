package com.example.zheweizhang.yunbuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class Withdraw3 extends AppCompatActivity {
    String syunbi,stsuid,stspw;
    TextView T_yunbi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw3);


        Intent intent = this.getIntent();
        stsuid = intent.getStringExtra("UID");
        stspw = intent.getStringExtra("SPW");
        syunbi = intent.getStringExtra("yunbi");
        T_yunbi = findViewById(R.id.Tyunbi);
        T_yunbi.setText(syunbi);
        T_yunbi.getText();

    }
    public void submit(View view){
        Withdrawback withdrawback = new Withdrawback(this);
        withdrawback.execute(stsuid, stspw,syunbi);

        Intent intent = new Intent(this,Withdraw4.class);
        startActivity(intent);
    }

    class Withdrawback extends AsyncTask<String,Void,String> {


        public Withdrawback(Withdraw3 withdraw3) {
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            String wallet_url = "http://140.125.81.16/YunBuy/withdrawYunBI.php";
            try {
                String stuser = params[0];
                String stpassword = params[1];
                String syunbi =params[2];
                URL url = new URL(wallet_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("UserId","UTF-8")+"="+URLEncoder.encode(stuser,"UTF-8")+"&"+URLEncoder.encode("Psd","UTF-8")+"="+URLEncoder.encode(stpassword,"UTF-8")
                        +"&"+URLEncoder.encode("Syunbi","UTF-8")+"="+URLEncoder.encode(syunbi,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine()) !=null){
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
}
