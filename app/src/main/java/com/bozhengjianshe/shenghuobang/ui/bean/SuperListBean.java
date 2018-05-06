package com.bozhengjianshe.shenghuobang.ui.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/1.
 */

public class SuperListBean<T> implements Serializable {
    public int state;
    public String message;

    public SuperListBean() {
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
        return list;
    }

    public void setData(T list) {
        this.list = list;
    }

    public T list;
}
