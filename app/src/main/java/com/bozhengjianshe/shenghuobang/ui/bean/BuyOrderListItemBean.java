package com.bozhengjianshe.shenghuobang.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen.zhiwei on 2017-7-4.
 */

public class BuyOrderListItemBean implements Serializable {


    /**
     * lxrdh : 13685965478
     * count : 0
     * del : 1
     * lxrxm : all咯
     * lxradress : 阿狸裤子我我我我我老K咯我我了了了家里好好好了了
     * addtime : 2018-05-08 11:05:08
     * name : null
     * odnum : 201805081127082608
     * id : 36
     * state : 1
     * detail : [{"zj":"10","dj":"10","num":"1","name":"测试图片","id":"10"}]
     * ncount : 30
     * extrafee : 10
     */

    private String lxrdh;
    private String count;
    private String del;
    private String lxrxm;
    private String lxradress;
    private String addtime;
    private String name;
    private String odnum;
    private String id;
    private int state;
    private String ncount;
    private String extrafee;
    private List<DetailBean> detail;

    public String getLxrdh() {
        return lxrdh;
    }

    public void setLxrdh(String lxrdh) {
        this.lxrdh = lxrdh;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getLxrxm() {
        return lxrxm;
    }

    public void setLxrxm(String lxrxm) {
        this.lxrxm = lxrxm;
    }

    public String getLxradress() {
        return lxradress;
    }

    public void setLxradress(String lxradress) {
        this.lxradress = lxradress;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOdnum() {
        return odnum;
    }

    public void setOdnum(String odnum) {
        this.odnum = odnum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getNcount() {
        return ncount;
    }

    public void setNcount(String ncount) {
        this.ncount = ncount;
    }

    public String getExtrafee() {
        return extrafee;
    }

    public void setExtrafee(String extrafee) {
        this.extrafee = extrafee;
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

        public String getZj() {
            return zj;
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
