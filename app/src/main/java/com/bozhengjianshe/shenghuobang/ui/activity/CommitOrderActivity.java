package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.ShoppingAddressListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.MenuItem;
import com.bozhengjianshe.shenghuobang.view.MyDialog;
import com.bozhengjianshe.shenghuobang.view.TitleBar;
import com.jpay.JPay;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

import static com.bozhengjianshe.shenghuobang.base.Constants.ADD_REQUEST_CODE;


/**
 * 提交订单
 * Created by chen.zhiwei on 2017-6-21.
 */

public class CommitOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.tv_goods_num)
    TextView tvNum;
    @BindView(R.id.btn_des_goods_num)
    TextView tvDes;
    @BindView(R.id.btn_add_goods_num)
    TextView tvIns;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_price_all)
    TextView tv_price_all;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.mi_name)
    MenuItem mi_name;
    @BindView(R.id.mi_phone)
    MenuItem mi_phone;
    @BindView(R.id.mi_addresss)
    MenuItem mi_addresss;

    @BindView(R.id.bt_commit)
    Button bt_commit;
    @BindView(R.id.rl_number)
    RelativeLayout rl_number;
    @BindView(R.id.tv_num_price)
    TextView tv_num_price;
    @BindView(R.id.tv_delivery_type)
    TextView tv_delivery_type;
    @BindView(R.id.rl_delivery_type)
    RelativeLayout rl_delivery_type;


//    private String tag;//rent,sale
    private String id;
    private int price;
    private int count = 1;
    private int days;
    private int hour;
    private int mxCount = 200;
    private GoodsListBean goodsBean;
    private Date beginDate;
    private Date endDate;
    private int payChannel = 0;//支付通道0为支付宝1为微信
    private int totalMoney;//订单总额单位是分
    private int orderType;//订单类型0为购买1租
    private int deliverytype = 1;//0为快递配送，1为店铺自取
    private Call<SuperBean<String>> getRsaOrderCall;
    private String orderId;
    Call<SuperBean<String>> commitOrderCall;
    Call<SuperBean<String>> commitRentCall;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_commit_order;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            goodsBean = (GoodsListBean) bundle.getSerializable("detail");
//            tag = bundle.getString("type");
        }

        setDeliveryType();
        bt_commit.setOnClickListener(this);
//        ll_begin_time.setOnClickListener(this);
//        ll_end_time.setOnClickListener(this);
        rl_delivery_type.setOnClickListener(this);
        mi_name.setOnClickListener(this);
        mi_phone.setOnClickListener(this);
        mi_addresss.setOnClickListener(this);

        tvDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.valueOf(tvNum.getText().toString());
                if (count == 1) {
                    tvDes.setEnabled(false);
                    return;

                }
                tvIns.setEnabled(true);

                if (count > 1) {

                    if (count == 2) {
                        tvDes.setEnabled(false);
                    }
                    count--;
                    tvNum.setText(count + "");
                    setSalePrice();
                }
            }
        });
        tvIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDes.setEnabled(true);
                count = Integer.valueOf(tvNum.getText().toString());
                if (count < mxCount) {

                    if (count == mxCount - 1) {
                        tvIns.setEnabled(false);
                    }
                    count++;
                    tvNum.setText(count + "");
                    setSalePrice();
                }


            }
        });
    }

    private void setRentPrice() {
//        if (goodsBean.getBillingmode() == 1) {
//            //按照天算
//            if (BaseContext.getInstance().getUserInfo().vipgrade > 0) {
//                price = goodsBean.getVipprice() * days + goodsBean.getDeposit();
//            } else {
//                price = goodsBean.getPrice() * days + goodsBean.getDeposit();
//            }
//        } else {
//            //按照小时算
//            if (BaseContext.getInstance().getUserInfo().vipgrade > 0) {
//                price = goodsBean.getVipprice() * hour + goodsBean.getDeposit();
//            } else {
//                price = goodsBean.getPrice() * hour + goodsBean.getDeposit();
//            }
//        }
        tv_num_price.setText("共计1件商品，合计￥" + price / 100.00);
        tv_price_all.setText("￥" + price / 100.00);
    }

    private void setSalePrice() {
//        price = goodsBean.getPurchase() * count;
        tv_num_price.setText("共计" + count + "件商品，合计￥" + price / 100.00);
        tv_price_all.setText("￥" + price / 100.00);
    }

    @Override
    public void loadData() {
        setValueDefault();
    }

    private void setValueDefault() {
//        ImageLoadedrManager.getInstance().display(this, goodsBean.getGoodsImg(), iv_goods);
//        tv_title.setText(goodsBean.getName());
//        if (tag.equals("rent")) {
//            if (BaseContext.getInstance().getUserInfo().vipgrade > 0) {
//
//                tv_price.setText("￥" + goodsBean.getVipprice() / 100.00);
//            } else {
//                tv_price.setText("￥" + goodsBean.getPrice() / 100.00);
//            }
//        } else {
//            tv_price.setText("￥" + goodsBean.getPurchase() / 100.00);
//        }


    }

    @Override
    public boolean isRegistEventBus() {
        return false;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {

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

    @Override
    protected View isNeedLec() {
        return null;
    }


    private void setAddress(ShoppingAddressListItemBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getName()) && !TextUtils.isEmpty(bean.getPhone()) && !TextUtils.isEmpty(bean.getDetail())) {
            mi_name.getRightText().setText(bean.getName());
            mi_phone.getRightText().setText(bean.getPhone());
            mi_addresss.getRightText().setText(bean.getDetail());
        } else {
            //
//            ll_address.setVisibility(View.GONE);
//            ll_add_addresss.setVisibility(View.VISIBLE);
            UIUtil.showToast("地址获取失败");
        }
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
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("提交订单");
        title_view.setShowDefaultRightValue();
    }

    /**
     * 支付方式
     */
    public void payStyleDialog() {

        View view = View.inflate(CommitOrderActivity.this, R.layout.dialog_pay_type, null);
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
                    payChannel = 0;
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
                    payChannel = 1;
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
                    payChannel = 2;
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
                    } else if (payChannel == 2) {
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

    /**
     * 配送方式
     */
    public void sendDialog() {

        View view = View.inflate(CommitOrderActivity.this, R.layout.dialog_publish_photo, null);
        showdialog(view);

        final TextView photo = view.findViewById(R.id.photo);
        final TextView picture =  view.findViewById(R.id.picture);
        photo.setText("到店自取");
        picture.setText("快递配送");
        photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deliverytype = 1;
                setDeliveryType();
                myDialog.dismiss();

            }
        });
        picture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deliverytype = 0;
                setDeliveryType();
                myDialog.dismiss();
            }
        });

    }

    private void setDeliveryType() {
        if (deliverytype == 0) {
            tv_delivery_type.setText("快递配送");
        } else {
            tv_delivery_type.setText("店铺自取");
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_commit:
                if (checkData())
                    if (!UIUtil.isFastDoubleClick()) {
//                        if (tag.equals("rent")) {
                            commitRentOrder();
//                        } else {
//                            commitOrder();
//                        }
                    }
                break;
            case R.id.rl_delivery_type:
                //
                sendDialog();
                break;
            case R.id.mi_name:
            case R.id.mi_phone:
            case R.id.mi_addresss:
                //地址
                Intent intent1 = new Intent(this, ShoppingAddressActivity.class);
                intent1.putExtra("type", "getAddress");
                startActivityForResult(intent1, ADD_REQUEST_CODE);
                break;

        }
    }



    private boolean checkData() {
        if (TextUtils.isEmpty(mi_addresss.getRightText().getText().toString())) {
            UIUtil.showToast("请选择收货地址");
            return false;
        }
//        if (tag.equals("rent")) {
//            if (hour == 0) {
//                UIUtil.showToast("请选择正确的时间");
//                return false;
//            }
//        }

        return true;
    }

    /**
     * 获取加密订单信息
     */
    private void getRSAOrderInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("userId", BaseContext.getInstance().getUserInfo().userId);
        map.put("payChannel", payChannel + "");
        map.put("totalMoney", price + "");
        map.put("orderType", orderType + "");
        DialogUtils.showDialog(CommitOrderActivity.this, "加载...", false);
        getRsaOrderCall = RestAdapterManager.getApi().getRsaOrderInfo(map);
        getRsaOrderCall.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    LogUtils.e(response.body().getMsg());
                    pay(response.body().getData());
                } else {
                    UIUtil.showToast("支付失败");
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
     * 提交购买订单
     */
    private void commitOrder() {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast(R.string.net_state_error);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("ordername", mi_name.getRightText().getText().toString());
        map.put("orderphone", mi_phone.getRightText().getText().toString());
        map.put("orderaddress", mi_addresss.getRightText().getText().toString());
//        map.put("goodsid", goodsBean.getId() + "");
//        map.put("goodsprice", goodsBean.getPrice() + "");
        map.put("count", tvNum.getText().toString());
        map.put("deliverytype", deliverytype + "");
        map.put("payType", orderType + "");
        map.put("totalmoney", price + "");
        map.put("userid", BaseContext.getInstance().getUserInfo().userId);
        LogUtils.e(JSON.toJSONString(map));
        Call<SuperBean<String>> commitOrderCall;
        DialogUtils.showDialog(CommitOrderActivity.this, "获取订单...", false);
        commitOrderCall = RestAdapterManager.getApi().getCommitOrder(map);
        commitOrderCall.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    LogUtils.e(response.body().getMsg());
                    DialogUtils.closeDialog();
                    orderId = response.body().getData();
                    payStyleDialog();

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

    /**
     * 提交租赁订单
     */
    private void commitRentOrder() {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast(R.string.net_state_error);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("ordername", mi_name.getRightText().getText().toString());
        map.put("orderphone", mi_phone.getRightText().getText().toString());
        map.put("orderaddress", mi_addresss.getRightText().getText().toString());
//        map.put("goodsid", goodsBean.getId() + "");
//        map.put("price", goodsBean.getPrice() + "");
        map.put("count", "1");
        map.put("starttime", UIUtil.getTime(beginDate, "yyyy-MM-dd HH:mm:ss"));
        map.put("endtime", UIUtil.getTime(endDate, "yyyy-MM-dd HH:mm:ss"));
        map.put("deliverytype", deliverytype + "");
        map.put("payType", orderType + "");
        map.put("totalmoney", price + "");
        map.put("userid", BaseContext.getInstance().getUserInfo().userId);
//        map.put("deposit", goodsBean.getDeposit() + "");
        LogUtils.e(JSON.toJSONString(map));
        DialogUtils.showDialog(CommitOrderActivity.this, "获取订单...", false);
        commitRentCall = RestAdapterManager.getApi().getRentOrder(map);
        commitRentCall.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    LogUtils.e(response.body().getMsg());
                    DialogUtils.closeDialog();
                    orderId = response.body().getData();
                    payStyleDialog();

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

    /**
     * 支付
     *
     * @param string
     */
    private void pay(String string) {
        if (payChannel==0){
            JPay.getIntance(this).toPay(JPay.PayMode.ALIPAY, string, new JPay.JPayListener() {
                @Override
                public void onPaySuccess() {
                    DialogUtils.closeDialog();
                    goNext();
                    Toast.makeText(CommitOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPayError(int error_code, String message) {
                    DialogUtils.closeDialog();
                    Toast.makeText(CommitOrderActivity.this, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPayCancel() {
                    DialogUtils.closeDialog();
                    Toast.makeText(CommitOrderActivity.this, "取消了支付", Toast.LENGTH_SHORT).show();
                }
            });
        }else if (payChannel==1){
            JPay.getIntance(this).toPay(JPay.PayMode.WXPAY, string, new JPay.JPayListener() {
                @Override
                public void onPaySuccess() {
                    DialogUtils.closeDialog();
                    goNext();
                    Toast.makeText(CommitOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPayError(int error_code, String message) {
                    DialogUtils.closeDialog();
                    Toast.makeText(CommitOrderActivity.this, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPayCancel() {
                    DialogUtils.closeDialog();
                    Toast.makeText(CommitOrderActivity.this, "取消了支付", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        if (commitRentCall != null) {
            commitRentCall.cancel();
        }
        if (commitOrderCall != null) {
            commitOrderCall.cancel();
        }
        super.onDestroy();
    }
}
