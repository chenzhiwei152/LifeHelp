package com.bozhengjianshe.shenghuobang.ui.activity;

import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class AddAddressSelfActivity extends BaseActivity {
    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_add_address_self;
    }

    @Override
    public void initViewsAndEvents() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public boolean isRegistEventBus() {
        return false;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {

    }

    @Override
    protected View isNeedLec() {
        return null;
    }
}
