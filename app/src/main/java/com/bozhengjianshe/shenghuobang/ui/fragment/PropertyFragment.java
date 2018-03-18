package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ViewFlipper;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.activity.AllServiceActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.OwnerLeaveMessageActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.PropertyOne2OneActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.PropertyPaymentActivity;
import com.bozhengjianshe.shenghuobang.ui.adapter.MainMenusAdapter;
import com.bozhengjianshe.shenghuobang.ui.bean.MainMenuInfo;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.AutoGridView;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 物业
 * Created by chen.zhiwei on 2018-1-29.
 */

public class PropertyFragment extends BaseFragment {
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.gv_menu)
    AutoGridView gv_menu;
    @BindView(R.id.vf_ad)
    ViewFlipper vf_ad;
    @BindView(R.id.title_view)
    TitleBar title_view;
    private ArrayList<MainMenuInfo> menus = new ArrayList<>();
    private MainMenusAdapter menusAdapter;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_property;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle();
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
        initMenus();
        initViewFlipper();
    }

    /**
     * 初始化菜单
     */
    private void initMenus() {
        //
        Bundle bundle1 = new Bundle();
        bundle1.putString(Constants.homeTypeTag, "82");
        MainMenuInfo Home1 = new MainMenuInfo("物业缴费", R.mipmap.ic_home_31, PropertyPaymentActivity.class, bundle1);
        menus.add(Home1);

        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.homeTypeTag, "78");
        MainMenuInfo Home2 = new MainMenuInfo("物业一对一", R.mipmap.ic_home_32, PropertyOne2OneActivity.class, bundle2);
        menus.add(Home2);

        Bundle bundle3 = new Bundle();
        bundle3.putString(Constants.homeTypeTag, "79");
        MainMenuInfo Home3 = new MainMenuInfo("业主留言", R.mipmap.ic_home_33, OwnerLeaveMessageActivity.class, bundle3);
        menus.add(Home3);

        Bundle bundle4 = new Bundle();
        bundle4.putString(Constants.homeTypeTag, "76");
        MainMenuInfo Home4 = new MainMenuInfo("清洁巡检", R.mipmap.ic_home_34, AllServiceActivity.class, bundle4);
        menus.add(Home4);
        Bundle bundle5 = new Bundle();
        bundle5.putString(Constants.homeTypeTag, "81");
        MainMenuInfo Home5 = new MainMenuInfo("设施巡检", R.mipmap.ic_home_35, AllServiceActivity.class, bundle5);
        menus.add(Home5);

        Bundle bundle6 = new Bundle();
        bundle6.putString(Constants.homeTypeTag, "77");
        MainMenuInfo Home6 = new MainMenuInfo("安保巡检", R.mipmap.ic_home_36, AllServiceActivity.class, bundle6);
        menus.add(Home6);

        menusAdapter = new MainMenusAdapter(getContext(), menus);
        gv_menu.setAdapter(menusAdapter);
    }

    private void initViewFlipper() {
        vf_ad.addView(View.inflate(getActivity(), R.layout.view_flipper_layout, null));
        vf_ad.addView(View.inflate(getActivity(), R.layout.view_flipper_layout, null));
        vf_ad.addView(View.inflate(getActivity(), R.layout.view_flipper_layout, null));
        vf_ad.addView(View.inflate(getActivity(), R.layout.view_flipper_layout, null));
        vf_ad.addView(View.inflate(getActivity(), R.layout.view_flipper_layout, null));
        vf_ad.addView(View.inflate(getActivity(), R.layout.view_flipper_layout, null));
    }

    @Override
    protected void loadData() {

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
    private void initTitle() {
        title_view.setTitle("物业");
    }
}
