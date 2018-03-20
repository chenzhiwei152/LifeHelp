package com.bozhengjianshe.shenghuobang.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * 巡检详情
 * Created by chen.zhiwei on 2018-3-20.
 */

public class InspectionDetailActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.title_view)
    TitleBar title_view;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_inspection_detail;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
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

    private void initTitle() {
        title_view.setShowDefaultRightValue();
        title_view.setTitle("巡检信息详情");
    }
}
