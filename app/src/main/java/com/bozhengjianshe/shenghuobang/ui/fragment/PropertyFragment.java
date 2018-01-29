package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;

/**
 * Created by chen.zhiwei on 2018-1-29.
 */

public class PropertyFragment extends BaseFragment {
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_property;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    @Override
    public boolean isRegistEventBus() {
        return false;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {

    }
}
