package com.bozhengjianshe.shenghuobang.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen.zhiwei on 2017-7-5.
 */

public class OrderDetailBean implements Serializable{


    /**
     * id : 0
     * userId : 0
     * createTime : null
     * receiveName : null
     * receivePhone : null
     * receiveAddress : null
     * transportType : 0
     * serviceTime : null
     * orderState : 0
     * orderStateCn : null
     * outTradeNo : null
     * serviceProviderId : 0
     * orderType : 0
     * orderAmount : 0
     * products : [{"id":11,"orderId":12,"userId":0,"productId":9,"productType":1,"productPrice":233,"productName":"测试建材推荐","productCount":1,"productImg":"http://47.92.24.138:8080/upload/goodsImg/20171213105428022.jpg"}]
     */

    private int id;
    private int userId;
    private String createTime;
    private String receiveName;
    private String receivePhone;
    private String receiveAddress;
    private int transportType;
    private String serviceTime;
    private int orderState;
    private String orderStateCn;
    private String outTradeNo;
    private int serviceProviderId;
    private int orderType;
    private int orderAmount;
    private List<ProductsBean> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public String getOrderStateCn() {
        return orderStateCn;
    }

    public void setOrderStateCn(String orderStateCn) {
        this.orderStateCn = orderStateCn;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(int serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean implements Serializable{
        /**
         * id : 11
         * orderId : 12
         * userId : 0
         * productId : 9
         * productType : 1
         * productPrice : 233
         * productName : 测试建材推荐
         * productCount : 1
         * productImg : http://47.92.24.138:8080/upload/goodsImg/20171213105428022.jpg
         */

        private int id;
        private int orderId;
        private int userId;
        private int productId;
        private int productType;
        private int productPrice;
        private String productName;
        private int productCount;
        private String productImg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
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
    }
}
