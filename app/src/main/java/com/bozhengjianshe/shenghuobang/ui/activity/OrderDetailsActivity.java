package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.OrderGoodsItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.OrderDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperOrderBean;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.MenuItem;
import com.bozhengjianshe.shenghuobang.view.MyDialog;
import com.bozhengjianshe.shenghuobang.view.TitleBar;
import com.jpay.JPay;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-22.
 */

public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.mi_name)
    MenuItem mi_name;
    @BindView(R.id.mi_phone)
    MenuItem mi_phone;
    @BindView(R.id.mi_addresss)
    MenuItem mi_addresss;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.tv_order_time)
    TextView tv_order_time;
    @BindView(R.id.tv_deposit)
    TextView tv_deposit;
    @BindView(R.id.tv_real_pay)
    TextView tv_real_pay;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.bt_cancel)
    Button bt_cancel;
    @BindView(R.id.bt_pay)
    Button bt_pay;
    @BindView(R.id.tv_state_title)
    TextView tv_state_title;
    private OrderGoodsItemAdapter goodsItemAdapter;
    private int payChannel = 1;//支付通道0为支付宝1为微信
    private Call<SuperBean<String>> getRsaOrderCall;
    private String orderId;
    private String type;
    Call<SuperOrderBean<OrderDetailBean>> call;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        goodsItemAdapter = new OrderGoodsItemAdapter(this);
        rv_list.setAdapter(goodsItemAdapter);
        try {
            orderId = getIntent().getExtras().getString("orderId");
            type = getIntent().getExtras().getString("type");
        } catch (Exception e) {
        }
        bt_pay.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        getOrderDetail();
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
     * 配置信息接口
     */
    private void initDate(OrderDetailBean orderDetailBean) {
        if (orderDetailBean != null) {

            mi_name.getRightText().setText(orderDetailBean.getLxrxm());
            mi_phone.getRightText().setText(orderDetailBean.getLxrdh());
            mi_addresss.getRightText().setText(orderDetailBean.getLxradress());
            tv_state.setText(orderDetailBean.getState() + "");
            tv_state_title.setText("订单号：" + orderDetailBean.getOdnum());
            tv_order_time.setText(UIUtil.timeStamp2Date(orderDetailBean.getTime() + ""));
//            tv_deposit.setText(orderDetailBean.get());//定金
            tv_real_pay.setText(orderDetailBean.getExtrafee() + "");
            goodsItemAdapter.addList(orderDetailBean.getDetail());
        }
    }

    /**
     * 获取数据接口
     */
    private void getOrderDetail() {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast(R.string.net_state_error);
            return;
        }
        if (TextUtils.isEmpty(orderId)) {
            UIUtil.showToast("订单id为空");
            finish();
            return;
        }
        RequestBody body = new FormBody.Builder()
                .add("memberid", BaseContext.getInstance().getUserInfo().id)
                .add("orderid", orderId)
                .build();
        DialogUtils.showDialog(this, "加载中", false);
        call = RestAdapterManager.getApi().getRentOrderDetails(body);


        call.enqueue(new JyCallBack<SuperOrderBean<OrderDetailBean>>() {
            @Override
            public void onSuccess(Call<SuperOrderBean<OrderDetailBean>> call, Response<SuperOrderBean<OrderDetailBean>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null) {
                    if (response.body().getCode() == Constants.successCode) {
                        try {
                            initDate(response.body().getData());
                        } catch (Exception e) {
                        }

                    } else {
                        UIUtil.showToast(response.body().getMsg());
                    }
                } else {
                    UIUtil.showToast("获取数据异常");
                }
            }

            @Override
            public void onError(Call<SuperOrderBean<OrderDetailBean>> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast("获取数据异常");
            }

            @Override
            public void onError(Call<SuperOrderBean<OrderDetailBean>> call, Response<SuperOrderBean<OrderDetailBean>> response) {
                DialogUtils.closeDialog();
                UIUtil.showToast("获取数据异常");
            }
        });

    }

    /**
     * 取消订单
     */
    private void quitOrder() {
        DialogUtils.showDialog(OrderDetailsActivity.this, "", false);
        Call<SuperBean<OrderDetailBean>> quitOrder = RestAdapterManager.getApi().quitOrder(orderId);
        quitOrder.enqueue(new JyCallBack<SuperBean<OrderDetailBean>>() {
            @Override
            public void onSuccess(Call<SuperBean<OrderDetailBean>> call, Response<SuperBean<OrderDetailBean>> response) {
                DialogUtils.closeDialog();
                UIUtil.showToast(response.body().getMsg());
                finish();
            }

            @Override
            public void onError(Call<SuperBean<OrderDetailBean>> call, Throwable t) {
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<SuperBean<OrderDetailBean>> call, Response<SuperBean<OrderDetailBean>> response) {
                DialogUtils.closeDialog();
            }
        });
    }

    private MyDialog myDialog;

    /**
     * 弹出dialog
     *
     * @param view
     */
    private void showdialog(View view) {

        myDialog = new MyDialog(this, 0, 0, view, R.style.dialog);
        myDialog.show();
        myDialog.setCancelable(false);
    }

    /**
     * 支付方式
     */
    public void payStyleDialog() {

        View view = View.inflate(OrderDetailsActivity.this, R.layout.dialog_pay_type, null);
        showdialog(view);


        final CheckBox aliCheck = (CheckBox) view.findViewById(R.id.cb_ali);
        final CheckBox wxCheck = (CheckBox) view.findViewById(R.id.cb_wx);
        final CheckBox offLineCheck = (CheckBox) view.findViewById(R.id.cb_off);
        final Button commit = (Button) view.findViewById(R.id.bt_commit);

        aliCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    wxCheck.setChecked(false);
                    offLineCheck.setChecked(false);
                    payChannel = 1;
                } else {
                    aliCheck.setChecked(false);
                    payChannel = 3;
                }
            }
        });
        wxCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    aliCheck.setChecked(false);
                    offLineCheck.setChecked(false);
                    payChannel = 2;
                } else {
                    wxCheck.setChecked(false);
                    payChannel = 3;
                }
            }
        });
        offLineCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    wxCheck.setChecked(false);
                    aliCheck.setChecked(false);
                    payChannel = 4;
                } else {
                    offLineCheck.setChecked(false);
                    payChannel = 3;
                }
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UIUtil.isFastDoubleClick()) {
                    if (payChannel == 3) {
                        UIUtil.showToast("请选择支付方式");
                    } else if (payChannel == 4) {
                        myDialog.dismiss();
                        goNext();
                    } else {
                        getRSAOrderInfo();
                        myDialog.dismiss();
                    }
                }

            }
        });

    }

    private void goNext() {
        if (!TextUtils.isEmpty(orderId)) {
            Intent intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtra("orderId", orderId);
//            intent.putExtra("type", tag);
            startActivity(intent);
            finish();
        } else {
            UIUtil.showToast("订单id为空");
        }

    }


    /**
     * 获取加密订单信息
     */
    private void getRSAOrderInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("userId", BaseContext.getInstance().getUserInfo().id);
        map.put("payType", payChannel + "");
        DialogUtils.showDialog(OrderDetailsActivity.this, "加载...", false);
        getRsaOrderCall = RestAdapterManager.getApi().getRsaOrderInfo(map);
        getRsaOrderCall.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    LogUtils.e(response.body().getMsg());
                    pay(response.body().getData());
                } else {
                    UIUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast("支付失败");
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
                UIUtil.showToast("支付失败");
            }
        });
    }

    /**
     * 支付
     *
     * @param string
     */
    private void pay(String string) {
        if (payChannel == 0) {
            JPay.getIntance(this).toPay(JPay.PayMode.ALIPAY, string, new JPay.JPayListener() {
                @Override
                public void onPaySuccess() {
                    DialogUtils.closeDialog();
                    goNext();
                    UIUtil.showToast("支付成功");
                }

                @Override
                public void onPayError(int error_code, String message) {
                    DialogUtils.closeDialog();
                    UIUtil.showToast("支付失败>" + error_code + " " + message);
                }

                @Override
                public void onPayCancel() {
                    DialogUtils.closeDialog();
                    UIUtil.showToast("取消了支付");
                }
            });
        } else if (payChannel == 1) {
            JPay.getIntance(this).toPay(JPay.PayMode.WXPAY, string, new JPay.JPayListener() {
                @Override
                public void onPaySuccess() {
                    DialogUtils.closeDialog();
                    goNext();
                    UIUtil.showToast("支付成功");
                }

                @Override
                public void onPayError(int error_code, String message) {
                    DialogUtils.closeDialog();
                    UIUtil.showToast("支付失败>" + error_code + " " + message);
                }

                @Override
                public void onPayCancel() {
                    DialogUtils.closeDialog();
                    UIUtil.showToast("取消了支付");
                }
            });
        }

    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("订单详情");
        title_view.setShowDefaultRightValue();
    }


    @Override
    protected void onDestroy() {
        if (call != null) {
            call.cancel();
        }
        if (getRsaOrderCall != null) {
            getRsaOrderCall.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pay:
                payStyleDialog();
                break;
            case R.id.bt_cancel:
                quitOrder();
                break;
        }
    }
}
