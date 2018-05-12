package com.bozhengjianshe.shenghuobang.ui.bean;


import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen.zhiwei on 2017-6-26.
 */

public class GoodsListBean implements Serializable {


    /**
     * thumbnail : files/idcard/20180412/3863.jpg
     * cost : 500
     * serv : 1
     * ejfl : 30
     * fee : 100
     * cname : 閾剁墝鐤忛€氫笅姘撮亾
     * cnum : 201804141957175017
     * picture : [{"img":"files/idcard/20180427/7531.png","detail":"璇︽儏鍥剧墖-1"},{"img":"files/idcard/20180427/3416.png","detail":"璇︽儏鍥剧墖-2"},{"img":"files/idcard/20180427/7605.png","detail":"璇︽儏鍥剧墖-3"}]
     * yjfl : 15
     * addtime : 2018-04-12 08:04:04
     * lb : 1
     * sfyh : 2
     * id : 5
     * detail : files/idcard/20180412/0405.jpg
     * sftj : 2
     * profit : 200
     */

    private String thumbnail;
    private String cost;
    private int serv;
    private int ejfl;
    private double fee;
    private String cname;
    private String cnum;
    private int yjfl;
    private String addtime;
    private int lb;
    private int sfyh;
    private int id;
    private String detail;
    private double sftj;
    private double profit;
    private int num = 1;
    private double freight;//运费

    public double getFreight() {
        return freight;
    }

    public void setFreight(float freight) {
        this.freight = freight;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private List<PictureBean> picture;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getCost() {
        return (TextUtils.isEmpty(cost)) ? 0.00 : Double.parseDouble(cost);
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getServ() {
        return serv;
    }

    public void setServ(int serv) {
        this.serv = serv;
    }

    public int getEjfl() {
        return ejfl;
    }

    public void setEjfl(int ejfl) {
        this.ejfl = ejfl;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
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

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getLb() {
        return lb;
    }

    public void setLb(int lb) {
        this.lb = lb;
    }

    public int getSfyh() {
        return sfyh;
    }

    public void setSfyh(int sfyh) {
        this.sfyh = sfyh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getSftj() {
        return sftj;
    }

    public void setSftj(double sftj) {
        this.sftj = sftj;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public List<PictureBean> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureBean> picture) {
        this.picture = picture;
    }

    public static class PictureBean implements Serializable {
        /**
         * img : files/idcard/20180427/7531.png
         * detail : 璇︽儏鍥剧墖-1
         */

        private String img;
        private String detail;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }


    private boolean Checked;

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }
}
