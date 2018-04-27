package com.bozhengjianshe.shenghuobang.ui.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/1.
 */

public class SuperGoodsListBean<T> implements Serializable {
    public int state;
    public String message;

    public SuperGoodsListBean() {
    }

    public int getCode() {
        return state;
    }

    public void setCode(int state) {
        this.state = state;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }

    public T getData() {
        return clist;
    }

    public void setData(T clist) {
        this.clist = clist;
    }

    public T clist;
}
