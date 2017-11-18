package com.bozhengjianshe.shenghuobang.ui.bean;


import com.bozhengjianshe.shenghuobang.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chen.zhiwei on 2017-6-26.
 */

public class GoodsListBean extends ErrorBean implements Serializable {


    /**
     * data : {"banners":[{"id":1,"img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","type":2},{"id":2,"img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","type":2},{"id":3,"img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","type":2}],"recService":[{"id":1,"serviceName":"测试1","servicePrice":1,"serviceType":0,"isBanner":1,"serviceDetail":null,"serviceImg":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","seeType":1,"isRec":1},{"id":2,"serviceName":"测试1","servicePrice":1,"serviceType":0,"isBanner":1,"serviceDetail":null,"serviceImg":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","seeType":1,"isRec":1},{"id":3,"serviceName":"测试1","servicePrice":1,"serviceType":0,"isBanner":1,"serviceDetail":null,"serviceImg":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","seeType":1,"isRec":1}],"recGoods":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * banners : [{"id":1,"img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","type":2},{"id":2,"img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","type":2},{"id":3,"img":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","type":2}]
         * recService : [{"id":1,"serviceName":"测试1","servicePrice":1,"serviceType":0,"isBanner":1,"serviceDetail":null,"serviceImg":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","seeType":1,"isRec":1},{"id":2,"serviceName":"测试1","servicePrice":1,"serviceType":0,"isBanner":1,"serviceDetail":null,"serviceImg":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","seeType":1,"isRec":1},{"id":3,"serviceName":"测试1","servicePrice":1,"serviceType":0,"isBanner":1,"serviceDetail":null,"serviceImg":"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG","seeType":1,"isRec":1}]
         * recGoods : null
         */

        private Object recGoods;
        private List<BannersBean> banners;
        private List<RecServiceBean> recService;

        public Object getRecGoods() {
            return recGoods;
        }

        public void setRecGoods(Object recGoods) {
            this.recGoods = recGoods;
        }

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public List<RecServiceBean> getRecService() {
            return recService;
        }

        public void setRecService(List<RecServiceBean> recService) {
            this.recService = recService;
        }

        public static class BannersBean implements Serializable{
            /**
             * id : 1
             * img : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG
             * type : 2
             */

            private int id;
            private String img;
            private int type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class RecServiceBean implements Serializable{
            /**
             * id : 1
             * serviceName : 测试1
             * servicePrice : 1
             * serviceType : 0
             * isBanner : 1
             * serviceDetail : null
             * serviceImg : https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2390632991,220987804&fm=173&s=0FC0FC068F244C03984DA0D20000C0B7&w=639&h=216&img.JPEG
             * seeType : 1
             * isRec : 1
             */

            private int id;
            private String serviceName;
            private int servicePrice;
            private int serviceType;
            private int isBanner;
            private Object serviceDetail;
            private String serviceImg;
            private int seeType;
            private int isRec;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public int getServicePrice() {
                return servicePrice;
            }

            public void setServicePrice(int servicePrice) {
                this.servicePrice = servicePrice;
            }

            public int getServiceType() {
                return serviceType;
            }

            public void setServiceType(int serviceType) {
                this.serviceType = serviceType;
            }

            public int getIsBanner() {
                return isBanner;
            }

            public void setIsBanner(int isBanner) {
                this.isBanner = isBanner;
            }

            public Object getServiceDetail() {
                return serviceDetail;
            }

            public void setServiceDetail(Object serviceDetail) {
                this.serviceDetail = serviceDetail;
            }

            public String getServiceImg() {
                return serviceImg;
            }

            public void setServiceImg(String serviceImg) {
                this.serviceImg = serviceImg;
            }

            public int getSeeType() {
                return seeType;
            }

            public void setSeeType(int seeType) {
                this.seeType = seeType;
            }

            public int getIsRec() {
                return isRec;
            }

            public void setIsRec(int isRec) {
                this.isRec = isRec;
            }
        }
    }
}
