package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.bean.ErrorBean;
import com.bozhengjianshe.shenghuobang.ui.adapter.ShoppingAddressListAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.ShoppingAddressListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.ShoppingAddressItemOnClickListerner;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-22.
 */

public class ShoppingAddressActivity extends BaseActivity {
    @BindView(R.id.sf_listview)
    RecyclerView sf_listview;
    @BindView(R.id.swiperefreshlayout)
    SmartRefreshLayout swiperefreshlayout;
    @BindView(R.id.title_view)
    TitleBar title_view;
    private ShoppingAddressListAdapter shoppingAddressListAdapter;
    private ImageView mCollectView;
    private Call<SuperBean<List<ShoppingAddressListItemBean>>> call;

    private Call<ErrorBean> deleteCall;
    private String type;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_shopping_address_list;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        try {
            if (getIntent().getExtras() != null) {
                type = getIntent().getExtras().getString("type", "");
            }
        } catch (Exception e) {

        }

        sf_listview.setLayoutManager(new LinearLayoutManager(this));


        shoppingAddressListAdapter = new ShoppingAddressListAdapter(this);
        shoppingAddressListAdapter.setOnClickListerner(new ShoppingAddressItemOnClickListerner() {
            @Override
            public void onClick(ShoppingAddressListItemBean bean) {
                //编辑
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("addressBean", bean);
                intent.putExtras(bundle);
                setResult(Constants.ADD_REQUEST_CODE, intent);
                finish();
            }

            @Override
            public void onDeleteListerner(final ShoppingAddressListItemBean bean) {
                //删除
                DialogUtils.showOrderCancelMsg(ShoppingAddressActivity.this, "确定要删除吗？", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bean != null && !TextUtils.isEmpty(bean.getId() + "")) {
                            deleteAddress(bean.getId() + "");
                        }
                    }

//            @Override
//            public void callBack() {//退出登录
//
//            }
                });
            }
        });

        sf_listview.setAdapter(shoppingAddressListAdapter);
        shoppingAddressListAdapter.setType(type);
        swiperefreshlayout.setEnableHeaderTranslationContent(false);
        swiperefreshlayout.setEnableLoadmore(false);
        swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadAddressData();
            }
        });
    }

    @Override
    public void loadData() {
        loadAddressData();
    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (eventBusCenter != null) {
            if (eventBusCenter.getEvenCode() == Constants.AddressUpdateSuccess) {
                loadAddressData();
            }
        }

    }

    @Override
    protected View isNeedLec() {
        return null;
    }


    /**
     * 获取数据
     */
    private void loadAddressData() {
        DialogUtils.showDialog(ShoppingAddressActivity.this, "加载中", false);
        call = RestAdapterManager.getApi().getAddressList(BaseContext.getInstance().getUserInfo().userId);
        call.enqueue(new JyCallBack<SuperBean<List<ShoppingAddressListItemBean>>>() {
            @Override
            public void onSuccess(Call<SuperBean<List<ShoppingAddressListItemBean>>> call, Response<SuperBean<List<ShoppingAddressListItemBean>>> response) {
                DialogUtils.closeDialog();
                swiperefreshlayout.finishRefresh();
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    shoppingAddressListAdapter.ClearData();
                    shoppingAddressListAdapter.addList(response.body().getData());
                    if (response.body().getData().size() > 0) {
                        mCollectView.setVisibility(View.GONE);
                    } else {
                        mCollectView.setVisibility(View.VISIBLE);
                    }
                } else {
                    try {
                        UIUtil.showToast(response.body().getMsg());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(Call<SuperBean<List<ShoppingAddressListItemBean>>> call, Throwable t) {
                DialogUtils.closeDialog();
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                }
            }

            @Override
            public void onError(Call<SuperBean<List<ShoppingAddressListItemBean>>> call, Response<SuperBean<List<ShoppingAddressListItemBean>>> response) {
                DialogUtils.closeDialog();
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                }
                try {
                    ErrorMessageUtils.taostErrorMessage(ShoppingAddressActivity.this, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteAddress(String id) {
        deleteCall = RestAdapterManager.getApi().deleteAddress(id);
        deleteCall.enqueue(new JyCallBack<ErrorBean>() {
            @Override
            public void onSuccess(Call<ErrorBean> call, Response<ErrorBean> response) {

                if (response != null && response.body() != null) {
                    UIUtil.showToast(response.body().msg);
                    if (response.body().code == 1000) {
                        loadAddressData();
                    }
                }
            }

            @Override
            public void onError(Call<ErrorBean> call, Throwable t) {

            }

            @Override
            public void onError(Call<ErrorBean> call, Response<ErrorBean> response) {
                try {
                    ErrorMessageUtils.taostErrorMessage(ShoppingAddressActivity.this, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("收货地址");
        title_view.setTitleColor(Color.WHITE);
        title_view.setLeftImageResource(R.mipmap.ic_title_back);
        title_view.setLeftText("返回");
        title_view.setLeftTextColor(Color.WHITE);
        title_view.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_view.setBackgroundColor(getResources().getColor(R.color.color_ff6900));
        mCollectView = (ImageView) title_view.addAction(new TitleBar.ImageAction(R.mipmap.ic_add_new) {
            @Override
            public void performAction(View view) {
//                UIUtil.showToast("点击了新增");
                Intent intent = new Intent(ShoppingAddressActivity.this, ShoppingAddressEditActivity.class);
                startActivity(intent);
            }
        });


        title_view.setImmersive(true);


    }


    @Override
    protected void onDestroy() {
        if (call != null) {
            call.cancel();
        }
        if (deleteCall != null) {
            deleteCall.cancel();
        }
        super.onDestroy();
    }
}
