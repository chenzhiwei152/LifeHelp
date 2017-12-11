package com.bozhengjianshe.shenghuobang.ui.bean;

/**
 * Created by chen.zhiwei on 2017-12-11.
 */

public class CollectionItemBean {

    /**
     * id : 3
     * productId : 0
     * productName : 服务测试反反复复
     * productPrice : 234
     * productImg : http://47.92.24.138:8080/upload/goodsImg/20171211134148241.jpg
     * userId : 2
     * productType : 2
     */

    private int id;
    private int productId;
    private String productName;
    private int productPrice;
    private String productImg;
    private int userId;
    private int productType;

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

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }
}
