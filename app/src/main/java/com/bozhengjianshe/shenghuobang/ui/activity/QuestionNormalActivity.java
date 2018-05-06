package com.bozhengjianshe.shenghuobang.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.QuestionListListAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.QuestionBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperGoodsListBean;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class QuestionNormalActivity extends BaseActivity {
    @BindView(R.id.sf_listview)
    RecyclerView sf_listview;
//    @BindView(R.id.swiperefreshlayout)
//    SmartRefreshLayout swiperefreshlayout;
    @BindView(R.id.title_view)
    TitleBar title_view;
    private QuestionListListAdapter questionListListAdapter;
    private Call<SuperGoodsListBean<List<QuestionBean>>> call;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_question_normal;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        sf_listview.setLayoutManager(new LinearLayoutManager(this));


        questionListListAdapter = new QuestionListListAdapter(this);

        sf_listview.setAdapter(questionListListAdapter);
//        swiperefreshlayout.setEnableLoadmore(false);
//        swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                getList();
//            }
//        });
    }

    @Override
    public void loadData() {
        getList();
    }

    private void getList() {
        call = RestAdapterManager.getApi().getQuestion();
        call.enqueue(new JyCallBack<SuperGoodsListBean<List<QuestionBean>>>() {
            @Override
            public void onSuccess(Call<SuperGoodsListBean<List<QuestionBean>>> call, Response<SuperGoodsListBean<List<QuestionBean>>> response) {
                if (response.body().getCode() == Constants.successCode) {
                    questionListListAdapter.ClearData();
                    questionListListAdapter.addList(response.body().getData());
                }
            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<QuestionBean>>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<QuestionBean>>> call, Response<SuperGoodsListBean<List<QuestionBean>>> response) {

            }
        });
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
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("常见问题");
        title_view.setShowDefaultRightValue();
    }
}
