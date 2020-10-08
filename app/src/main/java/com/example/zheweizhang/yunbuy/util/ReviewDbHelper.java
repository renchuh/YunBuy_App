package com.example.zheweizhang.yunbuy.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zheweizhang.yunbuy.bean.Review;

import java.util.LinkedList;

/**
 * 留言資料庫連接類別
 * @author autumn_leaf
 */
public class ReviewDbHelper extends SQLiteOpenHelper {

    //定義資料庫表名
    public static final String DB_NAME = "tb_review";
    /** 創建評論訊息表 **/
    private static final String CREATE_REVIEW_DB = "create table tb_review (" +
            "id integer primary key autoincrement," +
            "stuId text," +
            "currentTime text," +
            "content text," +
            "position integer )";

    public ReviewDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_REVIEW_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 添加評論
     * @param review 評論Object
     */
    public void addReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("stuId",review.getStuId());
        values.put("currentTime",review.getCurrentTime());
        values.put("content",review.getContent());
        values.put("position",review.getPosition());
        db.insert(DB_NAME,null,values);
        values.clear();
    }

    /**
     * 根據商品項目編號讀取相應的評論訊息
     * @return 評論對象數據
     */
    public LinkedList<Review> readReviews(int  position) {
        LinkedList<Review> reviews = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_review where position=?",new String[]{String.valueOf(position)});
        if(cursor.moveToFirst()) {
            do{
                String stuId = cursor.getString(cursor.getColumnIndex("stuId"));
                String currentTime = cursor.getString(cursor.getColumnIndex("currentTime"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Review review = new Review();
                review.setStuId(stuId);
                review.setCurrentTime(currentTime);
                review.setContent(content);
                reviews.add(review);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return reviews;
    }

}
