package com.bozhengjianshe.shenghuobang.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.bean.AboutInfoBean;
import com.bozhengjianshe.shenghuobang.utils.SysUtils;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-27.
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.iv_store_name)
    TextView iv_store_name;
    Call<AboutInfoBean> getAbout;
    @BindView(R.id.tv_info)
    TextView tv_info;

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
        getData();
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
        title_view.setShowDefaultRightValue();
    }

    @Override
    protected void onDestroy() {
        if (getAbout != null) {
            getAbout.cancel();
        }
        super.onDestroy();
    }

    private void getData() {
        getAbout = RestAdapterManager.getApi().getAboutInfo();
        getAbout.enqueue(new JyCallBack<AboutInfoBean>() {
            @Override
            public void onSuccess(Call<AboutInfoBean> call, Response<AboutInfoBean> response) {
                setData(response.body());
            }

            @Override
            public void onError(Call<AboutInfoBean> call, Throwable t) {

            }

            @Override
            public void onError(Call<AboutInfoBean> call, Response<AboutInfoBean> response) {

            }
        });
    }

    private void setData(AboutInfoBean bean) {
        if (bean == null||bean.getDetail()==null) {
            return;
        }
        iv_store_name.setText(R.string.app_name);
        String content = "";
        content += bean.getDetail().getCompany() + "\n";
        content += bean.getDetail().getKeywords() + "\n";
        content += bean.getDetail().getServcietel() + "\n";
        content += bean.getDetail().getSeotitle() + "\n";
        content += bean.getDetail().getDescription() + "\n";
        content += bean.getDetail().getBeian() + "\n";
        tv_info.setText(content);
    }
}
