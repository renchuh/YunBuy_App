package com.example.zheweizhang.yunbuy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zheweizhang.yunbuy.adapter.MyCommodityAdapter;
import com.example.zheweizhang.yunbuy.bean.Commodity;
import com.example.zheweizhang.yunbuy.util.CommodityDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的新增商品Activity類別
 */
public class MyCommodityActivity extends AppCompatActivity {

    ListView lvMyCommodity;
    List<Commodity> myCommodities = new ArrayList<>();
    TextView tvStuId;

    CommodityDbHelper dbHelper;

    MyCommodityAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commodity);
        TextView tvBack = findViewById(R.id.tv_back);
        //點擊返回銷毀當前頁面
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvStuId = findViewById(R.id.tv_stu_id);
        tvStuId.setText(this.getIntent().getStringExtra("stu_id"));
        lvMyCommodity = findViewById(R.id.lv_my_commodity);
        adapter = new MyCommodityAdapter(getApplicationContext());
        dbHelper = new CommodityDbHelper(getApplicationContext(), CommodityDbHelper.DB_NAME,null,1);
        myCommodities = dbHelper.readMyCommodities(tvStuId.getText().toString());
        adapter.setData(myCommodities);
        lvMyCommodity.setAdapter(adapter);
        //長按點擊event
        lvMyCommodity.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //注意,這裡的content不能寫getApplicationContent();
                AlertDialog.Builder builder = new AlertDialog.Builder(MyCommodityActivity.this);
                builder.setTitle("提示:").setMessage("確定刪除此商品嗎?").setIcon(R.drawable.icon_user).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //根據商品名稱,商品敘述和價格執行刪除動作
                        Commodity commodity = (Commodity) adapter.getItem(position);
                        dbHelper.deleteMyCommodity(commodity.getTitle(),commodity.getDescription(),commodity.getPrice());
                        //數據一樣,可以直接用,關聯删除
                        //dbHelper2.deleteMyCollection(commodity.getTitle(),commodity.getDescription(),commodity.getPrice());
                        Toast.makeText(MyCommodityActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return false;
            }
        });
        //刷新頁面點擊event
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new CommodityDbHelper(MyCommodityActivity.this, CommodityDbHelper.DB_NAME,null,1);
                adapter = new MyCommodityAdapter(MyCommodityActivity.this);
                myCommodities = dbHelper.readMyCommodities(tvStuId.getText().toString());
                adapter.setData(myCommodities);
                lvMyCommodity.setAdapter(adapter);
            }
        });
    }
}
