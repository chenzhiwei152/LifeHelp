package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.ui.activity.OrderDetailsActivity;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenzhiwei 2016/6/14.
 */
public class RentOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<BuyOrderListItemBean> list;
    private static Context context;
    private boolean isLight;
    private final LayoutInflater mLayoutInflater;

    public RentOrderListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public RentOrderListAdapter(Context context, List<BuyOrderListItemBean> items) {
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
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.fragment_item_rent_order_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (list != null) {
//            ((ImageViewHolder) viewHolder).tv_state_title.setText(list.get(position).geto());
            ((ImageViewHolder) viewHolder).tv_state.setText(list.get(position).getOrderStateCn());
            ((ImageViewHolder) viewHolder).tv_service_address.setText(list.get(position).getReceiveAddress());
            ((ImageViewHolder) viewHolder).tv_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra("orderId",list.get(position).getId()+"");
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_state_title)
        TextView tv_state_title;
        @BindView(R.id.tv_state)
        TextView tv_state;
        @BindView(R.id.tv_service_address)
        TextView tv_service_address;
        @BindView(R.id.tv_detail)
        TextView tv_detail;

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
