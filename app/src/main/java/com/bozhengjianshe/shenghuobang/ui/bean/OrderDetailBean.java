package com.bozhengjianshe.shenghuobang.ui.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen.zhiwei on 2017-7-5.
 */

public class OrderDetailBean implements Serializable {


    /**
     * lxrdh : 13685965478
     * commodity : 10,
     * lxrsfzh :
     * del : 1
     * managerid : 0
     * imgsfzfro :
     * imgsfzrev :
     * odnum : 201805081119565118
     * id : 34
     * state : 1
     * extrafee : 10
     * tpbz :
     * modtime : 0
     * count : 0
     * statemark :
     * quality : 1
     * lxrxm : all咯
     * fczimglist :
     * lxradress : 阿狸裤子我我我我我老K咯我我了了了家里好好好了了
     * membersid : 0
     * phone :
     * lxrhyzk : 0
     * lxrxb : 0
     * detail : [{"zj":"10","dj":"10","num":"1","name":"测试图片","id":"10"}]
     * time : 1525749596524
     * ncount : 30
     * age :
     * mark :
     * memberid : 4
     */

    private String lxrdh;
    private String commodity;
    private String lxrsfzh;
    private int del;
    private int managerid;
    private String imgsfzfro;
    private String imgsfzrev;
    private String odnum;
    private int id;
    private int state;
    private int extrafee;
    private String tpbz;
    private int modtime;
    private int count;
    private String statemark;
    private int quality;
    private String lxrxm;
    private String fczimglist;
    private String lxradress;
    private int membersid;
    private String phone;
    private int lxrhyzk;
    private int lxrxb;
    private long time;
    private int ncount;
    private String age;
    private String mark;
    private int memberid;
    private List<DetailBean> detail;

    public String getLxrdh() {
        return lxrdh;
    }

    public void setLxrdh(String lxrdh) {
        this.lxrdh = lxrdh;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getLxrsfzh() {
        return lxrsfzh;
    }

    public void setLxrsfzh(String lxrsfzh) {
        this.lxrsfzh = lxrsfzh;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }

    public int getManagerid() {
        return managerid;
    }

    public void setManagerid(int managerid) {
        this.managerid = managerid;
    }

    public String getImgsfzfro() {
        return imgsfzfro;
    }

    public void setImgsfzfro(String imgsfzfro) {
        this.imgsfzfro = imgsfzfro;
    }

    public String getImgsfzrev() {
        return imgsfzrev;
    }

    public void setImgsfzrev(String imgsfzrev) {
        this.imgsfzrev = imgsfzrev;
    }

    public String getOdnum() {
        return odnum;
    }

    public void setOdnum(String odnum) {
        this.odnum = odnum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getExtrafee() {
        return extrafee;
    }

    public void setExtrafee(int extrafee) {
        this.extrafee = extrafee;
    }

    public String getTpbz() {
        return tpbz;
    }

    public void setTpbz(String tpbz) {
        this.tpbz = tpbz;
    }

    public int getModtime() {
        return modtime;
    }

    public void setModtime(int modtime) {
        this.modtime = modtime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatemark() {
        return statemark;
    }

    public void setStatemark(String statemark) {
        this.statemark = statemark;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getLxrxm() {
        return lxrxm;
    }

    public void setLxrxm(String lxrxm) {
        this.lxrxm = lxrxm;
    }

    public String getFczimglist() {
        return fczimglist;
    }

    public void setFczimglist(String fczimglist) {
        this.fczimglist = fczimglist;
    }

    public String getLxradress() {
        return lxradress;
    }

    public void setLxradress(String lxradress) {
        this.lxradress = lxradress;
    }

    public int getMembersid() {
        return membersid;
    }

    public void setMembersid(int membersid) {
        this.membersid = membersid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLxrhyzk() {
        return lxrhyzk;
    }

    public void setLxrhyzk(int lxrhyzk) {
        this.lxrhyzk = lxrhyzk;
    }

    public int getLxrxb() {
        return lxrxb;
    }

    public void setLxrxb(int lxrxb) {
        this.lxrxb = lxrxb;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getNcount() {
        return ncount;
    }

    public void setNcount(int ncount) {
        this.ncount = ncount;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public static class DetailBean {
        /**
         * zj : 10
         * dj : 10
         * num : 1
         * name : 测试图片
         * id : 10
         */

        private String zj;
        private String dj;
        private String num;
        private String name;
        private String id;
        private int lb;
        private String thumbnail;

        public int getLb() {
            return lb;
        }

        public void setLb(int lb) {
            this.lb = lb;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public double getZj() {
            return TextUtils.isEmpty(zj)? 0.00:Double.parseDouble(zj);
        }

        public void setZj(String zj) {
            this.zj = zj;
        }

        public String getDj() {
            return dj;
        }

        public void setDj(String dj) {
            this.dj = dj;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
