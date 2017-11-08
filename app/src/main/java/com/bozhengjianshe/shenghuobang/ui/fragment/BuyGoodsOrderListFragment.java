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
import com.bozhengjianshe.shenghuobang.ui.adapter.BuyOrderListAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 买卖订单
 * Created by chen.zhiwei on 2017-6-21.
 */

public class BuyGoodsOrderListFragment extends BaseFragment {
    @BindView(R.id.sf_listview)
    RecyclerView sf_listview;
    @BindView(R.id.swiperefreshlayout)
    SmartRefreshLayout swiperefreshlayout;
    private BuyOrderListAdapter buyOrderListAdapter;
    private Call<SuperBean<BuyOrderListItemBean>> orderListCall;
    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_buy_order_list;
    }

    @Override
    protected void initViewsAndEvents() {
        sf_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        sf_listview.setNestedScrollingEnabled(false);
        swiperefreshlayout.setEnableHeaderTranslationContent(false);
        swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                getOrderList();
            }
        });
        swiperefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getOrderList();
            }
        });
        buyOrderListAdapter = new BuyOrderListAdapter(getActivity());
        sf_listview.setAdapter(buyOrderListAdapter);
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
        Map<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("pageSize", pageSize + "");
        map.put("userid", BaseContext.getInstance().getUserInfo().userId);
        orderListCall = RestAdapterManager.getApi().getBuyOrderList(map);
        orderListCall.enqueue(new JyCallBack<SuperBean<BuyOrderListItemBean>>() {
            @Override
            public void onSuccess(Call<SuperBean<BuyOrderListItemBean>> call, Response<SuperBean<BuyOrderListItemBean>> response) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                    swiperefreshlayout.finishLoadmore();
                }
                if (response != null && response.body() != null) {
                    if (response.body().getData().getData().size() > 0) {
                        if (pageNum == 1) {
                            buyOrderListAdapter.ClearData();
                        }
                        buyOrderListAdapter.addList(response.body().getData().getData());
                        pageNum++;
                    } else {
                        if (pageNum == 1) {
//无数据
                        } else {
                            UIUtil.showToast("已加载完全部数据");
                            swiperefreshlayout.setEnableLoadmore(false);
                        }

                    }
                }
            }

            @Override
            public void onError(Call<SuperBean<BuyOrderListItemBean>> call, Throwable t) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                    swiperefreshlayout.finishLoadmore();
                }
            }

            @Override
            public void onError(Call<SuperBean<BuyOrderListItemBean>> call, Response<SuperBean<BuyOrderListItemBean>> response) {
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
