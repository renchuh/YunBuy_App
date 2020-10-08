package com.example.zheweizhang.yunbuy.bean;

/**
 * 商品實體類別
 * @author : autumn_leaf
 */
public class Commodity {

    //編號
    private Integer id;
    //標題
    private String title;
    //類別
    private String category;
    //價格
    private float price;
    //聯絡方式
    private String phone;
    //商品敘述
    private String description;
    //商品圖片,以二進位byte儲存
    private byte[] picture;
    //使用者學號
    private String stuId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
