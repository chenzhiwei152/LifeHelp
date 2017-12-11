package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.CollectionItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.CollectionItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;
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
 * 收藏
 * Created by chen.zhiwei on 2017-12-11.
 */

public class CollectionActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.swiperefreshlayout)
    SmartRefreshLayout swiperefreshlayout;
    @BindView(R.id.title_view)
    TitleBar title_view;
    CollectionItemAdapter contentListAdapter;
    private TextView action;
    private CollectionItemBean bean;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        swiperefreshlayout.setEnableLoadmore(false);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        contentListAdapter = new CollectionItemAdapter(this);
        swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getColloction();
            }
        });
        rv_list.setAdapter(contentListAdapter);

        contentListAdapter.setOnClickListerner(new CommonOnClickListerner() {
            @Override
            public void myOnClick(Object data) {
                bean = (CollectionItemBean) data;
                if (contentListAdapter.isEditMode()) {
                    DialogUtils.showOrderCancelMsg(CollectionActivity.this, "确认要删除该条目吗？", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (view.getTag().equals("确定")) {
                                deleteColloction(bean);
                            }
                        }
                    });
                } else {
                    Intent intent = new Intent(CollectionActivity.this, GoodsDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", bean.getId() + "");
                    bundle.putString("type", bean.getProductType() + "");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void loadData() {
        swiperefreshlayout.autoRefresh();
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
     * 获取收藏
     */
    private void getColloction() {
        Call<SuperBean<List<CollectionItemBean>>> getCollection = RestAdapterManager.getApi().getCollection(BaseContext.getInstance().getUserInfo().userId);
        getCollection.enqueue(new JyCallBack<SuperBean<List<CollectionItemBean>>>() {
            @Override
            public void onSuccess(Call<SuperBean<List<CollectionItemBean>>> call, Response<SuperBean<List<CollectionItemBean>>> response) {
                swiperefreshlayout.finishRefresh();
                if (response != null && response.body() != null && response.body().getData() != null) {
                    contentListAdapter.ClearData();
                    contentListAdapter.addList(response.body().getData());
                }

            }

            @Override
            public void onError(Call<SuperBean<List<CollectionItemBean>>> call, Throwable t) {
                swiperefreshlayout.finishRefresh();
            }

            @Override
            public void onError(Call<SuperBean<List<CollectionItemBean>>> call, Response<SuperBean<List<CollectionItemBean>>> response) {
                swiperefreshlayout.finishRefresh();
                try {
                    ErrorMessageUtils.taostErrorMessage(CollectionActivity.this, response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 删除收藏
     */
    private void deleteColloction(final CollectionItemBean bean) {
        Call<SuperBean<String>> deleteCollection = RestAdapterManager.getApi().deleteCollection(bean.getId() + "");
        deleteCollection.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                UIUtil.showToast(response.body().getMsg());
                contentListAdapter.remove(bean);
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                try {
                    ErrorMessageUtils.taostErrorMessage(CollectionActivity.this, response.errorBody().string());
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
        title_view.setTitle("我的收藏");
        title_view.setShowDefaultRightValue();
        action = (TextView) title_view.addAction(new TitleBar.TextAction("管理") {
            @Override
            public void performAction(View view) {
                if (contentListAdapter != null && contentListAdapter.isEditMode()) {
                    contentListAdapter.setEditMode(false);
                    action.setText("管理");
                } else {
                    if (contentListAdapter != null) {
                        contentListAdapter.setEditMode(true);
                        action.setText("完成");
                    }
                }
            }
        });
    }
}
