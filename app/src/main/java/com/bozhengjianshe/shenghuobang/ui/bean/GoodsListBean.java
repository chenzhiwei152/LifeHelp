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

        private List<RecGoodsBean> recGoods;
        private List<BannersBean> banners;
        private List<RecServiceBean> recService;
        private List<RecServiceBean> newDiscount;

        public List<RecServiceBean> getNewDiscount() {
            return newDiscount;
        }

        public void setNewDiscount(List<RecServiceBean> newDiscount) {
            this.newDiscount = newDiscount;
        }

        public List<RecGoodsBean> getRecGoods() {
            return recGoods;
        }

        public void setRecGoods(List<RecGoodsBean> recGoods) {
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
        public static class RecGoodsBean implements Serializable{

            /**
             * id : 8
             * goodsName : 商品2
             * goodsPrice : 255
             * originalPrice : 234
             * profit : 21
             * goodsType : 0
             * goodsDetail : <p><img src="/ueditor/jsp/upload/image/20171209/1512788938147030202.jpg_800.jpg" title="1512788938147030202.jpg"/></p><p><img src="/ueditor/jsp/upload/image/20171209/1512788938069044289.jpg_800.jpg" title="1512788938069044289.jpg"/></p><p><img src="/ueditor/jsp/upload/image/20171209/1512788938147030678.jpg_800.jpg" title="1512788938147030678.jpg"/></p><p><img src="/ueditor/jsp/upload/image/20171209/1512788938554029599.jpg_800.jpg" title="1512788938554029599.jpg"/></p><p><br/></p>
             * stock : 332
             * isBanner : 0
             * goodsImg : http://47.92.24.138:8080/upload/goodsImg/20171209110834241.jpg
             * isRec : 1
             * serviceProvider : 王五 134658869434
             */

            private int id;
            private String goodsName;
            private int goodsPrice;
            private int originalPrice;
            private int profit;
            private int goodsType;
            private String goodsDetail;
            private int stock;
            private int isBanner;
            private String goodsImg;
            private int isRec;
            private String serviceProvider;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(int originalPrice) {
                this.originalPrice = originalPrice;
            }

            public int getProfit() {
                return profit;
            }

            public void setProfit(int profit) {
                this.profit = profit;
            }

            public int getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(int goodsType) {
                this.goodsType = goodsType;
            }

            public String getGoodsDetail() {
                return goodsDetail;
            }

            public void setGoodsDetail(String goodsDetail) {
                this.goodsDetail = goodsDetail;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public int getIsBanner() {
                return isBanner;
            }

            public void setIsBanner(int isBanner) {
                this.isBanner = isBanner;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public int getIsRec() {
                return isRec;
            }

            public void setIsRec(int isRec) {
                this.isRec = isRec;
            }

            public String getServiceProvider() {
                return serviceProvider;
            }

            public void setServiceProvider(String serviceProvider) {
                this.serviceProvider = serviceProvider;
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
            private String serviceDetail;
            private String serviceImg;
            private int seeType;
            private int isRec;
            private String isDiscount;
            private String discountPrice;

            public String getIsDiscount() {
                return isDiscount;
            }

            public void setIsDiscount(String isDiscount) {
                this.isDiscount = isDiscount;
            }

            public String getDiscountPrice() {
                return discountPrice;
            }

            public void setDiscountPrice(String discountPrice) {
                this.discountPrice = discountPrice;
            }

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

            public String getServiceDetail() {
                return serviceDetail;
            }

            public void setServiceDetail(String serviceDetail) {
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
