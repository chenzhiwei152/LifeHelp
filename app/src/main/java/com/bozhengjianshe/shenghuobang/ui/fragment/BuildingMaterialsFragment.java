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
import com.bozhengjianshe.shenghuobang.ui.activity.AllBuildingActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.GoodsDetailsActivity;
import com.bozhengjianshe.shenghuobang.ui.adapter.BuildingListItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.adapter.MainMenusAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.MainMenuInfo;
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

public class BuildingMaterialsFragment extends BaseFragment {
    @BindView(R.id.sf_listview)
    RecyclerView sf_listview;
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
    List<bannerBean> list = new ArrayList<>();
    List<GoodsListBean> adList = new ArrayList<>();
    private BuildingListItemAdapter listAdapter;

    private Call<GoodsListBean> goodsListCall;
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
        return R.layout.fragment_building_layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initViewsAndEvents() {
//        initTitle();


        sf_listview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        sf_listview.setNestedScrollingEnabled(false);
        swiperefreshlayout.setEnableHeaderTranslationContent(false);
        swiperefreshlayout.setEnableLoadmore(false);
        swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getList();
            }
        });
//        swiperefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                getList();
//            }
//        });
        listAdapter = new BuildingListItemAdapter(getActivity());
        sf_listview.setAdapter(listAdapter);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(edit_search.getText().toString())) {
                    keyWord = edit_search.getText().toString();
                    getList();
                    UIUtil.ShowOrHideSoftInput(getActivity(), false);
                }
            }
        });
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_search.setText("");
                keyWord = "";
                getList();
            }
        });
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
    }

    @Override
    protected void loadData() {
//        getAdList();
//        getFilterList();
        getList();
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
        bundle1.putString(Constants.homeTypeTag,"1");
        MainMenuInfo Home1 = new MainMenuInfo("居家常备", R.mipmap.ic_home_1, AllBuildingActivity.class, bundle1);
        menus.add(Home1);

        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.homeTypeTag,"5");
        MainMenuInfo Home2 = new MainMenuInfo("五金挂件", R.mipmap.ic_home_2, AllBuildingActivity.class, bundle2);
        menus.add(Home2);

        Bundle bundle3 = new Bundle();
        bundle3.putString(Constants.homeTypeTag,"7");
        MainMenuInfo Home3 = new MainMenuInfo("家居家纺", R.mipmap.ic_home_3, AllBuildingActivity.class, bundle3);
        menus.add(Home3);

        Bundle bundle4 = new Bundle();
        bundle4.putString(Constants.homeTypeTag,"3");
        MainMenuInfo Home4 = new MainMenuInfo("装修装饰", R.mipmap.ic_home_4, AllBuildingActivity.class, bundle4);
        menus.add(Home4);
        Bundle bundle5 = new Bundle();
        bundle5.putString(Constants.homeTypeTag,"6");
        MainMenuInfo Home5 = new MainMenuInfo("厨房卫浴", R.mipmap.ic_home_5, AllBuildingActivity.class, bundle5);
        menus.add(Home5);

        Bundle bundle6 = new Bundle();
        bundle6.putString(Constants.homeTypeTag,"4");
        MainMenuInfo Home6 = new MainMenuInfo("灯饰照明", R.mipmap.ic_home_6, AllBuildingActivity.class, bundle6);
        menus.add(Home6);
        Bundle bundle7 = new Bundle();
        bundle7.putString(Constants.homeTypeTag,"2");
        MainMenuInfo Home7 = new MainMenuInfo("水电材料", R.mipmap.ic_home_7, AllBuildingActivity.class, bundle7);
        menus.add(Home7);
        Bundle bundle8 = new Bundle();
        bundle8.putString(Constants.homeTypeTag,"8");
        MainMenuInfo Home8 = new MainMenuInfo("绿植花卉", R.mipmap.ic_home_8, AllBuildingActivity.class, bundle8);
        menus.add(Home8);



        menusAdapter = new MainMenusAdapter(getContext(), menus);
        gv_menu.setAdapter(menusAdapter);
    }


    private void getList() {
        Map<String, String> map = new HashMap<>();
        goodsListCall = RestAdapterManager.getApi().getGoodsList("2");
        goodsListCall.enqueue(new JyCallBack<GoodsListBean>() {
            @Override
            public void onSuccess(Call<GoodsListBean> call, Response<GoodsListBean> response) {
                swiperefreshlayout.finishRefresh();
                swiperefreshlayout.finishLoadmore();
                if (response != null && response.body() != null && response.body().code == Constants.successCode) {
                    if (response.body().getData().getRecGoods().size() > 0) {
                        ll_empty.setVisibility(View.GONE);
                        sf_listview.setVisibility(View.VISIBLE);
                        listAdapter.ClearData();
                        listAdapter.addList(response.body().getData().getRecGoods());
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
                    if (response.body().getData().getBanners().size() > 0) {
                        for (int i = 0; i < response.body().getData().getBanners().size(); i++) {
                            bannerBean bannerBean = new bannerBean();
                            bannerBean.setImage(response.body().getData().getBanners().get(i).getImg());
                            list.add(bannerBean);
                        }
                        initAD(list);
                    }

                } else {
                    try {
                        UIUtil.showToast(response.body().msg);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(Call<GoodsListBean> call, Throwable t) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                    swiperefreshlayout.finishLoadmore();
                }

            }

            @Override
            public void onError(Call<GoodsListBean> call, Response<GoodsListBean> response) {
                if (swiperefreshlayout != null) {
                    swiperefreshlayout.finishRefresh();
                    swiperefreshlayout.finishLoadmore();
                }
            }
        });
    }

    //筛选
//    private void setFilter() {
//        types.clear();
//        popupViews.clear();
//        //益智类
//        final MaxHeighListView sortView = new MaxHeighListView(getActivity());
//        sortView.setDividerHeight(0);
//        sortView.setMaxHeight(199);
//        mMenuAdapter = new PoPuMenuListAdapter(getActivity(), typesList);
//        sortView.setAdapter(mMenuAdapter);
//        sortView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                dropDownMenu.setTabText(typesList.get(position).getTypename());
//                dropDownMenu.closeMenu();
//                goodstype = typesList.get(position).getId() + "";
//                getList();
//            }
//        });
//        types.add("全部");
//        popupViews.add(sortView);
//
//
//        final MaxHeighListView softView = new MaxHeighListView(getActivity());
//        softView.setDividerHeight(0);
//        softView.setMaxHeight(199);
//        mMenuAdapter2 = new PoPuMenuListShopAdapter(getActivity(), shopsList);
//        softView.setAdapter(mMenuAdapter2);
//        softView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                dropDownMenu.setTabText(shopsList.get(position).getShopname());
//                dropDownMenu.closeMenu();
//                shop = shopsList.get(position).getId() + "";
//                getList();
//            }
//        });
//        types.add("全部");
//        popupViews.add(softView);
//        dropDownMenu.setDropDownMenu(types, popupViews);
//    }



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
                bundle.putString("type", "1");
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
