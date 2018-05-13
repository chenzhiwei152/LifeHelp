package com.bozhengjianshe.shenghuobang.ui.bean;

/**
 * Created by chen.zhiwei on 2017-12-11.
 */

public class AllServiceTypeBean {



    private String name;
    private int rank;
    private int id;
    private int mark;
    private String linkurl;

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
