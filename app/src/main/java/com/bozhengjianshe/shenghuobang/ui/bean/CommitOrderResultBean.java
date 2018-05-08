package com.bozhengjianshe.shenghuobang.ui.bean;

import java.util.List;

public class CommitOrderResultBean {


    /**
     * msg : 操作成功
     * alipay : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=ab472b2c55de6b8da&biz_content=%7B%22body%22%3A%22%E7%94%9F%E6%B4%BB%E9%82%A6%E2%80%94%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A%22201805080939165661%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%94%9F%E6%B4%BB%E9%82%A6%E2%80%94%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.0%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwww.laosijigzs.com%2Flife%2Fapi%2Fgetalipay.html&sign=Bl2TPnjZbk4aE7%2FVHqzVQqpZUfjCSMrHSqXDTB17TO3MXB2awmOCS0Qa44921uMsDU2W2IugzWTZnbciesyzyQqOJ6bStnVpPcfqT4Oje%2BcGV8%2BASAgaotcyam8HqPcIJVc6PAxOFtF0j%2F3D9IQWB3OA0eae5qWYW%2BQbw%2BPTQiq%2FpfLUi8VxVA3RWWsdz8HIXsZnE0OQ36Du1qg41bLk5iCklxEU76IyrKsieowN08mgdhwyCjelSJuRvFeTGdOpT%2FwkvbUSqJ7V%2F2uFCWX1aVv1irbO9IKWcICdruiI7QJW%2BfEZ6nM9%2FEgt02Jno9h2vZKuvx6%2BmLBwNWa6BhY1YA%3D%3D&sign_type=RSA2&timestamp=2018-05-08+09%3A39%3A16&version=1.0
     * state : 100
     * order : {"lxrdh":"13685965478","commodity":"1,2,","lxrsfzh":"","del":1,"managerid":0,"imgsfzfro":"","imgsfzrev":"","odnum":"201805080939165661","id":20,"state":1,"extrafee":0,"tpbz":"","modtime":0,"count":0,"statemark":"","quality":6,"lxrxm":"all咯","fczimglist":"","lxradress":"阿狸裤子我我我我我老K咯我我了了了家里好好好了了","membersid":0,"phone":"","lxrhyzk":0,"lxrxb":0,"detail":[{"zj":"15","dj":"5","num":"3","name":"银牌拖地","id":"1"},{"zj":"15","dj":"5","num":"3","name":"金牌拖地","id":"2"}],"time":1525743556237,"ncount":0,"age":"","mark":"","memberid":4}
     */

    private String msg;
    private String alipay;
    private String wxpay;
    private int state;
    private OrderBean order;

    public String getMsg() {
        return msg;
    }

    public String getWxpay() {
        return wxpay;
    }

    public void setWxpay(String wxpay) {
        this.wxpay = wxpay;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean {
        /**
         * lxrdh : 13685965478
         * commodity : 1,2,
         * lxrsfzh :
         * del : 1
         * managerid : 0
         * imgsfzfro :
         * imgsfzrev :
         * odnum : 201805080939165661
         * id : 20
         * state : 1
         * extrafee : 0
         * tpbz :
         * modtime : 0
         * count : 0
         * statemark :
         * quality : 6
         * lxrxm : all咯
         * fczimglist :
         * lxradress : 阿狸裤子我我我我我老K咯我我了了了家里好好好了了
         * membersid : 0
         * phone :
         * lxrhyzk : 0
         * lxrxb : 0
         * detail : [{"zj":"15","dj":"5","num":"3","name":"银牌拖地","id":"1"},{"zj":"15","dj":"5","num":"3","name":"金牌拖地","id":"2"}]
         * time : 1525743556237
         * ncount : 0
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
             * zj : 15
             * dj : 5
             * num : 3
             * name : 银牌拖地
             * id : 1
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
}
