package com.example.zheweizhang.yunbuy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zheweizhang.yunbuy.adapter.ReviewAdapter;
import com.example.zheweizhang.yunbuy.bean.Collection;
import com.example.zheweizhang.yunbuy.bean.Review;
import com.example.zheweizhang.yunbuy.util.MyCollectionDbHelper;
import com.example.zheweizhang.yunbuy.util.ReviewDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * 商品訊息評論/留言類別
 * @author autumn_leaf
 */
public class ReviewCommodityActivity extends AppCompatActivity {

    TextView title,description,price,phone;
    ImageView ivCommodity;
    ListView lvReview;
    LinkedList<Review> reviews = new LinkedList<>();
    EditText etComment;
    int position;
    byte[] picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_commodity);
        ivCommodity = findViewById(R.id.iv_commodity);
        title = findViewById(R.id.tv_title);
        description = findViewById(R.id.tv_description);
        price = findViewById(R.id.tv_price);
        phone = findViewById(R.id.tv_phone);
        Bundle b = getIntent().getExtras();
        if( b != null) {
            picture = b.getByteArray("picture");
            Bitmap img = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            ivCommodity.setImageBitmap(img);
            title.setText(b.getString("title"));
            description.setText(b.getString("description"));
            price.setText(String.valueOf(b.getFloat("price"))+"元");
            phone.setText(b.getString("phone"));
            position = b.getInt("position");
        }
        //返回
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //點擊收藏按鈕
        ImageButton ibMyLove = findViewById(R.id.ib_my_love);
        ibMyLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCollectionDbHelper dbHelper = new MyCollectionDbHelper(getApplicationContext(), MyCollectionDbHelper.DB_NAME,null,1);
                Collection collection = new Collection();
                collection.setTitle(title.getText().toString());
                String price1 = price.getText().toString().substring(0,price.getText().toString().length()-1);
                collection.setPrice(Float.parseFloat(price1));
                collection.setPhone(phone.getText().toString());
                collection.setDescription(description.getText().toString());
                collection.setPicture(picture);
                String stuId = getIntent().getStringExtra("stuId");
                collection.setStuId(stuId);
                dbHelper.addMyCollection(collection);
                Toast.makeText(getApplicationContext(),"已添加至我的收藏!",Toast.LENGTH_SHORT).show();
            }
        });

        etComment = findViewById(R.id.et_comment);
        lvReview = findViewById(R.id.list_comment);
        //提交評論點擊event
        Button btnReview = findViewById(R.id.btn_submit);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先檢查是否為空
                if(CheckInput()) {
                    ReviewDbHelper dbHelper = new ReviewDbHelper(getApplicationContext(), ReviewDbHelper.DB_NAME,null,1);
                    Review review = new Review();
                    review.setContent(etComment.getText().toString());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                    //獲取目前時間
                    Date date = new Date(System.currentTimeMillis());
                    review.setCurrentTime(simpleDateFormat.format(date));
                    String stuId = getIntent().getStringExtra("stuId");
                    review.setStuId(stuId);
                    review.setPosition(position);
                    dbHelper.addReview(review);
                    //評論置為空
                    etComment.setText("");
                    Toast.makeText(getApplicationContext(),"評論成功!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        final ReviewAdapter adapter = new ReviewAdapter(getApplicationContext());
        final ReviewDbHelper dbHelper = new ReviewDbHelper(getApplicationContext(), ReviewDbHelper.DB_NAME,null,1);
        reviews = dbHelper.readReviews(position);
        adapter.setData(reviews);
        //設置Adapter
        lvReview.setAdapter(adapter);
        //刷新頁面
        TextView tvRefresh = findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviews = dbHelper.readReviews(position);
                adapter.setData(reviews);
                lvReview.setAdapter(adapter);
            }
        });
    }

    /**
     * 檢查輸入評論是否為空
     * @return true
     */
    public boolean CheckInput() {
        String comment = etComment.getText().toString();
        if (comment.trim().equals("")) {
            Toast.makeText(this,"評論內容不能為空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
