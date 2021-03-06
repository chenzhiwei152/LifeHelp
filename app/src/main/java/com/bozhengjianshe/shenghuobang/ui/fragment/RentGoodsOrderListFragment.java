package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.RentOrderListAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperOrderListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 订单
 * Created by chen.zhiwei on 2017-6-21.
 */

public class RentGoodsOrderListFragment extends BaseFragment {
    @BindView(R.id.sf_listview)
    RecyclerView sf_listview;
    @BindView(R.id.swiperefreshlayout)
    SmartRefreshLayout swiperefreshlayout;
    private RentOrderListAdapter rentOrderListAdapter;
    private Call<SuperOrderListBean<List<BuyOrderListItemBean>>> orderListCall;
    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_rent_order_list;
    }

    @Override
    protected void initViewsAndEvents() {
        sf_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        swiperefreshlayout.setEnableLoadmore(false);
        rentOrderListAdapter = new RentOrderListAdapter(getActivity());
        sf_listview.setAdapter(rentOrderListAdapter);
        swiperefreshlayout.setEnableHeaderTranslationContent(false);
        swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                pageNum = 1;
                getOrderList();
            }
        });
//        swiperefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                getOrderList();
//            }
//        });
    }

    @Override
    protected void loadData() {
        getOrderList();
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

    private void getOrderList() {
        RequestBody body= new FormBody.Builder()
                .add("memberid",BaseContext.getInstance().getUserInfo().id)
                .build();
        orderListCall = RestAdapterManager.getApi().getRentOrderList(body);
        orderListCall.enqueue(new JyCallBack<SuperOrderListBean<List<BuyOrderListItemBean>>>() {
            @Override
            public void onSuccess(Call<SuperOrderListBean<List<BuyOrderListItemBean>>> call, Response<SuperOrderListBean<List<BuyOrderListItemBean>>> response) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                    swiperefreshlayout.finishLoadmore();
                }
                if (response != null && response.body() != null&&response.body().getData()!=null) {
                    if (response.body().getData().size() > 0) {
                            rentOrderListAdapter.ClearData();
                        rentOrderListAdapter.addList(response.body().getData());
//                        pageNum++;
                    } else {
//                        if (pageNum == 1) {
////无数据
//                        } else {
//                            UIUtil.showToast("已加载完全部数据");
//                            swiperefreshlayout.setEnableLoadmore(false);
//                        }

                    }
                }
            }

            @Override
            public void onError(Call<SuperOrderListBean<List<BuyOrderListItemBean>>> call, Throwable t) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                    swiperefreshlayout.finishLoadmore();
                }
            }

            @Override
            public void onError(Call<SuperOrderListBean<List<BuyOrderListItemBean>>> call, Response<SuperOrderListBean<List<BuyOrderListItemBean>>> response) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                    swiperefreshlayout.finishLoadmore();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (orderListCall != null) {
            orderListCall.cancel();
        }
    }
}
