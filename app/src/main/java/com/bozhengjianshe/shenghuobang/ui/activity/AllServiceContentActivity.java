package com.bozhengjianshe.shenghuobang.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.MainListItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceTypeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperGoodsListBean;
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

public class AllServiceContentActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    //    @BindView(R.id.rc_type_list)
//    RecyclerView rc_type_list;
    @BindView(R.id.sf_content_listview)
    RecyclerView sf_content_listview;
    @BindView(R.id.swiperefreshlayout)
    SmartRefreshLayout swiperefreshlayout;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.edit_search)
    EditText edit_search;
    @BindView(R.id.bt_customer)
    ImageView bt_customer;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    //    AllServiceTypeListAdapter typeListAdapter;
    MainListItemAdapter contentListAdapter;
    private int pageNumber = 1;
    private int pageSize = 10;
    private String classifyOne;
    private String classifyTwo;
    private String classifyThree;
    private String classifySuper;
    String title = "";
    private String keyWord;
    private Call<SuperGoodsListBean<List<AllServiceTypeBean>>> getAllServiceTypeList;
    private Call<SuperGoodsListBean<List<GoodsListBean>>> goodsListCall;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_all_service_content;
    }

    @Override
    public void initViewsAndEvents() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classifyOne = bundle.getString(Constants.homeTypeTag);
            classifyTwo = bundle.getString(Constants.homeTypeTagTwo);
            classifyThree = bundle.getString(Constants.homeTypeTagThree);
            classifySuper = bundle.getString(Constants.homeTypeTagSuper);
            title = bundle.getString("title","商品列表");
        }
        bt_customer.setVisibility(View.GONE);
        initTitle(title);
        tv_location.setText(BaseContext.getInstance().city);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        rc_type_list.setLayoutManager(layoutManager);
        sf_content_listview.setLayoutManager(new GridLayoutManager(this, 2));
        sf_content_listview.setNestedScrollingEnabled(false);

//        typeListAdapter = new AllServiceTypeListAdapter(this);
        contentListAdapter = new MainListItemAdapter(this);

//        rc_type_list.setAdapter(typeListAdapter);
        sf_content_listview.setAdapter(contentListAdapter);
        swiperefreshlayout.setEnableLoadmore(false);
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
//        typeListAdapter.setOnClickListerner(new CommonOnClickListerner() {
//            @Override
//            public void myOnClick(Object data) {
//                classify = ((AllServiceTypeBean) data).getId() + "";
//                pageNumber = 1;
//                getContentList();
//            }
//        });
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                    String keytag = edit_search.getText().toString().trim();

                    if (TextUtils.isEmpty(keytag)) {
                        Toast.makeText(AllServiceContentActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    // 搜索功能主体
                    getContentList();
                    return true;
                }
                return false;
            }
        });
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(edit_search.getText().toString())) {
                    iv_clear.setVisibility(View.GONE);
                } else {
                    iv_clear.setVisibility(View.VISIBLE);
                }
            }
        });
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_search.setText("");
                getContentList();
            }
        });

    }

    @Override
    public void loadData() {
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

//    private void getTyleList() {
//        getAllServiceTypeList = RestAdapterManager.getApi().getAllServiceTypeList("1");
//        getAllServiceTypeList.enqueue(new JyCallBack<SuperGoodsListBean<List<AllServiceTypeBean>>>() {
//            @Override
//            public void onSuccess(Call<SuperGoodsListBean<List<AllServiceTypeBean>>> call, Response<SuperGoodsListBean<List<AllServiceTypeBean>>> response) {
//                if (response != null && response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
//                    typeListAdapter.ClearData();
//                    typeListAdapter.addList(response.body().getData());
//                    for (int i = 0; i < response.body().getData().size(); i++) {
//                        if ((response.body().getData().get(i).getId() + "").equals(classify)) {
//                            typeListAdapter.setSelectedPosition(i);
//                            rc_type_list.scrollToPosition(i);
//                            break;
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onError(Call<SuperGoodsListBean<List<AllServiceTypeBean>>> call, Throwable t) {
//
//            }
//
//            @Override
//            public void onError(Call<SuperGoodsListBean<List<AllServiceTypeBean>>> call, Response<SuperGoodsListBean<List<AllServiceTypeBean>>> response) {
//
//            }
//        });
//    }

    private void getContentList() {

        Map<String, String> map = new HashMap<>();
        map.put("lb", classifySuper);//1 查询服务类商品 为 2 查询建材类商品 不传值则全部查询
        map.put("yjfl", classifyOne);//根据一级分类的id获取其目录下产品 不传值则全部查询
        map.put("ejfl", classifyTwo);//根据一级分类的id获取其目录下产品 不传值则全部查询
        map.put("sjfl", classifyThree);//根据一级分类的id获取其目录下产品 不传值则全部查询
        map.put("name", edit_search.getText().toString());
        goodsListCall = RestAdapterManager.getApi().getGoodsList(map);
        goodsListCall.enqueue(new JyCallBack<SuperGoodsListBean<List<GoodsListBean>>>() {
            @Override
            public void onSuccess(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Response<SuperGoodsListBean<List<GoodsListBean>>> response) {
                swiperefreshlayout.finishLoadmore();
                swiperefreshlayout.finishRefresh();
//                if (pageNumber == 1) {
                contentListAdapter.ClearData();
//                }
                if (response != null && response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                    contentListAdapter.addList(response.body().getData());
                    pageNumber++;
                    swiperefreshlayout.setEnableLoadmore(false);
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
                    ErrorMessageUtils.taostErrorMessage(AllServiceContentActivity.this, response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 初始化标题
     */
    private void initTitle(String title) {
        title_view.setTitle(title);
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
