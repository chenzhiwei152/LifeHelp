package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bozhengjianshe.shenghuobang.ui.fragment.MerchantOrderListFragment;

import java.util.List;

/**
 * Created by chen,zhiwei on 2017-12-19.
 */

public class MerchantOrderListFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> tabNames;

    public MerchantOrderListFragmentAdapter(FragmentManager fm, List<String> tabNames) {
        super(fm);
        this.tabNames = tabNames;
    }

    @Override
    public MerchantOrderListFragment getItem(int position) {
        int type = position;
        return MerchantOrderListFragment.createInstance(type);
    }

    @Override
    public int getCount() {
        return tabNames != null ? tabNames.size() : 0;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames != null ? tabNames.get(position) : "";
    }
}
