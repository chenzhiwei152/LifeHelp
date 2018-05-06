package com.bozhengjianshe.shenghuobang.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen.zhiwei on 2017-7-4.
 */

public class BuyOrderListItemBean implements Serializable {


    /**
     * lxrdh : 18888888888
     * count : 120
     * del : 1
     * lxrxm : 赵震
     * lxradress : 北京芍药居北里7号楼
     * addtime : 2018-04-14 09:04:53
     * name : null
     * odnum : 2018041414098765
     * id : 1
     * state : 2
     * detail : [{"zj":"15","dj":"5","num":"3","name":"金牌拖地"},{"zj":"15","dj":"5","num":"3","name":"金牌拖地"},{"zj":"15","dj":"5","num":"3","name":"金牌拖地"}]
     * ncount : 100
     * extrafee : 15
     */

    private String lxrdh;
    private int count;
    private int del;
    private String lxrxm;
    private String lxradress;
    private String addtime;
    private String name;
    private String odnum;
    private int id;
    private int state;
    private int ncount;
    private int extrafee;
    private List<DetailBean> detail;

    public String getLxrdh() {
        return lxrdh;
    }

    public void setLxrdh(String lxrdh) {
        this.lxrdh = lxrdh;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
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

    public int getNcount() {
        return ncount;
    }

    public void setNcount(int ncount) {
        this.ncount = ncount;
    }

    public int getExtrafee() {
        return extrafee;
    }

    public void setExtrafee(int extrafee) {
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
         * zj : 15
         * dj : 5
         * num : 3
         * name : 金牌拖地
         */

        private String zj;
        private String dj;
        private String num;
        private String name;

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
    }
}
