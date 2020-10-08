package com.example.zheweizhang.yunbuy.bean;

/**
 * 評論實體類別
 * @author autumn_leaf
 */
public class Review {

    private String stuId;//使用者帳號
    private String currentTime;//目前時間
    private String content;//評論內容
    private Integer position;//商品編號

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}


