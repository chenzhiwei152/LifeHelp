package com.bozhengjianshe.shenghuobang.ui.bean;

/**
 * Created by Administrator on 2018/5/6 0006.
 */

public class CardCacheBean {

    /**
     * num : 3
     * id : 5
     * thumbnail : file/image/1.png
     * dj : 1.0
     * cname : 金牌服务
     */

    private int num=1;
    private String id;
    private String thumbnail;
    private String dj;
    private String zj;
    private String cname;

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
