package com.bozhengjianshe.shenghuobang.ui.bean;

import java.io.Serializable;

/**
 * Created by chen.zhiwei on 2017-7-4.
 */

public class BuyOrderListItemBean implements Serializable {


    /**
     * id : 11
     * userId : 2
     * createTime : 1513149137000
     * receiveName : 测试
     * receivePhone : 132151515
     * receiveAddress : 详细地址详细地址
     * transportType : 0
     * serviceTime : null
     * orderState : 1
     * orderStateCn : 待支付
     * outTradeNo : TNO2017121315121714700000001
     * serviceProviderId : 0
     * orderType : 1
     * orderAmount : 233
     */

    private int id;
    private int userId;
    private long createTime;
    private String receiveName;
    private String receivePhone;
    private String receiveAddress;
    private int transportType;
    private Object serviceTime;
    private int orderState;
    private String orderStateCn;
    private String outTradeNo;
    private int serviceProviderId;
    private int orderType;
    private int orderAmount;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
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

    public Object getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Object serviceTime) {
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
}
