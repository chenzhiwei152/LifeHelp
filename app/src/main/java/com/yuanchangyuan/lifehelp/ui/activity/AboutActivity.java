package com.yuanchangyuan.lifehelp.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.yuanchangyuan.lifehelp.R;
import com.yuanchangyuan.lifehelp.base.BaseActivity;
import com.yuanchangyuan.lifehelp.base.EventBusCenter;
import com.yuanchangyuan.lifehelp.utils.SysUtils;
import com.yuanchangyuan.lifehelp.view.TitleBar;

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
