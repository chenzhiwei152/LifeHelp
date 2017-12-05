package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;

/**
 * Created by chen.zhiwei on 2017-12-5.
 */

public class GoodsDetailRightFragment extends BaseFragment {
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_goodsdetail_right;
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
