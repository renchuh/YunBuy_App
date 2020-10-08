package com.example.zheweizhang.yunbuy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    /*Spinner year,faculty,stclass;
    EditText Stname,Stid,Stphone,Stuser,Stpassword;*/
    TextInputEditText Suserid,Sname,SstudentID,Sphone,Semail,Sremail,Spassword,Sspassword;
    Spinner faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        faculty = findViewById(R.id.faculty);
        ArrayAdapter<CharSequence> nAdapter1 = ArrayAdapter.createFromResource(this, R.array.faculty, R.layout.support_simple_spinner_dropdown_item);
        faculty.setAdapter(nAdapter1);


    }

    public void conti (View view){
        String sfaculty =  faculty.getSelectedItem().toString();

        Suserid = findViewById(R.id.suserid);
        Sname = findViewById(R.id.sname);
        SstudentID = findViewById(R.id.sstudentID);
        Sphone = findViewById(R.id.sphone);
        Semail = findViewById(R.id.semail);
        Sremail = findViewById(R.id.sremail);
        Spassword = findViewById(R.id.spassword);
        Sspassword = findViewById(R.id.sspassword);

        String ssuserid = Suserid.getText().toString();
        String ssname = Sname.getText().toString();
        String ssstudentID = SstudentID.getText().toString();
        String ssphone = Sphone.getText().toString();
        String ssemail = Semail.getText().toString();
        String sspassword = Spassword.getText().toString();
        String ssspassword = Sspassword.getText().toString();

        Sent_out sent_out = new Sent_out(this);
        sent_out.execute(ssuserid,ssname,sspassword,ssspassword,ssphone,ssemail,ssstudentID,sfaculty);
    }
    class Sent_out extends AsyncTask<String,Void,String>{

        public Sent_out(SignupActivity signupActivity) {
        }

        @Override
        protected String doInBackground(String... params) {
            String ssuserid = params[0];
            String ssname = params[1];
            String sspassword = params[2];
            String ssspassword = params[3];
            String ssphone = params[4];
            String ssemail = params[5];
            String ssstudentID = params[6];
            String sfaculty = params[7];

            String sentURL = "http://140.125.81.16/YunBuy/signupapp.php";
            try {
                URL url = new URL(sentURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("UserId","UTF-8")+"="+URLEncoder.encode(ssuserid,"UTF-8")+"&"+URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(ssname,"UTF-8")
                        +"&"+URLEncoder.encode("Psd","UTF-8")+"="+URLEncoder.encode(sspassword,"UTF-8")+"&"+URLEncoder.encode("SecurityPassword","UTF-8")+"="+URLEncoder.encode(ssspassword,"UTF-8")
                        +"&"+URLEncoder.encode("Phone","UTF-8")+"="+URLEncoder.encode(ssphone,"UTF-8")+"&"+URLEncoder.encode("YuntechEmail","UTF-8")+"="+URLEncoder.encode(ssemail,"UTF-8")
                        +"&"+URLEncoder.encode("SId","UTF-8")+"="+URLEncoder.encode(ssstudentID,"UTF-8")+"&"+URLEncoder.encode("Department","UTF-8")+"="+URLEncoder.encode(sfaculty,"UTF-8");
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
                Log.d("HTTPts", result);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("1") ){
                Toast.makeText(SignupActivity.this,"註冊成功",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //Toast.makeText(SignupActivity.this,"註冊失敗",Toast.LENGTH_LONG).show();
                Toast.makeText(SignupActivity.this,"註冊失敗",Toast.LENGTH_LONG).show();

            }
        }
    }

}
