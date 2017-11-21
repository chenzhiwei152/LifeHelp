package com.bozhengjianshe.shenghuobang.ui.activity;

import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-11-21.
 */

public class EditActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle("修改用户名");
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
    private void initTitle(String title) {
        title_view.setLeftImageResource(R.mipmap.ic_title_back);
        title_view.setTitle(title);
//        title_view.setLeftText("返回");
//        title_view.setLeftTextColor(Color.WHITE);
        title_view.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        title_view.setBackgroundColor(getResources().getColor(R.color.color_ff6900));
        title_view.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {

            }
        });
        title_view.setImmersive(true);


    }
}
