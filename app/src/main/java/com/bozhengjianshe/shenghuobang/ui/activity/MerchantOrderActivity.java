package com.bozhengjianshe.shenghuobang.ui.activity;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.MerchantOrderListFragmentAdapter;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 商户订单
 * Created by chen.zhiwei on 2017-12-25.
 */

public class MerchantOrderActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private List<String> tabName = Arrays.asList("订单市场", "待处理", "订单历史");
    private int[] tabIcons = {
            R.drawable.selector_main_tab_order_market,
            R.drawable.selector_main_tab_order_handle,
            R.drawable.selector_main_tab_order_history
    };

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_merchant_order;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        viewPager.setAdapter(new MerchantOrderListFragmentAdapter(getSupportFragmentManager(), tabName));
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @Override
    public void loadData() {

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

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
        tabLayout.getTabAt(2).setCustomView(getTabView(2));
    }


    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tablayout, null);
        TextView txt_title = view.findViewById(R.id.txt_title);
        txt_title.setText(tabName.get(position));
        ImageView img_title = view.findViewById(R.id.img_title);
        img_title.setImageResource(tabIcons[position]);

//        if (position == 0) {
//            txt_title.setTextColor(Color.YELLOW);
//            img_title.setImageResource(tabIconsPressed[position]);
//        } else {
//            txt_title.setTextColor(Color.WHITE);
//            img_title.setImageResource(tabIcons[position]);
//        }
        return view;
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle(R.string.app_name);
        title_view.addAction(new TitleBar.ImageAction(R.mipmap.ic_tab_me_normal) {
            @Override
            public void performAction(View view) {
                Intent intent = new Intent(new Intent(MerchantOrderActivity.this, PersonInformationActivity.class));
                intent.putExtra("from", "Merchant");
                startActivity(intent);
            }
        });
    }
}
