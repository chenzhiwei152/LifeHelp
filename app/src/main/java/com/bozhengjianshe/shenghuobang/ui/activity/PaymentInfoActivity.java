package com.bozhengjianshe.shenghuobang.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/17.
 */

public class PaymentInfoActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.tv_pay_type)
    TextView tv_pay_type;
    @BindView(R.id.tv_pay_money)
    TextView tv_pay_money;
    @BindView(R.id.confirm)
    Button confirm;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_payment_info;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        title_view.setTitle("缴费信息");
        title_view.setShowDefaultRightValue();
    }
}
