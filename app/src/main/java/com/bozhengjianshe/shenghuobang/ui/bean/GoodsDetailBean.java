package com.bozhengjianshe.shenghuobang.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/9.
 */

public class GoodsDetailBean implements Serializable{


    /**
     * id : 11
     * images : [{"id":37,"url":"http://47.92.24.138:8080/upload/goodsImg/20171211134148241.jpg","productId":11,"productType":2},{"id":38,"url":"http://47.92.24.138:8080/upload/goodsImg/20171211134148366.jpg","productId":11,"productType":2},{"id":39,"url":"http://47.92.24.138:8080/upload/goodsImg/20171211134148429.jpg","productId":11,"productType":2}]
     * name : 服务测试反反复复
     * price : 234
     * img : http://47.92.24.138:8080/upload/goodsImg/20171211134148241.jpg
     * detailUrl : http://47.92.24.138:8080/controller/service/getServiceDetailHtml?id=11
     * type : 2
     */

    private int id;
    private String name;
    private int price;
    private String img;
    private String detailUrl;
    private int type;
    private List<ImagesBean> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean implements Serializable{
        /**
         * id : 37
         * url : http://47.92.24.138:8080/upload/goodsImg/20171211134148241.jpg
         * productId : 11
         * productType : 2
         */

        private int id;
        private String url;
        private int productId;
        private int productType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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
    }
}
