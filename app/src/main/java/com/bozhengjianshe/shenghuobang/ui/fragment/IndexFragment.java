package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.activity.AllServiceActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.GoodsDetailsActivity;
import com.bozhengjianshe.shenghuobang.ui.adapter.MainListItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.adapter.MainMenusAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.MainMenuInfo;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperGoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.bannerBean;
import com.bozhengjianshe.shenghuobang.utils.ImageLoadedrManager;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.AutoGridView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-19.
 */

public class IndexFragment extends BaseFragment {
    @BindView(R.id.sf_listview)
    RecyclerView sf_listview;
    @BindView(R.id.sf_recomment_listview)
    RecyclerView sf_recomment_listview;
    @BindView(R.id.convenientBanner)
    ConvenientBanner kanner;
    @BindView(R.id.swiperefreshlayout)
    SmartRefreshLayout swiperefreshlayout;
    @BindView(R.id.edit_search)
    EditText edit_search;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    @BindView(R.id.bt_search)
    TextView bt_search;
    @BindView(R.id.gv_menu)
    AutoGridView gv_menu;
    @BindView(R.id.rl_search_view)
    RelativeLayout rl_search_view;
    List<bannerBean> list = new ArrayList<>();
    List<GoodsListBean> adList = new ArrayList<>();
    private MainListItemAdapter listAdapter;
    private MainListItemAdapter listAdapter1;

    private Call<SuperGoodsListBean<List<GoodsListBean>>> goodsListCall;
    private ArrayList<MainMenuInfo> menus = new ArrayList<>();
    private MainMenusAdapter menusAdapter;

    //筛选的关键字
    private String goodstype;
    private String shop;
    private String keyWord;
    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_index_layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initViewsAndEvents() {
//        initTitle();


        sf_listview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        sf_listview.setNestedScrollingEnabled(false);
        sf_recomment_listview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        sf_recomment_listview.setNestedScrollingEnabled(false);
        swiperefreshlayout.setEnableHeaderTranslationContent(false);
        swiperefreshlayout.setEnableLoadmore(false);
        swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getList(1);
                getList(2);
            }
        });
//        swiperefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                getList();
//            }
//        });
        listAdapter = new MainListItemAdapter(getActivity());
        listAdapter1 = new MainListItemAdapter(getActivity());
        sf_listview.setAdapter(listAdapter);
        sf_recomment_listview.setAdapter(listAdapter1);
        edit_search.clearFocus();
        edit_search.setFocusable(false);
//        bt_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!TextUtils.isEmpty(edit_search.getText().toString())) {
//                    keyWord = edit_search.getText().toString();
//                    getList();
//                    UIUtil.ShowOrHideSoftInput(getActivity(), false);
//                }
//            }
//        });
//        iv_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                edit_search.setText("");
//                keyWord = "";
//                getList();
//            }
//        });
        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainMenuInfo menuItem = (MainMenuInfo) parent.getAdapter().getItem(position);
                if (menuItem != null) {
                    if (menuItem.aClass != null) {
                        Intent intent = new Intent(getActivity(), menuItem.aClass);
                        if (menuItem.bundle != null) {
                            intent.putExtras(menuItem.bundle);
                        }
                        startActivity(intent);
                    } else {
                        UIUtil.showToast(getActivity(), "敬请期待!");
                    }
                }
            }
        });
        edit_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constants.homeTypeTag,"82");
                Intent intent=new Intent(getActivity(),AllServiceActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {
//        getAdList();
//        getFilterList();
        getList(1);
        getList(2);
        initMenus();
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
            if (eventBusCenter.getEvenCode() == Constants.LOGIN_FAILURE || eventBusCenter.getEvenCode() == Constants.LOGIN_SUCCESS) {
                loadData();
            }
        }

    }

    /**
     * 初始化菜单
     */
    private void initMenus() {
        //
        Bundle bundle1 = new Bundle();
        bundle1.putString(Constants.homeTypeTag,"82");
        MainMenuInfo Home1 = new MainMenuInfo("补漏疏通", R.mipmap.ic_home_1, AllServiceActivity.class, bundle1);
        menus.add(Home1);

        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.homeTypeTag,"78");
        MainMenuInfo Home2 = new MainMenuInfo("门窗锁具", R.mipmap.ic_home_2, AllServiceActivity.class, bundle2);
        menus.add(Home2);

        Bundle bundle3 = new Bundle();
        bundle3.putString(Constants.homeTypeTag,"79");
        MainMenuInfo Home3 = new MainMenuInfo("水路电路", R.mipmap.ic_home_3, AllServiceActivity.class, bundle3);
        menus.add(Home3);

        Bundle bundle4 = new Bundle();
        bundle4.putString(Constants.homeTypeTag,"76");
        MainMenuInfo Home4 = new MainMenuInfo("居家安装", R.mipmap.ic_home_4, AllServiceActivity.class, bundle4);
        menus.add(Home4);
        Bundle bundle5 = new Bundle();
        bundle5.putString(Constants.homeTypeTag,"81");
        MainMenuInfo Home5 = new MainMenuInfo("保洁清洗", R.mipmap.ic_home_5, AllServiceActivity.class, bundle5);
        menus.add(Home5);

        Bundle bundle6 = new Bundle();
        bundle6.putString(Constants.homeTypeTag,"77");
        MainMenuInfo Home6 = new MainMenuInfo("搬家搬运", R.mipmap.ic_home_6, AllServiceActivity.class, bundle6);
        menus.add(Home6);
        Bundle bundle7 = new Bundle();
        bundle7.putString(Constants.homeTypeTag,"75");
        MainMenuInfo Home7 = new MainMenuInfo("家庭维修", R.mipmap.ic_home_7, AllServiceActivity.class, bundle7);
        menus.add(Home7);
        Bundle bundle8 = new Bundle();
        bundle8.putString(Constants.homeTypeTag,"80");
        MainMenuInfo Home8 = new MainMenuInfo("局部改造", R.mipmap.ic_home_8, AllServiceActivity.class, bundle8);
        menus.add(Home8);

        menusAdapter = new MainMenusAdapter(getContext(), menus);
        gv_menu.setAdapter(menusAdapter);
    }


    private void getList(final int tag) {
        Map<String, String> map = new HashMap<>();
        map.put("sftj",tag+"");//1 时查询推荐产品 2时为非推荐商品 不传值则全部查询
        map.put("lb","1");//1 查询服务类商品 为 2 查询建材类商品 不传值则全部查询
        goodsListCall = RestAdapterManager.getApi().getGoodsList(map);
        goodsListCall.enqueue(new JyCallBack<SuperGoodsListBean<List<GoodsListBean>>>() {
            @Override
            public void onSuccess(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Response<SuperGoodsListBean<List<GoodsListBean>>> response) {
                swiperefreshlayout.finishRefresh();
                swiperefreshlayout.finishLoadmore();
                if (response != null && response.body() != null && response.body().state == Constants.successCode) {
                    if (response.body().getData()!=null&&response.body().getData().size() > 0) {
                        ll_empty.setVisibility(View.GONE);
                        sf_listview.setVisibility(View.VISIBLE);
                        if (tag==1){
                            listAdapter.ClearData();
                            listAdapter.addList(response.body().getData());
                        }else {
                            listAdapter1.ClearData();
                            listAdapter1.addList(response.body().getData());
                        }
                        pageNum++;
                    } else {
                        if (!TextUtils.isEmpty(keyWord)) {
                            //搜索数据为空
//                            tv_no_data.setText("没有“" + keyWord + "”的搜索结果");
//                            ll_empty.setVisibility(View.VISIBLE);
//                            sf_listview.setVisibility(View.GONE);
//                            listAdapter.ClearData();
                        } else {
                            ll_empty.setVisibility(View.GONE);
                            sf_listview.setVisibility(View.VISIBLE);
                            listAdapter.ClearData();
                            listAdapter1.ClearData();
//                            if (pageNum == 1) {
//                                //无数据
//                                listAdapter.ClearData();
//                            } else {
//                                //加载完全部数据
//                                UIUtil.showToast("已加载完全部数据");
//                                swiperefreshlayout.setEnableLoadmore(false);
//                            }
                        }
                    }
//                    if (response.body().getData().getBanners().size() > 0) {
//                        list.clear();
//                        for (int i = 0; i < response.body().getData().getBanners().size(); i++) {
//                            bannerBean bannerBean = new bannerBean();
//                            bannerBean.setImage(response.body().getData().getBanners().get(i).getImg());
//                            bannerBean.setId(response.body().getData().getBanners().get(i).getId());
//                            bannerBean.setType(response.body().getData().getBanners().get(i).getType());
//                            list.add(bannerBean);
//                        }
//                        initAD(list);
//                    }

                } else {
                    try {
                        UIUtil.showToast(response.body().message);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Throwable t) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                    swiperefreshlayout.finishLoadmore();
                }

            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Response<SuperGoodsListBean<List<GoodsListBean>>> response) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                    swiperefreshlayout.finishLoadmore();
                }
            }
        });
    }




    /**
     * 广告栏定义图片地址
     */
    public class LocalImageHolderView implements Holder<bannerBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, bannerBean data) {
            ImageLoadedrManager.getInstance().display(getActivity(), data.getImage(), imageView);
        }
    }

    private void initAD(final List<bannerBean> list) {
//        View header = LayoutInflater.from(getActivity()).inflate(R.layout.kanner_index, sf_listview, false);//headview,广告栏


//自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        kanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.dot_blur, R.mipmap.dot_black})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).startTurning(3000);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
//        convenientBanner.setManualPageable(false);//设置不能手动影响

        kanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", list.get(position).getId()+"");
                bundle.putString("type", list.get(position).getType()+"");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//增加headview
//        sf_listview.addHeaderView(header);
//        sf_listview.add
    }

    @Override
    public void onDestroy() {
        if (goodsListCall != null) {
            goodsListCall.cancel();
        }
        super.onDestroy();
    }
}
