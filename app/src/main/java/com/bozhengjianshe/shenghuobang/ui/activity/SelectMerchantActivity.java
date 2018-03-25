package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * 选择服务商
 * Created by Administrator on 2018/3/25.
 */

public class SelectMerchantActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.iv_join_service)
    ImageView iv_join_service;
    @BindView(R.id.iv_join_property)
    ImageView iv_join_property;
    @BindView(R.id.iv_join_supply)
    ImageView iv_join_supply;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_select_mercahnt;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        iv_join_service.setOnClickListener(this);
        iv_join_property.setOnClickListener(this);
        iv_join_supply.setOnClickListener(this);
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

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("选择服务商");
        title_view.setShowDefaultRightValue();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_join_service:
                startActivity(new Intent(this, JoinsUsActivity.class));
                finish();
                break;
            case R.id.iv_join_property:
                break;
            case R.id.iv_join_supply:
                break;
        }
    }
}
