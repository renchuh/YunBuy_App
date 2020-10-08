package com.example.zheweizhang.yunbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zheweizhang.yunbuy.adapter.AllCommodityAdapter;
import com.example.zheweizhang.yunbuy.bean.Commodity;
import com.example.zheweizhang.yunbuy.util.CommodityDbHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView lvAllCommodity;
    List<Commodity> allCommodities = new ArrayList<>();


    CommodityDbHelper dbHelper;
    AllCommodityAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);


        ImageButton btnBack = findViewById(R.id.btn_back1);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        lvAllCommodity = findViewById(R.id.lv_all_commodity);
        dbHelper = new CommodityDbHelper(getApplicationContext(), CommodityDbHelper.DB_NAME, null, 1);
        adapter = new AllCommodityAdapter(getApplicationContext());
        allCommodities = dbHelper.readAllCommodities();
        adapter.setData(allCommodities);
        lvAllCommodity.setAdapter(adapter);
        final Bundle bundle = this.getIntent().getExtras();
        final TextView tvStuNumber = findViewById(R.id.tv_student_number);
        final String stuNum = this.getIntent().getStringExtra("user_id");
        String str = "";
        if (bundle != null) {
            str = "歡迎" + stuNum + ",你好!";
        }
        tvStuNumber.setText(str);




        //刷新界面
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCommodities = dbHelper.readAllCommodities();
                adapter.setData(allCommodities);
                lvAllCommodity.setAdapter(adapter);
            }
        });
        //為每一個item設置點擊event
        lvAllCommodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Commodity commodity = (Commodity) lvAllCommodity.getAdapter().getItem(position);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position",position);
                bundle1.putByteArray("picture",commodity.getPicture());
                bundle1.putString("title",commodity.getTitle());
                bundle1.putString("description",commodity.getDescription());
                bundle1.putFloat("price",commodity.getPrice());
                bundle1.putString("phone",commodity.getPhone());
                bundle1.putString("stuId",stuNum);
                Intent intent = new Intent(SearchActivity.this, ReviewCommodityActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });





    }
}
