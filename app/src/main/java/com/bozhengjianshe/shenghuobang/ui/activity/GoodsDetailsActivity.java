package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.GoodsDetailItemAdapter;
import com.bozhengjianshe.shenghuobang.ui.adapter.IndexFragmentPagerAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperGoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.bannerBean;
import com.bozhengjianshe.shenghuobang.ui.fragment.GoodsDetailLeftFragment;
import com.bozhengjianshe.shenghuobang.ui.fragment.GoodsDetailRightFragment;
import com.bozhengjianshe.shenghuobang.ui.utils.CollectionUtils;
import com.bozhengjianshe.shenghuobang.ui.utils.ShoppingCardsUtils;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.ImageLoadedrManager;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-21.
 */

public class GoodsDetailsActivity extends BaseActivity implements View.OnClickListener {

    //    @BindView(R.id.sf_listview)
//    RecyclerView sf_listview;
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.tv_goods_detail_describe)
    TextView tv_goods_detail_describe;
    @BindView(R.id.tv_goods_price)
    TextView tv_goods_price;
    @BindView(R.id.iv_add_card)
    ImageView iv_add_card;
    @BindView(R.id.iv_add_star)
    ImageView iv_add_star;
    @BindView(R.id.tv_commit)
    TextView tv_commit;
    @BindView(R.id.tv_all_price)
    TextView tv_all_price;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.nes_scroll)
    NestedScrollView nes_scroll;
    private ConvenientBanner kanner;
    List<bannerBean> list = new ArrayList<>();
    private GoodsDetailItemAdapter listAdapter;
    private Button bt_buy;
    private Button bt_exchange_state;
    private GoodsListBean goodsBean;
    private List<GoodsListBean> goodsBeanList;
    private TextView tv_goods_name;
    private TextView tv_price_title;
    private TextView tv_member_price;
    private TextView tv_member_price_title;
    private Call<SuperGoodsListBean<List<GoodsListBean>>> call;
    private boolean isNewData = true;
    private IndexFragmentPagerAdapter adapter;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    @BindView(R.id.tv_collection_tip)
    TextView tv_collection_tip;
    public static int mCurrentTab = 0;
    private String[] tabNames = {"详情", "评价"};
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.ll_add_card)
    LinearLayout ll_add_card;
    @BindView(R.id.ll_add_star)
    LinearLayout ll_add_star;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    private String type;
    private String id;


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_goods_details;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            UIUtil.showToast("商品id为空");
            finish();
        }
        kanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        bt_buy = (Button) findViewById(R.id.bt_buy);
        bt_exchange_state = (Button) findViewById(R.id.bt_exchange_state);
        tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
        tv_price_title = (TextView) findViewById(R.id.tv_price_title);
        tv_member_price = (TextView) findViewById(R.id.tv_member_price);
        tv_member_price_title = (TextView) findViewById(R.id.tv_member_price_title);
        iv_add_card.setOnClickListener(this);
        iv_add_star.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        ll_add_card.setOnClickListener(this);
        ll_add_star.setOnClickListener(this);

        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new GoodsDetailLeftFragment());
        fragmentList.add(new GoodsDetailRightFragment());
        adapter = new IndexFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        for (int i = 0; i < tabNames.length; i++) {
            View tabView = View.inflate(this, R.layout.layout_tab_item, null);
            TextView textView = (TextView) tabView.findViewById(R.id.tab_title);
            textView.setText(tabNames[i]);
            // 利用这种办法设置图标是为了解决默认设置图标和文字出现的距离较大问题
//            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[i], 0, 0);
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(textView));
        }
        viewPager.setAdapter(adapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = mTabLayout.getSelectedTabPosition();//tab.getPosition();
                mCurrentTab = position;
                viewPager.setCurrentItem(position, false);
//                nes_scroll.smoothScrollTo(0,0);
//                EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.CHECK_AGREE));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

    /**
     * 设置ViewPager的自适应
     *
     * @param childViewHeight
     */
    private void setViewPagerWrapContentHeight(int childViewHeight) {

//        int viewPagerIndex = ll_content.indexOfChild(viewPager);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, childViewHeight);//这里设置params的高度。
//        ll_content.removeView(viewPager);
//        ll_content.addView(viewPager, viewPagerIndex, params);//使用这个params
    }

    @Override
    public void loadData() {
        getGoodsDetail();
    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (eventBusCenter != null) {
            if (eventBusCenter.getEvenCode() == Constants.LOGIN_SUCCESS) {
                isNewData = false;
                getGoodsDetail();
            } else if (eventBusCenter.getEvenCode() == Constants.UPDA_DETAIL_WEBVIEW_HEIGHT) {
//                UIUtil.showToast((String)eventBusCenter.getData());
                setViewPagerWrapContentHeight((Integer) eventBusCenter.getData());
            } else if (eventBusCenter.getEvenCode() == Constants.UPDATE_COLLECTION_SUCCESS) {
                setStar();
            }
        }
    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    private void setDefault() {
        if (!TextUtils.isEmpty(type) && type.equals(Constants.typeService)) {
            //服务类
            tv_commit.setText("立即预约");
            tv_title.setText("建材库");
            tv_goods_detail_describe.setText("服务价格为维修人工费，不包含材料配件费。如需购买材料，请前往建材库");
        } else {
            //建材城
            tv_title.setText("服务商城");
            tv_commit.setText("立即购买");
            tv_goods_detail_describe.setText("标注价格为建材价格以及快递价格，如需相关服务，请前往服务商城");
        }
    }

    private void getGoodsDetail() {
        DialogUtils.showDialog(this, "加载中", false);
        Map<String, String> map = new HashMap<>();
        map.put("ids", id + "");
//        if (type.equals(Constants.typeService)) {
//            map.put("lb", "1");
//        } else {
//            map.put("lb", "2");
//        }
        call = RestAdapterManager.getApi().getGoodsList(map);
        call.enqueue(new JyCallBack<SuperGoodsListBean<List<GoodsListBean>>>() {
            @Override
            public void onSuccess(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Response<SuperGoodsListBean<List<GoodsListBean>>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode && response.body().getData().size() > 0) {
                    goodsBeanList = response.body().getData();
                    goodsBean = response.body().getData().get(0);
                    setData();
                    isNewData = true;

                } else {
                    try {
                        UIUtil.showToast(response.body().getMsg());
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Throwable t) {
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<SuperGoodsListBean<List<GoodsListBean>>> call, Response<SuperGoodsListBean<List<GoodsListBean>>> response) {
                DialogUtils.closeDialog();
                try {
                    ErrorMessageUtils.taostErrorMessage(GoodsDetailsActivity.this, response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setData() {
        if (goodsBean != null) {
            type = goodsBean.getLb() + "";
            setDefault();
            if (goodsBean.getLb() == 2) {
                tv_goods_price.setText(getResources().getString(R.string.money) + (goodsBean.getProfit() + goodsBean.getCost()) + "");
                tv_all_price.setText(getResources().getString(R.string.money) + String.format("%.2f", (goodsBean.getProfit() + goodsBean.getCost() + goodsBean.getFreight())));

            } else {
                tv_goods_price.setText(getResources().getString(R.string.money) + String.format("%.2f", (goodsBean.getFee())) + "");
                tv_all_price.setText(getResources().getString(R.string.money) + String.format("%.2f", goodsBean.getFee()) + "");
            }
            tv_goods_name.setText(goodsBean.getCname());//标题
            List<String> pics = new ArrayList<>();
            for (int i = 0; i < goodsBean.getPicture().size(); i++) {
                pics.add(goodsBean.getPicture().get(i).getImg());
            }
            EventBus.getDefault().post(new EventBusCenter<>(Constants.UPDA_GOODS_DETAIL_H5, pics));
            if (goodsBean.getSowing() != null) {
                //广告位
                for (int i = 0; i < goodsBean.getSowing().size(); i++) {
                    bannerBean bannerBean = new bannerBean();
                    bannerBean.setImage(goodsBean.getSowing().get(i).getImg());
                    list.add(bannerBean);
                }
                //初始化广告栏
                initAD(list);

                setStar();
            }

        }
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("商品详情");
        title_view.setShowDefaultRightValue();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_card:
                //加入购物车
                if (BaseContext.getInstance().getUserInfo() != null) {
                    ShoppingCardsUtils.updateItem(this, goodsBean.getId() + "", "add");
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;
            case R.id.ll_add_star:
                if (BaseContext.getInstance().getUserInfo() != null) {
                    addCollection();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.tv_commit:
                if (BaseContext.getInstance().getUserInfo() != null) {
                    if (goodsBean != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("detail", (Serializable) goodsBeanList);
                        Intent intent = new Intent(this, CommitOrderActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }


                break;
            case R.id.tv_title:
                if (tv_title.getText().equals("建材库")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(Constants.homeTypeTag, "22");
                    Intent intent = new Intent(this, AllBuildingActivity.class);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(Constants.homeTypeTag, "14");
                    Intent intent = new Intent(this, AllServiceActivity.class);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }
                break;
        }
    }

//    /**
//     * 添加到购物车
//     */
//    private void addToCard(Map<String, String> maps) {
//        Map map = new HashMap();
//        map.put("productCount", "1");
//        map.put("productId", goodsBean.getId());
//        map.put("productType", type);
//        map.put("userId", BaseContext.getInstance().getUserInfo().id);
//        Call<SuperBean<String>> addTocard = RestAdapterManager.getApi().addTocard(map);
//        addTocard.enqueue(new JyCallBack<SuperBean<String>>() {
//            @Override
//            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
//                UIUtil.showToast(response.body().getMsg());
//            }
//
//            @Override
//            public void onError(Call<SuperBean<String>> call, Throwable t) {
//
//            }
//
//            @Override
//            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
//                try {
//                    ErrorMessageUtils.taostErrorMessage(GoodsDetailsActivity.this, response.errorBody().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * 添加到搜藏夹
     */
    private void addCollection() {

//        RequestBody formBody = new FormBody.Builder()
//                .add("productId", goodsBean.getId() + "")
//                .add("productType", type).add("userId", BaseContext.getInstance().getUserInfo().id)
//                .build();
//
//        Call<SuperBean<String>> addCollection = RestAdapterManager.getApi().addCollection(formBody);
//        addCollection.enqueue(new JyCallBack<SuperBean<String>>() {
//            @Override
//            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
//                UIUtil.showToast(response.body().getMsg());
//            }
//
//            @Override
//            public void onError(Call<SuperBean<String>> call, Throwable t) {
//
//            }
//
//            @Override
//            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
//
//            }
//        });
        if (CollectionUtils.getIsInCollection(goodsBean.getId() + "")) {
            CollectionUtils.updateColloction(this, goodsBean.getId() + "", "delete");
        } else {
            CollectionUtils.updateColloction(this, goodsBean.getId() + "", "add");
        }
    }

    private void setStar() {
        if (CollectionUtils.getIsInCollection(goodsBean.getId() + "")) {
            iv_add_star.setImageResource(R.mipmap.icon_collect);
            tv_collection_tip.setText("已收藏");
        } else {
            iv_add_star.setImageResource(R.mipmap.icon_uncollect);
            tv_collection_tip.setText("收藏");
        }
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
            ImageLoadedrManager.getInstance().display(GoodsDetailsActivity.this, data.getImage(), imageView);
        }
    }

    private void initAD(final List<bannerBean> list) {

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

    }


    @Override
    protected void onDestroy() {
        if (call != null) {
            call.cancel();
        }
        super.onDestroy();
    }
}
