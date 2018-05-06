package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.GoodsDetailCommentsItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsCommentsItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperGoodsListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-12-5.
 */

public class GoodsDetailRightFragment extends BaseFragment {
    Call<SuperGoodsListBean<List<GoodsCommentsItemBean>>> getComments;
    @BindView(R.id.rvList)
    RecyclerView rvList;
    private GoodsDetailCommentsItemAdapter itemAdapter;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_goodsdetail_right;
    }

    @Override
    protected void initViewsAndEvents() {
        itemAdapter = new GoodsDetailCommentsItemAdapter(getContext());
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(itemAdapter);
        rvList.setNestedScrollingEnabled(false);

    }

    @Override
    protected void loadData() {
        getData();
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

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("commondityid", "10");
        getComments = RestAdapterManager.getApi().getComments(map);
        getComments.enqueue(new JyCallBack<SuperGoodsListBean<List<GoodsCommentsItemBean>>>() {
            @Override
            public void onSuccess(Call<SuperGoodsListBean<List<GoodsCommentsItemBean>>> call, Response<SuperGoodsListBean<List<GoodsCommentsItemBean>>> response) {
                if (response.body().getCode() == Constants.successCode) {
                    itemAdapter.addList(response.body().getData());
                }
            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<GoodsCommentsItemBean>>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<GoodsCommentsItemBean>>> call, Response<SuperGoodsListBean<List<GoodsCommentsItemBean>>> response) {

            }
        });
    }
}
