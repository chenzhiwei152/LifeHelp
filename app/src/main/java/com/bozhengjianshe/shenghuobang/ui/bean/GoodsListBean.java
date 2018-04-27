package com.bozhengjianshe.shenghuobang.ui.bean;


import java.io.Serializable;

/**
 * Created by chen.zhiwei on 2017-6-26.
 */

public class GoodsListBean  implements Serializable {


    /**
     * id : 6
     * fee : 100
     * profit : 200
     * ejfl : 13
     * addtime : 2018-04-14 07:04:17
     * thumbnail : files/idcard/20180414/5258.jpg
     * serv : 1
     * lb : 2
     * cnum : 201804141957175013
     * yjfl : 10
     * cost : 100
     * cname : 落地大窗帘
     */

    private int id;
    private int fee;
    private int profit;
    private int ejfl;
    private String addtime;
    private String thumbnail;
    private int serv;
    private int lb;
    private String cnum;
    private int yjfl;
    private int cost;
    private String cname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getEjfl() {
        return ejfl;
    }

    public void setEjfl(int ejfl) {
        this.ejfl = ejfl;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getServ() {
        return serv;
    }

    public void setServ(int serv) {
        this.serv = serv;
    }

    public int getLb() {
        return lb;
    }

    public void setLb(int lb) {
        this.lb = lb;
    }

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }

    public int getYjfl() {
        return yjfl;
    }

    public void setYjfl(int yjfl) {
        this.yjfl = yjfl;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
