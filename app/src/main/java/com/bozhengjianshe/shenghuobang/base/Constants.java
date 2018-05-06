package com.bozhengjianshe.shenghuobang.base;

/**
 * Created by sun.luwei on 2016/12/20.
 */

public class Constants {

    public static final int AddressUpdateSuccess = 0x10;
    public static final String APP_KEY = "c3c5a51f3a4c70827523f8";
    public static int ADD_REQUEST_CODE = 11;
    public static int successCode = 100;
    public static String typeService = "2";
    public static String typeGoods = "1";
    public static String homeTypeTag = "homeTypeTag";

    public static class ErrorCode {
        public static String check_code = "400001039";//验证码错误
    }

    public static final int SHOW_CHECK_CODE = 0x20;//显示验证码
    public static final int HIDE_CHECK_CODE = 0x21;//隐藏证码
    public static final int REGIST_SUCCESS = 0x22;//注册成功
    public static final int LOGIN_FAILURE = 0x40;//登录失败
    public static final int LOGIN_SUCCESS = 0x41;//登录失败
    public static final int CHANGE_PASSWORD_SUCCESS = 0x23;//修改密码成功
    public static final int PAY_MEMBER_SUCCESS = 0x24;//修改密码成功
    public static final int UPDA_GOODS_DETAIL_H5 = 0x25;//更新详情页h5
    public static final int UPDA_CARD_GOODS_SELECTED = 0x26;//更新购物车选中的商品
    public static final int UPDA_DETAIL_WEBVIEW_HEIGHT = 0x27;//更新购物车选中的商品
    public static final int ADD_TO_CARD = 0x28;//加入购入车
    public static final int UPDATE_CARD_PRICE = 0x29;//更新购物车里面的价格
    public static final int LOCATION_CITY_SUCCESS = 0x30;//定位城市
    public static final int UPDATE_COLLECTION_SUCCESS = 0x31;//定位城市


}
