package com.bozhengjianshe.shenghuobang.ui.listerner;


import com.bozhengjianshe.shenghuobang.ui.bean.ShoppingAddressListItemBean;

/**
 * Created by chen.zhiwei on 2017-6-27.
 */

public interface ShoppingAddressItemOnClickListerner {
    void onClick(ShoppingAddressListItemBean bean);
    void onDeleteListerner(ShoppingAddressListItemBean bean);
}
