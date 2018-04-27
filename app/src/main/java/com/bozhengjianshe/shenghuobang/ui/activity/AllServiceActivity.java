package com.bozhengjianshe.shenghuobang.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.AllServiceTypeListAdapter;
import com.bozhengjianshe.shenghuobang.ui.adapter.MainListItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceTypeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperGoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.view.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-12-11.
 */

public class AllServiceActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.rc_type_list)
    RecyclerView rc_type_list;
    @BindView(R.id.sf_content_listview)
    RecyclerView sf_content_listview;
    @BindView(R.id.swiperefreshlayout)
    SmartRefreshLayout swiperefreshlayout;
    AllServiceTypeListAdapter typeListAdapter;
    MainListItemAdapter contentListAdapter;
    private int pageNumber = 1;
    private int pageSize = 10;
    private String classify;
    private String keyWord;
    private Call<SuperBean<List<AllServiceTypeBean>>> getAllServiceTypeList;
    private Call<SuperGoodsListBean<List<GoodsListBean>>> goodsListCall;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_all_service;
    }

    @Override
    public void initViewsAndEvents() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classify = bundle.getString(Constants.homeTypeTag);
        }
        initTitle();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rc_type_list.setLayoutManager(layoutManager);
        sf_content_listview.setLayoutManager(new GridLayoutManager(this, 2));
        sf_content_listview.setNestedScrollingEnabled(false);

        typeListAdapter = new AllServiceTypeListAdapter(this);
        contentListAdapter = new MainListItemAdapter(this);

        rc_type_list.setAdapter(typeListAdapter);
        sf_content_listview.setAdapter(contentListAdapter);
        swiperefreshlayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getContentList();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNumber = 1;
                getContentList();
            }
        });
        typeListAdapter.setOnClickListerner(new CommonOnClickListerner() {
            @Override
            public void myOnClick(Object data) {
                classify = ((AllServiceTypeBean) data).getId() + "";
                pageNumber = 1;
                getContentList();
            }
        });
    }

    @Override
    public void loadData() {
        getTyleList();
        getContentList();
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

    private void getTyleList() {
        getAllServiceTypeList = RestAdapterManager.getApi().getAllServiceTypeList("0", "2");
        getAllServiceTypeList.enqueue(new JyCallBack<SuperBean<List<AllServiceTypeBean>>>() {
            @Override
            public void onSuccess(Call<SuperBean<List<AllServiceTypeBean>>> call, Response<SuperBean<List<AllServiceTypeBean>>> response) {
                if (response != null && response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                    typeListAdapter.ClearData();
                    typeListAdapter.addList(response.body().getData());
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if ((response.body().getData().get(i).getId() + "").equals(classify)) {
                            typeListAdapter.setSelectedPosition(i);
                            rc_type_list.scrollToPosition(i);
                            break;
                        }
                    }
                }

            }

            @Override
            public void onError(Call<SuperBean<List<AllServiceTypeBean>>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperBean<List<AllServiceTypeBean>>> call, Response<SuperBean<List<AllServiceTypeBean>>> response) {

            }
        });
    }

    private void getContentList() {

        Map<String, String> map = new HashMap<>();
        map.put("lb", "1");//1 查询服务类商品 为 2 查询建材类商品 不传值则全部查询
        map.put("yjfl", classify);//根据一级分类的id获取其目录下产品 不传值则全部查询
        goodsListCall = RestAdapterManager.getApi().getGoodsList(map);
        goodsListCall.enqueue(new JyCallBack<SuperGoodsListBean<List<GoodsListBean>>>() {
            @Override
            public void onSuccess(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Response<SuperGoodsListBean<List<GoodsListBean>>> response) {
                swiperefreshlayout.finishLoadmore();
                swiperefreshlayout.finishRefresh();
                if (pageNumber == 1) {
                    contentListAdapter.ClearData();
                }
                if (response != null && response.body()!=null&&response.body().getData() != null && response.body().getData().size() > 0) {
                    contentListAdapter.addList(response.body().getData());
                    pageNumber++;
                    swiperefreshlayout.setEnableLoadmore(true);
                } else {
                    swiperefreshlayout.setEnableLoadmore(false);
                }
            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Throwable t) {
                swiperefreshlayout.finishLoadmore();
                swiperefreshlayout.finishRefresh();
            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Response<SuperGoodsListBean<List<GoodsListBean>>> response) {
                swiperefreshlayout.finishLoadmore();
                swiperefreshlayout.finishRefresh();
                try {
                    ErrorMessageUtils.taostErrorMessage(AllServiceActivity.this, response.errorBody().string());
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
        title_view.setTitle("分类详情");
        title_view.setShowDefaultRightValue();
    }

    @Override
    protected void onDestroy() {
        if (getAllServiceTypeList != null) {
            getAllServiceTypeList.cancel();
        }
        if (goodsListCall != null) {
            goodsListCall.cancel();
        }
        super.onDestroy();

    }
}
