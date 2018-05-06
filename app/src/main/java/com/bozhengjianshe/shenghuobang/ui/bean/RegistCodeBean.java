package com.bozhengjianshe.shenghuobang.ui.bean;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class RegistCodeBean {

    /**
     * state : 100
     * message : 短信发送成功
     * dxcode : 4146
     */

    private int state;
    private String message;
    private String dxcode;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDxcode() {
        return dxcode;
    }

    public void setDxcode(String dxcode) {
        this.dxcode = dxcode;
    }
}
