package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.MerchantOrderItemAdapter;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;

import butterknife.BindView;

/**
 * 商户订单
 * Created by chen.zhiwei on 2017-12-19.
 */

public class MerchantOrderListFragment extends BaseFragment {

    @BindView(R.id.rvOrderList)
    RecyclerView rvOrderList;
    public static final String bundleName_type = "type";
    private int type = 0;
    private MerchantOrderItemAdapter orderItemAdapter;

    /**
     * 创建fragment
     *
     * @param type
     * @return
     */
    public static Fragment createInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(bundleName_type, type);
        MerchantOrderListFragment fragment = new MerchantOrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_merchant_order;
    }

    @Override
    public void initViewsAndEvents() {
        orderItemAdapter = new MerchantOrderItemAdapter(getContext());
        rvOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOrderList.setAdapter(orderItemAdapter);
//        adapter.setOnItemClickListener(new DecorationRecordListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(OrderItemBean bean, int position) {
//                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
//                startActivity(intent);
//            }
//        });
        LogUtils.e(BaseContext.getInstance().getUserInfo().id);
    }

    @Override
    public void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(bundleName_type);
        }
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
    private void getList(){

    }
}
