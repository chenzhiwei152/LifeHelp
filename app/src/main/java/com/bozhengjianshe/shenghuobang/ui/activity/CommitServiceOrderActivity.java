package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.ShoppingAddressListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.utils.timepicker.TimePickerView;
import com.bozhengjianshe.shenghuobang.view.MenuItem;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

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
    Call<SuperBean<String>> commitRentCall;
    private String orderId;
    private GoodsDetailBean goodsBean;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_commit_service;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            goodsBean = (GoodsDetailBean) bundle.getSerializable("detail");
        }
        bt_commit.setOnClickListener(this);
        mi_reservation_project.setOnClickListener(this);
        mi_reservation_time.setOnClickListener(this);
        mi_name.setOnClickListener(this);
        mi_phone.setOnClickListener(this);
        mi_addresss.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        setValueDefault();
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
                if (checkData() && !UIUtil.isFastDoubleClick()) {
                    commitRentOrder();
                }
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

    private boolean checkData() {
        if (TextUtils.isEmpty(mi_addresss.getRightText().getText())) {
            UIUtil.showToast("地址不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mi_reservation_time.getRightText().getText())) {
            UIUtil.showToast("时间不能为空");
            return false;
        }
        return true;
    }

    private void setValueDefault() {
        if (goodsBean != null) {
            mi_reservation_project.getRightText().setText(goodsBean.getType()+"");
        }
    }

    /**
     * 提交订单
     */
    private void commitRentOrder() {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast(R.string.net_state_error);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("receiveName", mi_name.getRightText().getText().toString());
        map.put("receivePhone", mi_phone.getRightText().getText().toString());
        map.put("receiveAddress", mi_addresss.getRightText().getText().toString());
        map.put("productCount", "1");
        map.put("serviceTime", mi_reservation_time.getRightText().getText().toString());
//        map.put("transportType", deliverytype + "");
        map.put("productId", goodsBean.getId() + "");
        map.put("productType", goodsBean.getType() + "");
        map.put("userid", BaseContext.getInstance().getUserInfo().userId);
        LogUtils.e(JSON.toJSONString(map));
        DialogUtils.showDialog(CommitServiceOrderActivity.this, "获取订单...", false);
        commitRentCall = RestAdapterManager.getApi().getRentOrder(map);
        commitRentCall.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    LogUtils.e(response.body().getMsg());
                    DialogUtils.closeDialog();
                    orderId = response.body().getData();
                    Intent intent = new Intent(CommitServiceOrderActivity.this, OrderDetailsActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast(t.getMessage());
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
                try {
                    UIUtil.showToast(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
