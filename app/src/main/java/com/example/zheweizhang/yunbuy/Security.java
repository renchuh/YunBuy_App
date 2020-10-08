package com.example.zheweizhang.yunbuy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

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

public class Security extends AppCompatActivity {
    TextView t_suid,t_spw,t_tunbi,t_Security;
    String stsuid,stspw,syunbi,wyunbi;
    int yunbi,wallet;
    TextInputEditText t_code;
    boolean yunbienough = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registartion);

        t_suid = findViewById(R.id.tsuid);
        t_spw = findViewById(R.id.tspw);
        t_tunbi = findViewById(R.id.yunbi);
        t_Security = findViewById(R.id.security);
        t_code = findViewById(R.id.code);

        Intent intent = this.getIntent();
        stsuid = intent.getStringExtra("UID");
        stspw = intent.getStringExtra("SPW");
        syunbi = intent.getStringExtra("yunbi");
        Log.d("UID", syunbi);

        codeBack codeback = new codeBack(this);
        codeback.execute(stsuid, stspw);

        YunbiBack yunbiback = new YunbiBack(this);
        yunbiback.execute(stsuid, stspw);

        yunBack yunback = new yunBack(this);
        yunback.execute(stsuid, stspw);
        chack();
    }

    public void chack(){
        try{
            yunbi = Integer.parseInt(syunbi);
            wallet = Integer.parseInt(wyunbi);
        } catch (NumberFormatException e) {}

        //this wallet is User yunbi
        if(wallet >= 0 ){
            yunbienough = false;
        }
        //this yunbi is product yunbi
        if (yunbi >= 0){
            yunbienough = false;
        }
    }


    class codeBack extends AsyncTask<String,Void,String> {

        public codeBack(Security security) {
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            String wallet_url = "http://140.125.81.16/YunBuy/appmail.php";
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
    }
    class YunbiBack extends AsyncTask<String,Void,String> {

        public YunbiBack(Security security) {
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            String wallet_url = "http://140.125.81.16/YunBuy/yunbiapp.php";
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

                String Yunbi = obj.getString("Yunbi");
                Transaction t = new Transaction(Yunbi);
                trans.add(t);
                Log.d("HJSON",Yunbi);

                wyunbi = Yunbi;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class yunBack extends AsyncTask<String,Void,String> {

        public yunBack(Security security) {

        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            String wallet_url = "http://140.125.81.16/YunBuy/Securityapp.php";
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

                String SecurityPassword = obj.getString("Code");

                Transaction t = new Transaction(SecurityPassword);
                trans.add(t);
                Log.d("HJSON",SecurityPassword);

                String sp = SecurityPassword;
                Log.d("UID", sp);
                t_Security.setText(sp);
                t_Security.getText();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    int Error = 0;
    public void check(View view){

        String Scode = t_code.getText().toString();
        String SSecurity = t_Security.getText().toString();

        if(yunbienough) {
            if (Scode.equals(SSecurity)) {
                Toast.makeText(Security.this, "Security Password success", Toast.LENGTH_SHORT).show();
                Payback payback = new Payback(this);
                payback.execute(stsuid, stspw, syunbi);
                Intent intent = new Intent(this, Successful.class);
                startActivity(intent);
            } else {
                for (int i = 0; Error <= 3; i++) {
                    Error++;
                    Toast.makeText(Security.this, "Security Password Error" + Error + "time", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if (Error == 3) {
                Toast.makeText(Security.this, "Account Locking", Toast.LENGTH_SHORT).show();
            }
        }else {
            AlertDialog.Builder builder =  new AlertDialog.Builder(Security.this);
            builder.setMessage("Yunbi insufficient");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
    }

    class Payback extends AsyncTask<String,Void,String> {

        public Payback(Security security) {
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
