package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;
import com.bozhengjianshe.shenghuobang.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenzhiwei 2016/6/14.
 */
public class MerchantOrderItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<BuyOrderListItemBean> list;
    private static Context context;
    private boolean isLight;
    private final LayoutInflater mLayoutInflater;

    public MerchantOrderItemAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public MerchantOrderItemAdapter(Context context, List<BuyOrderListItemBean> items) {
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(items);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<BuyOrderListItemBean> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    public void ClearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public static List<BuyOrderListItemBean> getEntities() {
        return list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.member_rank_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (list != null) {
            ((ImageViewHolder) viewHolder).tv_time.setText(list.get(position).getAddtime());
            ((ImageViewHolder) viewHolder).mi_contract.getRightText().setText(list.get(position).getLxrxm());
            ((ImageViewHolder) viewHolder).mi_tel.getRightText().setText(list.get(position).getLxrdh());
            ((ImageViewHolder) viewHolder).mi_address.getRightText().setText(list.get(position).getLxradress());
            ((ImageViewHolder) viewHolder).mi_serviced.getRightText().setText(list.get(position).getLxradress());
            if (list.get(position).getDetail() != null && list.get(position).getDetail().size() > 0) {
                LinearLayoutManager manager = new LinearLayoutManager(context);
                ((ImageViewHolder) viewHolder).rv_list.setLayoutManager(manager);
                OrderGoodsItemAdapter goodsItemAdapter;
                goodsItemAdapter = new OrderGoodsItemAdapter(context);
                ((ImageViewHolder) viewHolder).rv_list.setAdapter(goodsItemAdapter);
                goodsItemAdapter.addList(list.get(position).getDetail());
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.mi_contract)
        MenuItem mi_contract;
        @BindView(R.id.mi_tel)
        MenuItem mi_tel;
        @BindView(R.id.mi_address)
        MenuItem mi_address;
        @BindView(R.id.mi_serviced)
        MenuItem mi_serviced;
        @BindView(R.id.rv_list)
        RecyclerView rv_list;

        ImageViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

}
