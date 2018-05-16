package com.bozhengjianshe.shenghuobang.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.OrderGoodsItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.CommitOrderResultBean;
import com.bozhengjianshe.shenghuobang.ui.bean.OrderDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperOrderBean;
import com.bozhengjianshe.shenghuobang.ui.utils.OrderStateUtils;
import com.bozhengjianshe.shenghuobang.ui.utils.ShoppingCardsUtils;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.MenuItem;
import com.bozhengjianshe.shenghuobang.view.MyDialog;
import com.bozhengjianshe.shenghuobang.view.TitleBar;
import com.jpay.JPay;

import java.io.IOException;

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
    @BindView(R.id.ll_button)
    LinearLayout ll_button;
    private OrderGoodsItemAdapter goodsItemAdapter;
    private int payChannel = 1;//支付通道1为支付宝2为微信
    private Call<SuperBean<String>> getRsaOrderCall;
    private String orderId;
    private String type;
    Call<SuperOrderBean<OrderDetailBean>> call;
    Call<CommitOrderResultBean> commitRentCall;
    OrderDetailBean orderDetailBean;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setNestedScrollingEnabled(false);
        goodsItemAdapter = new OrderGoodsItemAdapter(this);
        rv_list.setAdapter(goodsItemAdapter);
        try {
            orderId = getIntent().getExtras().getString("orderId");
            type = getIntent().getExtras().getString("type");
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(orderId)) {
            UIUtil.showToast("订单id为空");
            finish();
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
            tv_state.setText(OrderStateUtils.getOrderStateDescribe(orderDetailBean.getState()));
            tv_state_title.setText("订单号：" + orderDetailBean.getOdnum());
            tv_order_time.setText(UIUtil.timeStamp2Date(orderDetailBean.getTime() + ""));
//            tv_deposit.setText(orderDetailBean.get());//定金

//            double price = 0.00f;
//            for (int i = 0; i < orderDetailBean.getDetail().size(); i++) {
////                if (orderDetailBean.getDetail().get(i).getLb() == 2) {
//                //1   服务  2商品
//                price += (orderDetailBean.getDetail().get(i).getZj());
////                } else {
////                    price += orderDetailBean.getDetail().get(i).getFee();
////                }
//            }
            tv_real_pay.setText(getResources().getString(R.string.money) + String.format("%.2f", orderDetailBean.getNcount()) + "");
            if (orderDetailBean.getState() == Constants.STATE_ONE) {
                if (orderDetailBean.getPayment() == 2) {
                    //已付款
                    bt_cancel.setVisibility(View.GONE);
                    bt_pay.setVisibility(View.GONE);
                    ll_button.setVisibility(View.GONE);
                } else {
                    bt_cancel.setVisibility(View.VISIBLE);
                    bt_pay.setVisibility(View.VISIBLE);
                    ll_button.setVisibility(View.VISIBLE);
                }
            } else if (orderDetailBean.getState() == Constants.STATE_THREE) {
                bt_cancel.setVisibility(View.GONE);
                bt_pay.setVisibility(View.VISIBLE);
                ll_button.setVisibility(View.VISIBLE);
            } else {
                bt_cancel.setVisibility(View.GONE);
                bt_pay.setVisibility(View.GONE);
                ll_button.setVisibility(View.GONE);
            }
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
                            orderDetailBean = response.body().getData();
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
        RequestBody body = new FormBody.Builder()
                .add("memberid", BaseContext.getInstance().getUserInfo().id)
                .add("orderid", orderId)
                .add("state", Constants.STATE_FIVE + "")
                .build();
        Call<SuperOrderBean<OrderDetailBean>> quitOrder = RestAdapterManager.getApi().quitOrder(body);
        quitOrder.enqueue(new JyCallBack<SuperOrderBean<OrderDetailBean>>() {
            @Override
            public void onSuccess(Call<SuperOrderBean<OrderDetailBean>> call, Response<SuperOrderBean<OrderDetailBean>> response) {
                DialogUtils.closeDialog();
                UIUtil.showToast(response.body().getMsg());
                initDate(response.body().getData());
            }

            @Override
            public void onError(Call<SuperOrderBean<OrderDetailBean>> call, Throwable t) {
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<SuperOrderBean<OrderDetailBean>> call, Response<SuperOrderBean<OrderDetailBean>> response) {
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
                    payChannel = 4;
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
                    payChannel = 4;
                }
            }
        });
        offLineCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    wxCheck.setChecked(false);
                    aliCheck.setChecked(false);
                    payChannel = 3;
                } else {
                    offLineCheck.setChecked(false);
                    payChannel = 4;
                }
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UIUtil.isFastDoubleClick()) {
                    if (payChannel == 4) {
                        UIUtil.showToast("请选择支付方式");
                    } else if (payChannel == 3) {
                        myDialog.dismiss();
//                        goNext();
                    } else {
                        commitRentOrder((payChannel) + "");
                        myDialog.dismiss();
                    }

                }

            }
        });

    }


    /**
     * 提交订单
     */
    private void commitRentOrder(final String type) {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast(R.string.net_state_error);
            return;
        }
        RequestBody formBody;
        if (orderDetailBean.getState() == Constants.STATE_THREE) {
            formBody = new FormBody.Builder()
                    .add("orderid", orderDetailBean.getId() + "")
                    .add("mark", type)
                    .add("memberid", BaseContext.getInstance().getUserInfo().id)
                    .build();
            commitRentCall = RestAdapterManager.getApi().getRentOrderAgain(formBody);
        } else {
            formBody = new FormBody.Builder()
                    .add("detail", JSON.toJSONString(ShoppingCardsUtils.changeCommitBean1(orderDetailBean.getDetail())))
                    .add("mark", type)
                    .add("memberid", BaseContext.getInstance().getUserInfo().id)
                    .build();
            commitRentCall = RestAdapterManager.getApi().getRentOrder(formBody);
        }

        LogUtils.e(JSON.toJSONString(formBody));
        DialogUtils.showDialog(OrderDetailsActivity.this, "获取订单...", false);

        commitRentCall.enqueue(new JyCallBack<CommitOrderResultBean>() {
            @Override
            public void onSuccess(Call<CommitOrderResultBean> call, Response<CommitOrderResultBean> response) {
                DialogUtils.closeDialog();
//                UIUtil.showToast(response.body().getMsg());
                if (response != null && response.body() != null && response.body().getState() == Constants.successCode) {
                    if (response.body().getOrder() != null) {

                        orderId = response.body().getOrder().getId() + "";
                    }
                    pay((type.equals("1") ? response.body().getAlipay() : response.body().getWxpay()));
//
                }
            }

            @Override
            public void onError(Call<CommitOrderResultBean> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast(t.getMessage());
            }

            @Override
            public void onError(Call<CommitOrderResultBean> call, Response<CommitOrderResultBean> response) {
                DialogUtils.closeDialog();
                try {
                    UIUtil.showToast(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                    UIUtil.showToast("支付失败>");
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
                    UIUtil.showToast("支付失败>");
                }

                @Override
                public void onPayCancel() {
                    DialogUtils.closeDialog();
                    UIUtil.showToast("取消了支付");
                }
            });
        }

    }

    private void goNext() {
        loadData();
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
