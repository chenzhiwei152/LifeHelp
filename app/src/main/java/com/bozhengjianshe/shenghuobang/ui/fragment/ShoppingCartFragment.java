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
import com.bozhengjianshe.shenghuobang.ui.adapter.ShoppingCardListAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.CardListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
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
 * Created by chen.zhiwei on 2017-12-5.
 */

public class ShoppingCartFragment extends BaseFragment {
    @BindView(R.id.title_view)
    TitleBar title_view;
    private ShoppingCardListAdapter listAdapter;
    @BindView(R.id.sf_listview)
    RecyclerView sf_listview;
    @BindView(R.id.swiperefreshlayout)
    SmartRefreshLayout swiperefreshlayout;
//    @BindView(R.id.rb_check_all)
//    RadioButton rb_check_all;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle();
        sf_listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        sf_listview.setNestedScrollingEnabled(false);
        swiperefreshlayout.setEnableLoadmore(false);
        swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getCardList();
            }
        });

        listAdapter = new ShoppingCardListAdapter(getActivity());
        sf_listview.setAdapter(listAdapter);
//        rb_check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                rb_check_all.setChecked(b);
//            }
//        });
    }

    @Override
    protected void loadData() {
        swiperefreshlayout.autoRefresh();
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

    private void getCardList() {
        Call<SuperBean<List<CardListItemBean>>> getCardList = RestAdapterManager.getApi().getCardList(BaseContext.getInstance().getUserInfo().userId);
        getCardList.enqueue(new JyCallBack<SuperBean<List<CardListItemBean>>>() {
            @Override
            public void onSuccess(Call<SuperBean<List<CardListItemBean>>> call, Response<SuperBean<List<CardListItemBean>>> response) {
                swiperefreshlayout.finishRefresh();
                if (response.body() != null && response.body().getData() != null) {
                    listAdapter.ClearData();
                    listAdapter.addList(response.body().getData());
                }

            }

            @Override
            public void onError(Call<SuperBean<List<CardListItemBean>>> call, Throwable t) {
                swiperefreshlayout.finishRefresh();
                LogUtils.e(t.getMessage());
            }

            @Override
            public void onError(Call<SuperBean<List<CardListItemBean>>> call, Response<SuperBean<List<CardListItemBean>>> response) {
                swiperefreshlayout.finishRefresh();
                try {
                    ErrorMessageUtils.taostErrorMessage(getActivity(), response.errorBody().string());
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
        title_view.setTitle("购物车");
//        title_view.setTitleColor(Color.WHITE);
//        title_view.setLeftImageResource(R.mipmap.ic_title_back);
//        title_view.setLeftClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().finish();
//            }
//        });
        title_view.setImmersive(true);
    }
}
