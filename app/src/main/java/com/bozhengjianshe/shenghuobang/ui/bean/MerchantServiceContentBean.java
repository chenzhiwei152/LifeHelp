package com.bozhengjianshe.shenghuobang.ui.bean;

import java.util.List;

public class MerchantServiceContentBean {

    /**
     * ejfl : ["管道疏通","水路维修","电路维修","卫浴花撒"]
     * state : 100
     * message : 查询服务商服务内容成功
     */

    private int state;
    private String message;
    private List<String> ejfl;

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

    public List<String> getEjfl() {
        return ejfl;
    }

    public void setEjfl(List<String> ejfl) {
        this.ejfl = ejfl;
    }
}
