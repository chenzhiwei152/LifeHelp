package com.bozhengjianshe.shenghuobang.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.ClearingInspectionListAdapter;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * 保洁巡检
 * Created by chen.zhiwei on 2018-3-20.
 */

public class ClearingInspectionActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.title_view)
    TitleBar title_view;
    private ClearingInspectionListAdapter inspectionListAdapter;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_property_one_2_one;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        inspectionListAdapter = new ClearingInspectionListAdapter(this);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setAdapter(inspectionListAdapter);
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
        title_view.setTitle("保洁巡检");
    }
}
