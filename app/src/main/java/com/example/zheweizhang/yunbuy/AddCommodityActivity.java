package com.example.zheweizhang.yunbuy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zheweizhang.yunbuy.bean.Commodity;
import com.example.zheweizhang.yunbuy.util.CommodityDbHelper;

import java.io.ByteArrayOutputStream;

public class AddCommodityActivity extends AppCompatActivity {
    TextView tvStuId;
    ImageButton ivPhoto;
    EditText etTitle,etPrice,etStock,etDescription;
    Spinner spType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commodity);

        tvStuId = findViewById(R.id.tv_student_id);
        tvStuId.setText(this.getIntent().getStringExtra("user_id"));

        ImageButton btnBack = findViewById(R.id.btn_back2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivPhoto = findViewById(R.id.iv_photo);
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,1);
            }
        });

        etTitle = findViewById(R.id.et_title);
        etPrice = findViewById(R.id.et_price);
        etStock = findViewById(R.id.et_stock);
        etDescription = findViewById(R.id.et_description);
        ImageButton btnPublish = findViewById(R.id.btn_publish);
        //上傳按鈕點擊event
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //檢查合法性
                if(CheckInput()) {
                    CommodityDbHelper dbHelper = new CommodityDbHelper(getApplicationContext(), CommodityDbHelper.DB_NAME, null, 1);
                    Commodity commodity = new Commodity();
                    //把圖片先轉換成bitmap（點陣圖）格式
                    BitmapDrawable drawable = (BitmapDrawable) ivPhoto.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    //二進位數output
                    ByteArrayOutputStream byStream = new ByteArrayOutputStream();
                    //將圖片壓縮成質量為100的PNG格式圖片
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byStream);
                    //把output轉換為二進位數
                    byte[] byteArray = byStream.toByteArray();
                    commodity.setPicture(byteArray);
                    commodity.setTitle(etTitle.getText().toString());
                    commodity.setPrice(Float.parseFloat(etPrice.getText().toString()));
                    commodity.setPhone(etStock.getText().toString());
                    commodity.setDescription(etDescription.getText().toString());
                    commodity.setStuId(tvStuId.getText().toString());
                    if (dbHelper.AddCommodity(commodity)) {
                        Toast.makeText(getApplicationContext(), "商品上傳成功 !", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "商品上傳失敗 !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == 1) {
            //從相簿返回的數據
            if (data != null) {
                //得到圖片的所有路徑
                Uri uri = data.getData();
                ivPhoto.setImageURI(uri);
            }
        }
    }

    /**
     * 檢查input是否合法
     */
    public boolean CheckInput() {
        String title = etTitle.getText().toString();
        String price = etPrice.getText().toString();
        String stock = etStock.getText().toString();
        String description = etDescription.getText().toString();
        if (title.trim().equals("")) {
            Toast.makeText(this,"商品名稱不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (price.trim().equals("")) {
            Toast.makeText(this,"商品價格不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stock.trim().equals("")) {
            Toast.makeText(this,"商品數量不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (description.trim().equals("")) {
            Toast.makeText(this,"商品敘述不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
