package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.MerchantOrderItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.OrderDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperOrderBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperOrderListBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 商户订单
 * Created by chen.zhiwei on 2017-12-19.
 */

public class MerchantOrderListFragment extends BaseFragment {
    protected boolean isViewInitiated; //UI初始化完成
    protected boolean isVisibleToUser; //Fragment是当前窗体显示
    protected boolean isDataInitiated; //是否获取了接口数据
    @BindView(R.id.rvOrderList)
    RecyclerView rvOrderList;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smart_refresh;
    public static final String bundleName_type = "type";
    private int type = 0;
    private MerchantOrderItemAdapter orderItemAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    // 移除所有的msg.what为0等消息，保证只有一个循环消息队列再跑
                    handler.removeMessages(0);
                    // app的功能逻辑处理
                    prepareFetchData();
                    // 再次发出msg，循环更新
                    handler.sendEmptyMessageDelayed(0, 30000);
                    break;

                case 1:
                    // 直接移除，定时器停止
                    handler.removeMessages(0);
                    break;

                default:
                    break;
            }
        }


    };

    /**
     * 创建fragment
     *
     * @param type
     * @return
     */
    public static MerchantOrderListFragment createInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(bundleName_type, type);
        MerchantOrderListFragment fragment = new MerchantOrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_merchant_order;
    }

    @Override
    public void initViewsAndEvents() {
        smart_refresh.setEnableLoadmore(false);
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getList();
            }
        });
        orderItemAdapter = new MerchantOrderItemAdapter(getContext());
        rvOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOrderList.setAdapter(orderItemAdapter);
        orderItemAdapter.setOnClickListerner(new CommonOnClickListerner() {
            @Override
            public void myOnClick(Object data) {
                if (data != null) {
                    updateOrder(((BuyOrderListItemBean) data).getId());
                }
            }
        });
        LogUtils.e(BaseContext.getInstance().getUserInfo().id);
    }

    @Override
    public void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(bundleName_type);
        }
        isViewInitiated = true;
//        prepareFetchData();
    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    @Override
    public boolean isRegistEventBus() {
        return false;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {

    }

    private void getList() {
        String state = Constants.STATE_TWO + "";
        switch (type) {
            case 0:
                state = Constants.STATE_TWO + "";
                break;
            case 1:
                state = Constants.STATE_THREE + "," + Constants.STATE_FOUR;
                break;
            case 2:
                state = Constants.STATE_FIVE + "";
                break;
        }
        LogUtils.e("Gets","ceshi------"+state);
        RequestBody body = new FormBody.Builder().add("state", state + "").add("memberid", BaseContext.getInstance().getUserInfo().id).build();
        Call<SuperOrderListBean<List<BuyOrderListItemBean>>> getOrderList = RestAdapterManager.getApi().getOrderList(body);
        getOrderList.enqueue(new JyCallBack<SuperOrderListBean<List<BuyOrderListItemBean>>>() {
            @Override
            public void onSuccess(Call<SuperOrderListBean<List<BuyOrderListItemBean>>> call, Response<SuperOrderListBean<List<BuyOrderListItemBean>>> response) {
                smart_refresh.finishRefresh();
                UIUtil.showToast(response.body().message);
                orderItemAdapter.ClearData();
                if (response.body().getCode() == Constants.successCode) {
                    orderItemAdapter.addList(response.body().getData());
                }
            }

            @Override
            public void onError(Call<SuperOrderListBean<List<BuyOrderListItemBean>>> call, Throwable t) {
                smart_refresh.finishRefresh();
            }

            @Override
            public void onError(Call<SuperOrderListBean<List<BuyOrderListItemBean>>> call, Response<SuperOrderListBean<List<BuyOrderListItemBean>>> response) {
                smart_refresh.finishRefresh();
            }
        });
    }

    /**
     * 处理接单
     */
    private void updateOrder(String orderId) {
        String state = Constants.STATE_FOUR + "";
        switch (type) {
            case 0:
                state = Constants.STATE_FOUR + "";
                break;
            case 1:
                state = Constants.STATE_FIVE + "";
                break;

        }
        DialogUtils.showDialog(getActivity(), "", false);
        RequestBody body = new FormBody.Builder()
                .add("memberid", BaseContext.getInstance().getUserInfo().id)
                .add("orderid", orderId)
                .add("state", state)
                .build();
        Call<SuperOrderBean<OrderDetailBean>> quitOrder = RestAdapterManager.getApi().quitOrder(body);
        quitOrder.enqueue(new JyCallBack<SuperOrderBean<OrderDetailBean>>() {
            @Override
            public void onSuccess(Call<SuperOrderBean<OrderDetailBean>> call, Response<SuperOrderBean<OrderDetailBean>> response) {
                DialogUtils.closeDialog();
                UIUtil.showToast(response.body().getMsg());
                EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.UPDATE_ORDER_SUCCESS));
                getList();
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        handler.sendEmptyMessage(0);
    }

    public boolean prepareFetchData() {

        if (isVisibleToUser && isViewInitiated) {
            getList();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessage(0);
    }
}
