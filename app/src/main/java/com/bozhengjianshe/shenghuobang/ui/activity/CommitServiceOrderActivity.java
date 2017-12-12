package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.ShoppingAddressListItemBean;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.utils.timepicker.TimePickerView;
import com.bozhengjianshe.shenghuobang.view.MenuItem;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.util.Date;

import butterknife.BindView;

import static com.bozhengjianshe.shenghuobang.base.Constants.ADD_REQUEST_CODE;

/**
 * Created by chen.zhiwei on 2017-12-12.
 */

public class CommitServiceOrderActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.bt_commit)
    Button bt_commit;
    @BindView(R.id.mi_reservation_project)
    MenuItem mi_reservation_project;
    @BindView(R.id.mi_reservation_time)
    MenuItem mi_reservation_time;
    @BindView(R.id.mi_name)
    MenuItem mi_name;
    @BindView(R.id.mi_phone)
    MenuItem mi_phone;
    @BindView(R.id.mi_addresss)
    MenuItem mi_addresss;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_commit_service;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        bt_commit.setOnClickListener(this);
        mi_reservation_project.setOnClickListener(this);
        mi_reservation_time.setOnClickListener(this);
        mi_name.setOnClickListener(this);
        mi_phone.setOnClickListener(this);
        mi_addresss.setOnClickListener(this);
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
        title_view.setTitle("预约");
        title_view.setShowDefaultRightValue();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_commit:
                //提交
                break;
            case R.id.mi_name:
            case R.id.mi_phone:
            case R.id.mi_addresss:
                //地址
                Intent intent = new Intent(this, ShoppingAddressActivity.class);
                intent.putExtra("type", "getAddress");
                startActivityForResult(intent, ADD_REQUEST_CODE);
                break;
            case R.id.mi_reservation_time:
                //时间
                DialogUtils.showTimePcikerDialog(this, TimePickerView.Type.YEAR_MONTH_DAY, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        mi_reservation_time.getRightText().setText(UIUtil.getTime(date));
                    }
                });

                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (resultCode == ADD_REQUEST_CODE) {
//获取收货地址
                setAddress((ShoppingAddressListItemBean) data.getExtras().getSerializable("addressBean"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setAddress(ShoppingAddressListItemBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getName()) && !TextUtils.isEmpty(bean.getPhone()) && !TextUtils.isEmpty(bean.getDetail())) {
            mi_name.getRightText().setText(bean.getName());
            mi_phone.getRightText().setText(bean.getPhone());
            mi_addresss.getRightText().setText(bean.getDetail());
//            ll_address.setVisibility(View.VISIBLE);
//            ll_add_addresss.setVisibility(View.GONE);
        } else {
            //
//            ll_address.setVisibility(View.GONE);
//            ll_add_addresss.setVisibility(View.VISIBLE);
            UIUtil.showToast("地址获取失败");
        }
    }
}
