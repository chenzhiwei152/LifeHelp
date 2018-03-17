package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.PropertyPaymentMenusAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.MainMenuInfo;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.AutoGridView;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;

import static com.mob.MobSDK.getContext;

/**
 * 物业缴费
 * Created by Administrator on 2018/3/17.
 */

public class PropertyPaymentActivity extends BaseActivity {
    @BindView(R.id.gv_menu)
    AutoGridView gv_menu;
    @BindView(R.id.title_view)
    TitleBar title_view;
    private ArrayList<MainMenuInfo> menus = new ArrayList<>();
    private PropertyPaymentMenusAdapter menusAdapter;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_property_payment;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainMenuInfo menuItem = (MainMenuInfo) parent.getAdapter().getItem(position);
                if (menuItem != null) {
                    if (menuItem.aClass != null) {
                        Intent intent = new Intent(PropertyPaymentActivity.this, menuItem.aClass);
                        if (menuItem.bundle != null) {
                            intent.putExtras(menuItem.bundle);
                        }
                        startActivity(intent);
                    } else {
                        UIUtil.showToast(PropertyPaymentActivity.this, "敬请期待!");
                    }
                }
            }
        });
        initMenus();
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

    /**
     * 初始化菜单
     */
    private void initMenus() {
        //
        Bundle bundle1 = new Bundle();
        bundle1.putString(Constants.homeTypeTag, "82");
        MainMenuInfo Home1 = new MainMenuInfo("物业费缴纳", R.mipmap.ic_bg_property_payment, PaymentInfoActivity.class, bundle1);
        menus.add(Home1);

        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.homeTypeTag, "78");
        MainMenuInfo Home2 = new MainMenuInfo("水费缴纳", R.mipmap.ic_bg_water_pay, PaymentInfoActivity.class, bundle2);
        menus.add(Home2);

        Bundle bundle3 = new Bundle();
        bundle3.putString(Constants.homeTypeTag, "79");
        MainMenuInfo Home3 = new MainMenuInfo("电费缴纳", R.mipmap.ic_bg_electricity_fee_payment, PaymentInfoActivity.class, bundle3);
        menus.add(Home3);

        menusAdapter = new PropertyPaymentMenusAdapter(getContext(), menus);
        gv_menu.setAdapter(menusAdapter);
    }

    private void initTitle() {
        title_view.setTitle("物业缴费");
        title_view.setShowDefaultRightValue();
    }
}
