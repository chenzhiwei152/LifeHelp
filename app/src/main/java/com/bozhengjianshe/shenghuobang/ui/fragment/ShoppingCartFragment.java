package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.ShoppingCardListAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.CardListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
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
    private double mTotalPrice;
    @BindView(R.id.rb_check_all)
    CheckBox rb_check_all;
    @BindView(R.id.tv_calculate)
    TextView tv_calculate;
    private TextView action;

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
        rb_check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                rb_check_all.setChecked(b);
                setIsAllChecked(b);
            }
        });
        listAdapter.setOnDeleteListerner(new CommonOnClickListerner() {
            @Override
            public void myOnClick(Object data) {
                deleteItem((CardListItemBean) data);
            }
        });
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
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (eventBusCenter != null) {
            if (eventBusCenter.getEvenCode() == Constants.UPDA_CARD_GOODS_SELECTED) {
                if (getSelected().size() == listAdapter.getItemCount()) {
                    rb_check_all.setChecked(true);
                } else {
                    rb_check_all.setChecked(false);
                }
            } else if (eventBusCenter.getEvenCode() == Constants.UPDATE_CARD_PRICE) {
                //更新价格
                CalculatePrice();
            }
        }
    }

    /**
     * 获取列表
     */
    private void getCardList() {
        Call<SuperBean<List<CardListItemBean>>> getCardList = RestAdapterManager.getApi().getCardList(BaseContext.getInstance().getUserInfo().id);
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
     * 删除数据
     */
    private void deleteItem(final CardListItemBean bean) {
        Call<SuperBean<String>> deleteCardList = RestAdapterManager.getApi().deleteCardList(bean.getId() + "");
        deleteCardList.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                UIUtil.showToast(response.body().getMsg());
                if (response.body().getCode() == Constants.successCode) {
                    listAdapter.removeItem(bean);
                }
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {

            }
        });
    }

    private void CalculatePrice() {
        if (listAdapter.getList() != null && listAdapter.getList().size() > 0) {
            for (int i = 0; i < listAdapter.getList().size(); i++) {
                if (listAdapter.getList().get(i).isChecked()) {
                    mTotalPrice += listAdapter.getList().get(i).getProductPrice();
                }
            }
        }

    }

    /**
     * 获取选中的bean
     *
     * @return
     */
    List<CardListItemBean> selected = new ArrayList<>();

    private List<CardListItemBean> getSelected() {
        selected.clear();
        if (listAdapter.getList() != null && listAdapter.getList().size() > 0) {
            for (int i = 0; i < listAdapter.getList().size(); i++) {
                if (listAdapter.getList().get(i).isChecked()) {
                    selected.add(listAdapter.getList().get(i));
                }
            }
        }
        return selected;
    }

    /**
     * 选中全选以后的设置
     *
     * @param isAllChecked
     */
    private void setIsAllChecked(boolean isAllChecked) {
        if (listAdapter.getList() != null && listAdapter.getList().size() > 0) {
            for (int i = 0; i < listAdapter.getList().size(); i++) {
                listAdapter.getList().get(i).setChecked(isAllChecked);
            }
            listAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("购物车");
        action = (TextView) title_view.addAction(new TitleBar.TextAction("编辑") {
            @Override
            public void performAction(View view) {
                if (listAdapter != null && listAdapter.isEditMode()) {
                    listAdapter.setEditMode(false);
                    listAdapter.notifyDataSetChanged();
                    action.setText("编辑");
                    tv_calculate.setVisibility(View.VISIBLE);
                } else {
                    if (listAdapter != null) {
                        listAdapter.setEditMode(true);
                        listAdapter.notifyDataSetChanged();
                        action.setText("完成");
                        tv_calculate.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
