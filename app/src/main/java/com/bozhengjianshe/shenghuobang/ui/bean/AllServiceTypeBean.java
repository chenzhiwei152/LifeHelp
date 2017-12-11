package com.bozhengjianshe.shenghuobang.ui.bean;

/**
 * Created by chen.zhiwei on 2017-12-11.
 */

public class AllServiceTypeBean {

    /**
     * id : 75
     * parentId : 0
     * text : 装修装饰
     * deleteFlag : 0
     * isLeaf : 0
     * children : null
     */

    private int id;
    private int parentId;
    private String text;
    private int deleteFlag;
    private int isLeaf;
    private String children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(int isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }
}
