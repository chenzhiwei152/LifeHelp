package com.bozhengjianshe.shenghuobang.ui.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/9.
 */

public class CardListItemBean implements Serializable{


    /**
     * id : 4
     * productId : 0
     * productName : 测试服务1
     * productPrice : 121
     * productCount : 1
     * productImg : http://47.92.24.138:8080/upload/goodsImg/20171209110834241.jpg
     * productType : 2
     * userId : 2
     */

    private int id;
    private int productId;
    private String productName;
    private int productPrice;
    private int productCount;
    private String productImg;
    private int productType;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
