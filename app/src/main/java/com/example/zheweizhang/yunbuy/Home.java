package com.example.zheweizhang.yunbuy;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class Home extends AppCompatActivity {

    boolean logon = false;
    public static final int FUNC_LOGIN = 1;
    TextView text_uid,text_pw,t_name;
    String suid,spw,sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_uid = findViewById(R.id.textuid);
        text_pw = findViewById(R.id.textpw);
        t_name = findViewById(R.id.textName);


        if (!logon){
            Intent intent = new Intent(this, Login.class);
            startActivityForResult(intent,FUNC_LOGIN);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FUNC_LOGIN){
            if (resultCode == RESULT_OK){
                String uid = data.getStringExtra("LOGIN_USERID");
                String pw = data.getStringExtra("LOGIN_PASSWD");
                Log.d("RESULT", uid + "/" + pw);
                text_uid.setText(uid);
                text_uid.getText();

                text_pw.setText(pw);
                text_pw.getText();

                suid = text_uid.getText().toString();
                spw = text_pw.getText().toString();
                YunBuyBack yunbuyback = new YunBuyBack(this);
                yunbuyback.execute(suid,spw);
            }else {
                finish();
            }
        }
    }
    class YunBuyBack extends AsyncTask<String,Void,String>{

        public YunBuyBack(Home home) {
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb = new StringBuilder();
            String wallet_url = "http://140.125.81.16/YunBuy/nameapp.php";
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

                Transaction t = new Transaction(Name);
                trans.add(t);
                Log.d("HJSON",Name);

                String Na = "Hello,"+Name;
                t_name.setText(Na);
                t_name.getText();

                sname = Name;
                Log.d("name",sname);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void PayYunBi(View view){
        Intent intent = new Intent(this, PayYunbi.class);
        intent.putExtra("UID",suid);
        intent.putExtra("SPW",spw);
        startActivity(intent);
    }
    public void goCommodity(View view){
        Intent intent = new Intent(this, CommodityActicity.class);
        intent.putExtra("UID",suid);
        intent.putExtra("SPW",spw);
        startActivity(intent);
    }
    public void goSearch(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("UID",suid);
        intent.putExtra("SPW",spw);
        intent.putExtra("user_id",sname);
        startActivity(intent);
    }
    public void storeYunbi(View view){
        Intent intent = new Intent(this,StoreYunBi.class);
        intent.putExtra("UID",suid);
        intent.putExtra("SPW",spw);
        startActivity(intent);
    }
    public void WithdrawYunbi(View view){
        Intent intent = new Intent(this,Withdraw.class);
        intent.putExtra("UID",suid);
        intent.putExtra("SPW",spw);
        startActivity(intent);
    }
    public void profile(View view){
        Intent intent = new Intent(this,Profile.class);
        intent.putExtra("UID",suid);
        intent.putExtra("SPW",spw);
        startActivity(intent);
    }
}
