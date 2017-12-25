package com.bozhengjianshe.shenghuobang.ui.activity;

import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.view.MenuItem;
import com.bozhengjianshe.shenghuobang.view.MenuItemEdittext;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-12-25.
 */

public class JoinsUsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_view)
    TitleBar title_view;

    @BindView(R.id.mi_principal)
    MenuItemEdittext mi_principal;
    @BindView(R.id.mi_tel)
    MenuItemEdittext mi_tel;
    @BindView(R.id.mi_address)
    MenuItemEdittext mi_address;
    @BindView(R.id.mi_serviced)
    MenuItem mi_serviced;
    @BindView(R.id.mi_service_content)
    MenuItem mi_service_content;
    @BindView(R.id.mi_service_range)
    MenuItem mi_service_range;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_join_us;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        mi_principal.setOnClickListener(this);
        mi_tel.setOnClickListener(this);
        mi_address.setOnClickListener(this);
        mi_serviced.setOnClickListener(this);
        mi_service_content.setOnClickListener(this);
        mi_service_range.setOnClickListener(this);
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
        title_view.setTitle("加入我们");
        title_view.setShowDefaultRightValue();
        title_view.addAction(new TitleBar.TextAction("提交审核") {
            @Override
            public void performAction(View view) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mi_service_range:

                break;
            case R.id.mi_service_content:
                break;
        }

    }
}
