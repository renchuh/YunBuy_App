package com.example.zheweizhang.yunbuy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class Login extends AppCompatActivity {

    Thread login;
    private TextInputEditText  edUserid,edPasswd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //取得通訊服務
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            //是否有連線
            Boolean IsConnected = false;
            //依序檢查各種網路連線方式
            for(NetworkInfo network : connectivity.getAllNetworkInfo())
            {
                if(network.getState() == NetworkInfo.State.CONNECTED)
                {
                    IsConnected = true;
                    //顯示網路連線種類
                    //Toast.makeText(getApplicationContext(), network.getTypeName(), Toast.LENGTH_SHORT).show();
                }
            }
            if(IsConnected == false)
                Toast.makeText(getApplicationContext(), "沒有網路連線", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "沒有網路連線", Toast.LENGTH_SHORT).show();

        }


    }

    public void login(View view){
        edUserid = findViewById(R.id.code);
        edPasswd = findViewById(R.id.passwd);
        String uid = edUserid.getText().toString();
        String pw = edPasswd.getText().toString();
        String type = "login";
        yunBacklogin yunbacklogin = new yunBacklogin(this);
        yunbacklogin.execute(type, uid, pw);
    }
    public void signup(View view){
        Intent intent = new  Intent (this,SignupActivity.class);
        startActivity(intent);
    }

    class yunBacklogin extends AsyncTask<String,Void,String>{

        public yunBacklogin(Login login) {
        }

        @Override
        protected String doInBackground(String... params) {
            boolean logon = false;

            String type = params[0];
            String login_url = "http://140.125.81.16/YunBuy/loginapp.php";
            if(type.equals("login")){
                try {
                    String user = params[1];
                    String password = params[2];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("UserId","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"+URLEncoder.encode("Psd","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"big5"));
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("1") ){

                String uid = edUserid.getText().toString();
                String pw = edPasswd.getText().toString();
                getIntent().putExtra("LOGIN_USERID",uid);
                getIntent().putExtra("LOGIN_PASSWD",pw);
                setResult(RESULT_OK, getIntent());
                finish();
            } else {
                Toast.makeText(Login.this,"登入失敗",Toast.LENGTH_LONG).show();
            }
        }
    }

}
