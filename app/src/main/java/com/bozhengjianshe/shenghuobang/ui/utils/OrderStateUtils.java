package com.bozhengjianshe.shenghuobang.ui.utils;

import com.bozhengjianshe.shenghuobang.base.Constants;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class OrderStateUtils {
    public static String getOrderStateDescribe(int state) {
        String describe = "";
        switch (state) {
            case Constants.STATE_ONE:
                describe = "派单中";
                break;
            case Constants.STATE_TWO:
                describe = "服务中";
                break;
            case Constants.STATE_THREE:
                describe = "待付款";
                break;
            case Constants.STATE_FOUR:
                describe = "待评价";
                break;
            case Constants.STATE_FIVE:
                describe = "结束";
                break;
        }
        return describe;
    }
}
