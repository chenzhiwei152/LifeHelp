package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bozhengjianshe.shenghuobang.ui.adapter.CommitOrderItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.CommitOrderResultBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.ShoppingAddressListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.utils.ShoppingCardsUtils;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.MyDialog;
import com.bozhengjianshe.shenghuobang.view.TitleBar;
import com.jpay.JPay;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.RequestBody;
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
    ImageView tvDes;
    @BindView(R.id.btn_add_goods_num)
    ImageView tvIns;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.mi_name)
    TextView mi_name;
    @BindView(R.id.mi_phone)
    TextView mi_phone;
    @BindView(R.id.mi_addresss)
    TextView mi_addresss;

    @BindView(R.id.bt_commit)
    Button bt_commit;
    @BindView(R.id.rl_number)
    RelativeLayout rl_number;
    @BindView(R.id.tv_delivery_type)
    TextView tv_delivery_type;
    @BindView(R.id.rl_delivery_type)
    RelativeLayout rl_delivery_type;
    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.address)
    CardView address;
    @BindView(R.id.tv_all_price)
    TextView tv_all_price;

    private CommitOrderItemAdapter commitOrderItemAdapter;

    private String ids;

    private double price;
    private int count = 1;
    private int mxCount = 200;
    private List<GoodsListBean> goodsBean;
    private int payChannel = 1;//支付通道1为支付宝2为微信
    private int totalMoney;//订单总额单位是分
    private int orderType;//订单类型0为购买1租
    private int deliverytype = 1;//0为快递配送，1为店铺自取
    private Call<SuperBean<String>> getRsaOrderCall;
    private String orderId;
    Call<CommitOrderResultBean> commitRentCall;
    ShoppingAddressListItemBean bean;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_commit_order;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            goodsBean = (List<GoodsListBean>) bundle.getSerializable("detail");
        }

        commitOrderItemAdapter = new CommitOrderItemAdapter(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(commitOrderItemAdapter);
        rvList.setNestedScrollingEnabled(false);
        commitOrderItemAdapter.addList(goodsBean);

        bt_commit.setOnClickListener(this);
        address.setOnClickListener(this);

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


    private void setSalePrice() {
        ids = "";
        for (int i = 0; i < goodsBean.size(); i++) {
            ids += goodsBean.get(i).getId();
            ids += ",";
            if (goodsBean.get(i).getLb() == 2) {
                //1   服务  2商品
                price += (goodsBean.get(i).getProfit() + goodsBean.get(i).getCost())*goodsBean.get(i).getNum();
                price+=goodsBean.get(i).getFreight();//运费
            } else {
                price += goodsBean.get(i).getFee();
            }

        }
        tv_all_price.setText(getResources().getString(R.string.money) + String.format("%.2f", price)+"");
    }

    @Override
    public void loadData() {
        setSalePrice();
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
                bean = (ShoppingAddressListItemBean) data.getExtras().getSerializable("addressBean");
                setAddress(bean);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected View isNeedLec() {
        return null;
    }


    private void setAddress(ShoppingAddressListItemBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getLxr()) && !TextUtils.isEmpty(bean.getLxdh()) && !TextUtils.isEmpty(bean.getLxxq())) {
            mi_name.setText("收货人：" + bean.getLxr());
            mi_phone.setText(bean.getLxdh());
            mi_addresss.setText("收货地址：" + bean.getLxdz() + bean.getLxxq());
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
                        goNext();
                    } else {
                        commitRentOrder((payChannel) + "");
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
        final TextView picture = view.findViewById(R.id.picture);
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

                        payStyleDialog();
                    }
                break;
            case R.id.rl_delivery_type:
                //
                sendDialog();
                break;
            case R.id.address:
                //地址
                Intent intent1 = new Intent(this, ShoppingAddressActivity.class);
                intent1.putExtra("type", "getAddress");
                startActivityForResult(intent1, ADD_REQUEST_CODE);
                break;

        }
    }


    private boolean checkData() {
        if (bean == null) {
            UIUtil.showToast("请选择收货地址");
            return false;
        }

        return true;
    }



    /**
     * 提交订单
     */
    private void commitRentOrder(final String type) {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast(R.string.net_state_error);
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("addressid", bean.getId() + "")
                .add("detail", JSON.toJSONString(ShoppingCardsUtils.changeCommitBean(goodsBean)))
                .add("mark", type)
                .add("memberid", BaseContext.getInstance().getUserInfo().id)
                .build();
        LogUtils.e(JSON.toJSONString(formBody));
        DialogUtils.showDialog(CommitOrderActivity.this, "获取订单...", false);
        commitRentCall = RestAdapterManager.getApi().getRentOrder(formBody);
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
        if (payChannel == 1) {
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
        } else if (payChannel == 2) {
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
        super.onDestroy();
    }
}
