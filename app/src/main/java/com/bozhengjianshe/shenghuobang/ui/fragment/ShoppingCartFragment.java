package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.activity.CommitOrderActivity;
import com.bozhengjianshe.shenghuobang.ui.adapter.ShoppingCardListAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperShoppingCardsBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;
import com.bozhengjianshe.shenghuobang.ui.utils.ShoppingCardsUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.bozhengjianshe.shenghuobang.base.Constants.REDUCE_NUMBER_CARD;

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
//    private double mTotalPrice;
    private double price;
    private double yunfei;
    @BindView(R.id.rb_check_all)
    CheckBox rb_check_all;
    @BindView(R.id.tv_calculate)
    TextView tv_calculate;
    @BindView(R.id.tv_price)
    TextView tv_price;
    private TextView action;
    private Call<SuperShoppingCardsBean<List<GoodsListBean>>> goodsListCall;

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
                getList();
            }
        });

        listAdapter = new ShoppingCardListAdapter(getActivity());
        sf_listview.setAdapter(listAdapter);
//        rb_check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
////                rb_check_all.setChecked(b);
//
//            }
//        });
        rb_check_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIsAllChecked(rb_check_all.isChecked());
            }
        });
        listAdapter.setOnDeleteListerner(new CommonOnClickListerner() {
            @Override
            public void myOnClick(Object data) {
                deleteItem((GoodsListBean) data);
            }
        });
        tv_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//结算
                if (getSelectedGoods().size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("detail", (Serializable) getSelectedGoods());
                    Intent intent = new Intent(getActivity(), CommitOrderActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
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
                setSalePrice();
            } else if (eventBusCenter.getEvenCode() == Constants.ADD_NUMBER_CARD) {
                //更新
                ShoppingCardsUtils.updateItem(getActivity(),(String) eventBusCenter.getData(), "add");
                setSalePrice();
            } else if (eventBusCenter.getEvenCode() == REDUCE_NUMBER_CARD) {
                ShoppingCardsUtils.updateItem(getActivity(),(String) eventBusCenter.getData(), "reduce");
                setSalePrice();
            } else if (eventBusCenter.getEvenCode() == Constants.ADD_TO_CARD) {
                getList();
            }
        }
    }


    private void getList() {
        if (BaseContext.getInstance().getUserInfo() == null) {
            swiperefreshlayout.finishRefresh();
            return;
        }
        RequestBody body = new FormBody.Builder().add("memberid", BaseContext.getInstance().getUserInfo().id).build();
        goodsListCall = RestAdapterManager.getApi().getShoppingCardList(body);
        goodsListCall.enqueue(new JyCallBack<SuperShoppingCardsBean<List<GoodsListBean>>>() {
            @Override
            public void onSuccess(Call<SuperShoppingCardsBean<List<GoodsListBean>>> call, Response<SuperShoppingCardsBean<List<GoodsListBean>>> response) {
                swiperefreshlayout.finishRefresh();
                if (response != null && response.body() != null && response.body().state == Constants.successCode) {
                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                        listAdapter.ClearData();
                        listAdapter.addList(response.body().getData());
                    }

                } else {
                    try {
                        UIUtil.showToast(response.body().message);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(Call<SuperShoppingCardsBean<List<GoodsListBean>>> call, Throwable t) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                }

            }

            @Override
            public void onError(Call<SuperShoppingCardsBean<List<GoodsListBean>>> call, Response<SuperShoppingCardsBean<List<GoodsListBean>>> response) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                }
            }
        });
    }

    /**
     * 删除购物车
     */
    private void deleteItem(final GoodsListBean bean) {
        Call<SuperShoppingCardsBean<List<GoodsListBean>>> deleteCall;
        RequestBody body = new FormBody.Builder().add("memberid", BaseContext.getInstance().getUserInfo().id).add("comids", "" + bean.getId()).build();
        deleteCall = RestAdapterManager.getApi().deleteCardList(body);
        deleteCall.enqueue(new JyCallBack<SuperShoppingCardsBean<List<GoodsListBean>>>() {
            @Override
            public void onSuccess(Call<SuperShoppingCardsBean<List<GoodsListBean>>> call, Response<SuperShoppingCardsBean<List<GoodsListBean>>> response) {
                swiperefreshlayout.finishRefresh();
                getList();
//                if (response != null && response.body() != null && response.body().state == Constants.successCode) {
//                    if (response.body().getData() != null && response.body().getData().size() > 0) {
//                        listAdapter.ClearData();
//                        listAdapter.addList(response.body().getData());
//                    }
//
//                } else {
//                    try {
//                        UIUtil.showToast(response.body().message);
//                    } catch (Exception e) {
//                    }
//                }
            }

            @Override
            public void onError(Call<SuperShoppingCardsBean<List<GoodsListBean>>> call, Throwable t) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                }

            }

            @Override
            public void onError(Call<SuperShoppingCardsBean<List<GoodsListBean>>> call, Response<SuperShoppingCardsBean<List<GoodsListBean>>> response) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                }
            }
        });

    }


    private void setSalePrice() {
        price=0;
        yunfei=0;
        List<GoodsListBean> goodsBean=getSelected();
        if (isContailsService(goodsBean)) {
            //有服务类
            for (int i = 0; i < goodsBean.size(); i++) {
                if (goodsBean.get(i).getLb() == 2) {
                    //1   服务  2商品
                    price += (goodsBean.get(i).getProfit() + goodsBean.get(i).getCost()) * goodsBean.get(i).getNum();
                    if ((goodsBean.get(i).getSfkxd() == 2)) {
                        price += goodsBean.get(i).getFreight()* goodsBean.get(i).getNum();//运费
                        yunfei+= goodsBean.get(i).getFreight()* goodsBean.get(i).getNum();
                    }
                } else {
                    price += goodsBean.get(i).getFee();
                }
            }
        } else {
            for (int i = 0; i < goodsBean.size(); i++) {
                if (goodsBean.get(i).getLb() == 2) {
                    //1   服务  2商品
                    price += (goodsBean.get(i).getProfit() + goodsBean.get(i).getCost()) * goodsBean.get(i).getNum();
                    price += (goodsBean.get(i).getFreight())* goodsBean.get(i).getNum();//运费
                    yunfei+= goodsBean.get(i).getFreight()* goodsBean.get(i).getNum();
                } else {
                    price += goodsBean.get(i).getFee();
                }
            }
        }
        tv_price.setText(getResources().getString(R.string.money) + String.format("%.2f", price) + "(含运费"+getResources().getString(R.string.money)+yunfei+")");
    }
    private boolean isContailsService(List<GoodsListBean> beans) {
        for (int i = 0; i < beans.size(); i++) {
            if (beans.get(i).getLb() == 1) {
                return true;
            }
        }
        return false;
    }
    /**
     * 获取选中的bean
     *
     * @return
     */
    List<GoodsListBean> selected = new ArrayList<>();

    private List<GoodsListBean> getSelected() {
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

    private List<GoodsListBean> getSelectedGoods() {
        List<GoodsListBean> selected = new ArrayList<>();
        if (listAdapter.getList() != null && listAdapter.getList().size() > 0) {
            for (int i = 0; i < listAdapter.getList().size(); i++) {
                if (listAdapter.getList().get(i).isChecked()) {
                    selected.add(listAdapter.getList().get(i));
                }
            }
        }
        return selected;
    }
}
