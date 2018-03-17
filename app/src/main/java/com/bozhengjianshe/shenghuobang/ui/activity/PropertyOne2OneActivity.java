package com.bozhengjianshe.shenghuobang.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.PropertyOne2OneListAdapter;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/17.
 */

public class PropertyOne2OneActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.title_view)
    TitleBar title_view;
    private PropertyOne2OneListAdapter propertyOne2OneListAdapter;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_property_one_2_one;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        propertyOne2OneListAdapter = new PropertyOne2OneListAdapter(this);
        rv_list.setAdapter(propertyOne2OneListAdapter);
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
        title_view.setTitle("物业一对一");
    }
}
