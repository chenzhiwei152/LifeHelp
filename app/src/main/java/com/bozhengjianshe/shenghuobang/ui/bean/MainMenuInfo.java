package com.bozhengjianshe.shenghuobang.ui.bean;

import android.os.Bundle;

/**
 * Created by liu.zhenrong on 2016/6/21.
 */
public class MainMenuInfo {
    public int iconID;//菜单图标id
    public String menuName;//菜单名称
    public Class aClass;//点击需要跳转的class
    public Bundle bundle;//跳转携带的参数

    public MainMenuInfo(String menuName, int iconID, Class aClass, Bundle bundle) {
        this.aClass = aClass;
        this.bundle = bundle;
        this.iconID = iconID;
        this.menuName = menuName;
    }
}
