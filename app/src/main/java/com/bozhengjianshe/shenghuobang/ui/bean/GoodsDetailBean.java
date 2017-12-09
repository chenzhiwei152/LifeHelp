package com.bozhengjianshe.shenghuobang.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/9.
 */

public class GoodsDetailBean implements Serializable{


    /**
     * id : 5
     * images : []
     * name : 测试服务1
     * price : 121
     * img : http://47.92.24.138:8080/upload/goodsImg/20171209110834241.jpg
     * detailUrl : http://47.92.24.138:8080/controller/service/getServiceDetailHtml?id=5
     * type : 2
     */

    private int id;
    private String name;
    private int price;
    private String img;
    private String detailUrl;
    private int type;
    private List<String> images;

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
