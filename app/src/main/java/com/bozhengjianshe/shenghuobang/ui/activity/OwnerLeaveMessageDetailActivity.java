package com.bozhengjianshe.shenghuobang.ui.activity;

import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * 留言
 * Created by Administrator on 2018/3/18.
 */

public class OwnerLeaveMessageDetailActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_owner_leave_message_detail;
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
        title_view.setTitle("业主留言反馈");
        title_view.setShowDefaultRightValue();
    }
}
