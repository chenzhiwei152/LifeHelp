package com.bozhengjianshe.shenghuobang.ui.activity;

import android.os.Bundle;
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
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceContentBean;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceTypeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
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

public class AllBuildingActivity extends BaseActivity {
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


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_all_building;
    }

    @Override
    public void initViewsAndEvents() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classify = bundle.getString(Constants.homeTypeTag);
        }
        initTitle();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rc_type_list.setLayoutManager(layoutManager);
        sf_content_listview.setLayoutManager(new LinearLayoutManager(this));
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
        Call<SuperBean<List<AllServiceTypeBean>>> getAllServiceTypeList = RestAdapterManager.getApi().getAllServiceTypeList("0", "2");
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
        map.put("pageSize", pageSize + "");
        map.put("pageNum", pageNumber + "");
        map.put("keyWord", keyWord);
        map.put("classify", classify);
        map.put("type", Constants.typeService);
        Call<SuperBean<AllServiceContentBean>> getAllServiceContentList = RestAdapterManager.getApi().getAllServiceContentList(map);
        getAllServiceContentList.enqueue(new JyCallBack<SuperBean<AllServiceContentBean>>() {
            @Override
            public void onSuccess(Call<SuperBean<AllServiceContentBean>> call, Response<SuperBean<AllServiceContentBean>> response) {
                swiperefreshlayout.finishLoadmore();
                swiperefreshlayout.finishRefresh();
                if (pageNumber == 1) {
                    contentListAdapter.ClearData();
                }
                if (response != null && response.body() != null && response.body().getData().getData().size() > 0) {
                    contentListAdapter.addList(response.body().getData().getData());
                    pageNumber++;
                    swiperefreshlayout.setEnableLoadmore(true);
                } else {
                    swiperefreshlayout.setEnableLoadmore(false);
                }

            }

            @Override
            public void onError(Call<SuperBean<AllServiceContentBean>> call, Throwable t) {
                swiperefreshlayout.finishLoadmore();
                swiperefreshlayout.finishRefresh();
            }

            @Override
            public void onError(Call<SuperBean<AllServiceContentBean>> call, Response<SuperBean<AllServiceContentBean>> response) {
                swiperefreshlayout.finishLoadmore();
                swiperefreshlayout.finishRefresh();
                try {
                    ErrorMessageUtils.taostErrorMessage(AllBuildingActivity.this, response.errorBody().string());
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
        title_view.setLeftImageResource(R.mipmap.ic_title_back);
        title_view.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_view.setImmersive(true);
    }
}