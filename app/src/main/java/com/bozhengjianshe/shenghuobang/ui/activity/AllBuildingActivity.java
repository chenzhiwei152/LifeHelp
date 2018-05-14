package com.bozhengjianshe.shenghuobang.ui.activity;

import android.os.Bundle;
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
import com.bozhengjianshe.shenghuobang.ui.adapter.AllBuildingTypeListAdapter;
import com.bozhengjianshe.shenghuobang.ui.adapter.TypeListItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceTypeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperGoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;
import com.bozhengjianshe.shenghuobang.view.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

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
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.edit_search)
    EditText edit_search;
    @BindView(R.id.bt_customer)
    ImageView bt_customer;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    AllBuildingTypeListAdapter typeListAdapter;
    TypeListItemAdapter contentListAdapter;
    private int pageNumber = 1;
    private int pageSize = 10;
    private String classify;
    private String keyWord;
    private Call<SuperGoodsListBean<List<GoodsListBean>>> goodsListCall;


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
        bt_customer.setVisibility(View.GONE);
        initTitle();
        tv_location.setText(BaseContext.getInstance().city);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rc_type_list.setLayoutManager(layoutManager);
        sf_content_listview.setLayoutManager(new LinearLayoutManager(this));
        sf_content_listview.setNestedScrollingEnabled(false);

        typeListAdapter = new AllBuildingTypeListAdapter(this);
        contentListAdapter = new TypeListItemAdapter(this);

        rc_type_list.setAdapter(typeListAdapter);
        sf_content_listview.setAdapter(contentListAdapter);
        swiperefreshlayout.setEnableLoadmore(false);
        swiperefreshlayout.setEnableRefresh(false);
//        swiperefreshlayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                getContentList();
//            }
//
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                pageNumber = 1;
//                getContentList();
//            }
//        });
        contentListAdapter.setOneType("2");
        typeListAdapter.setOnClickListerner(new CommonOnClickListerner() {
            @Override
            public void myOnClick(Object data) {
                classify = ((AllServiceTypeBean) data).getId() + "";
                contentListAdapter.setSecondType(((AllServiceTypeBean) data).getId());
                contentListAdapter.setSecondType(((AllServiceTypeBean) data).getId());
                getContentList(((AllServiceTypeBean) data).getId()+"");
            }
        });
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                    String keytag = edit_search.getText().toString().trim();

                    if (TextUtils.isEmpty(keytag)) {
                        Toast.makeText(AllBuildingActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    // 搜索功能主体
//                    getContentList();
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
//                getContentList();
            }
        });
    }

    @Override
    public void loadData() {
        getTyleList();
//        getContentList();
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
     * 获取二级分类列表
     */
    private void getTyleList() {
        Call<SuperGoodsListBean<List<AllServiceTypeBean>>> getAllServiceTypeList = RestAdapterManager.getApi().getAllServiceTypeList("2");
        getAllServiceTypeList.enqueue(new JyCallBack<SuperGoodsListBean<List<AllServiceTypeBean>>>() {
            @Override
            public void onSuccess(Call<SuperGoodsListBean<List<AllServiceTypeBean>>> call, Response<SuperGoodsListBean<List<AllServiceTypeBean>>> response) {
                if (response != null && response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                    typeListAdapter.ClearData();
                    typeListAdapter.addList(response.body().getData());
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if ((response.body().getData().get(i).getId() + "").equals(classify)) {
                            typeListAdapter.setSelectedPosition(i);
                            rc_type_list.scrollToPosition(i);
                            getContentList(classify);
                            break;
                        }
                    }
                }

            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<AllServiceTypeBean>>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<AllServiceTypeBean>>> call, Response<SuperGoodsListBean<List<AllServiceTypeBean>>> response) {

            }
        });
    }

    /**
     * 获取三级分类
     *
     * @param threeLevelid
     */
    private void getContentList(String threeLevelid) {

        Call<SuperGoodsListBean<List<AllServiceTypeBean>>> getAllServiceTypeList = RestAdapterManager.getApi().getAllServiceTypeList(threeLevelid);
        getAllServiceTypeList.enqueue(new JyCallBack<SuperGoodsListBean<List<AllServiceTypeBean>>>() {
            @Override
            public void onSuccess(Call<SuperGoodsListBean<List<AllServiceTypeBean>>> call, Response<SuperGoodsListBean<List<AllServiceTypeBean>>> response) {
                contentListAdapter.ClearData();
                if (response != null && response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                    if (response.body() != null)
                        contentListAdapter.addList(response.body().getData());

                }

            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<AllServiceTypeBean>>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<AllServiceTypeBean>>> call, Response<SuperGoodsListBean<List<AllServiceTypeBean>>> response) {

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

    @Override
    protected void onDestroy() {
        if (goodsListCall != null) {
            goodsListCall.cancel();
        }
        super.onDestroy();
    }
}
