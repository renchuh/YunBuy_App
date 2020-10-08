package com.example.zheweizhang.yunbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CommodityActicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);

        final Bundle bundle = this.getIntent().getExtras();


        ImageButton btnBack = findViewById(R.id.btn_back1);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView IbAddProduct = findViewById(R.id.iv_user_info);
        //跳轉到新增商品頁面
        IbAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommodityActicity.this, AddCommodityActivity.class);
                startActivity(intent);
            }
        });

        ImageButton IbMyProduct = findViewById(R.id.arrow_btn1);
        //跳轉到商品頁面
        IbMyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommodityActicity.this, MyCommodityActivity.class);
                startActivity(intent);
            }
        });
    }
}
