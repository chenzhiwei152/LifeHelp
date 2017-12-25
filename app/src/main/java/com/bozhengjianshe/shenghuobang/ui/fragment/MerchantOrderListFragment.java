package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.MerchantOrderItemAdapter;

import butterknife.BindView;

/**
 * 装修记录
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
    }

    @Override
    public void loadData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(bundleName_type);
        }
//        LogUtils.d("liu=================");
//        orderList.add(new OrderItemBean("http://upload-images.jianshu.io/upload_images/3722695-5ad19f0c6b812be1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700",
//                "2017-12-17", "张灿", "34545"));
//        orderList.add(new OrderItemBean("https://image1.jyall.com/v1/tfs/T1TyxQB4hT1RXrhCrK.jpg",
//                "2017-12-17", "张灿", "34545"));
//        orderList.add(new OrderItemBean("http://upload-images.jianshu.io/upload_images/3722695-5ad19f0c6b812be1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700",
//                "2017-12-17", "张灿", "34545"));
//        orderList.add(new OrderItemBean("https://image1.jyall.com/v1/tfs/T1rxC_BQKT1RXrhCrK.jpg",
//                "2017-12-17", "张灿", "34545"));
//        adapter.setDatas(orderList);
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
}
