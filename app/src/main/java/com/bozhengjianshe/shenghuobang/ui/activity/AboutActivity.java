package com.bozhengjianshe.shenghuobang.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.utils.SysUtils;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-6-27.
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.tv_version)
    TextView tv_version;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        tv_version.setText("版本号V" + SysUtils.getVersionName(this));
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
        title_view.setTitle("关于");
    }

}
