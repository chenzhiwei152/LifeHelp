package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.ShoppingCardListAdapter;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-12-5.
 */

public class ShoppingCartFragment extends BaseFragment {
    @BindView(R.id.title_view)
    TitleBar title_view;
    private ShoppingCardListAdapter listAdapter;
    @BindView(R.id.sf_listview)
    RecyclerView sf_listview;
//    @BindView(R.id.rb_check_all)
//    RadioButton rb_check_all;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected void initViewsAndEvents() {
        initTitle();
        listAdapter = new ShoppingCardListAdapter(getActivity());
        sf_listview.setAdapter(listAdapter);
//        rb_check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                rb_check_all.setChecked(b);
//            }
//        });
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

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("购物车");
        title_view.setTitleColor(Color.WHITE);
        title_view.setLeftImageResource(R.mipmap.ic_title_back);
        title_view.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        title_view.setImmersive(true);
    }
}
