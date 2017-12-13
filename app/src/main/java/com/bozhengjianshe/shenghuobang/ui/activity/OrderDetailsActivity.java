package com.bozhengjianshe.shenghuobang.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.OrderGoodsItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.OrderDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.MenuItem;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-22.
 */

public class OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.mi_name)
    MenuItem mi_name;
    @BindView(R.id.mi_phone)
    MenuItem mi_phone;
    @BindView(R.id.mi_addresss)
    MenuItem mi_addresss;
//    @BindView(R.id.tv_goods_name)
//    TextView tv_goods_name;
//    @BindView(R.id.tv_price)
//    TextView tv_price;
//    @BindView(R.id.tv_count)
//    TextView tv_count;
    @BindView(R.id.tv_real_pay)
    TextView tv_real_pay;
//    @BindView(R.id.iv_goods)
//    ImageView iv_goods;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    private OrderGoodsItemAdapter goodsItemAdapter;

    private String orderId;
    private String type;
    Call<SuperBean<OrderDetailBean>> call;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        goodsItemAdapter=new OrderGoodsItemAdapter(this);
        rv_list.setAdapter(goodsItemAdapter);
        try {
            orderId = getIntent().getExtras().getString("orderId");
            type = getIntent().getExtras().getString("type");
        } catch (Exception e) {
        }

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

//            ImageLoadedrManager.getInstance().display(this, orderDetailBean.getProducts().get(0).getProductImg(), iv_goods);
            mi_name.getRightText().setText(orderDetailBean.getReceiveName());
            mi_phone.getRightText().setText(orderDetailBean.getReceivePhone());
            mi_addresss.getRightText().setText(orderDetailBean.getReceiveAddress());
//            tv_goods_name.setText(orderDetailBean.getProducts().get(0).getProductName());
//            tv_price.setText("￥" + orderDetailBean.getProducts().get(0).getProductPrice());
//            tv_count.setText("x" + orderDetailBean.getProducts().get(0).getProductCount());
//            tv_real_pay.setText("￥" + orderDetailBean.getProducts().get(0).get());
            goodsItemAdapter.addList(orderDetailBean.getProducts());
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
            UIUtil.showToast("dingdanid为空");
            finish();
            return;
        }
        DialogUtils.showDialog(this, "加载中", false);
//        if (type.equals("rent")) {
        call = RestAdapterManager.getApi().getRentOrderDetails(orderId);
//        } else {
//            call = RestAdapterManager.getApi().getOrderDetails(orderId);
//        }


        call.enqueue(new JyCallBack<SuperBean<OrderDetailBean>>() {
            @Override
            public void onSuccess(Call<SuperBean<OrderDetailBean>> call, Response<SuperBean<OrderDetailBean>> response) {
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
            public void onError(Call<SuperBean<OrderDetailBean>> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast("获取数据异常");
            }

            @Override
            public void onError(Call<SuperBean<OrderDetailBean>> call, Response<SuperBean<OrderDetailBean>> response) {
                DialogUtils.closeDialog();
                UIUtil.showToast("获取数据异常");
            }
        });

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
        super.onDestroy();
    }
}
