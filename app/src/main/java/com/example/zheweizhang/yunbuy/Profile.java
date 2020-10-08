package com.example.zheweizhang.yunbuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    String stsuid,stspw;
    TextView T_name,T_SID,Tyunbi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Intent intent = this.getIntent();

        stsuid = intent.getStringExtra("UID");
        stspw = intent.getStringExtra("SPW");

        T_name = findViewById(R.id.username);
        T_SID = findViewById(R.id.sid);
        Tyunbi = findViewById(R.id.yunbi);
        Profileback profileback = new Profileback(this);
        profileback.execute(stsuid, stspw);

    }
    class Profileback extends AsyncTask<String,Void,String> {

        public Profileback(Profile profile) {
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb = new StringBuilder();
            String wallet_url = "http://140.125.81.16/YunBuy/Profile.php";
            try {
                String stuser = params[0];
                String stpassword = params[1];
                URL url = new URL(wallet_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("UserId","UTF-8")+"="+URLEncoder.encode(stuser,"UTF-8")+"&"+URLEncoder.encode("Psd","UTF-8")+"="+URLEncoder.encode(stpassword,"UTF-8");
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("JSON", s);
            parseJSON(s);
        }

        private void parseJSON(String s) {
            ArrayList<Transaction> trans =new ArrayList<>();
            try {
                JSONObject obj = new JSONObject(s);

                String Name = obj.getString("Name");
                String SId = obj.getString("SId");
                String Yunbi = obj.getString("Yunbi");
                Transaction t = new Transaction(Name);
                trans.add(t);
                Log.d("HJSON",Name);


                T_name.setText(Name);
                T_name.getText();

                T_SID.setText(SId);
                T_SID.getText();

                Tyunbi.setText(Yunbi);
                Tyunbi.getText();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    public void yunbobo (View view){
        Uri uri = Uri.parse("https://line.me/R/ti/p/%40454cfnrv");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }



}
