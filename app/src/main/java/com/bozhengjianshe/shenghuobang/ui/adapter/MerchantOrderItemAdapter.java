package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;

import java.util.ArrayList;
import java.util.List;

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

//            ((ImageViewHolder) viewHolder).tv_rank1_title.setText(list.get(position).getName());
//            ((ImageViewHolder) viewHolder).tv_rank.setText(list.get(position).getMoney()/100.00+"");
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_rank1_title)
//        TextView tv_rank1_title;
//        @BindView(R.id.tv_rank)
//        TextView tv_rank;

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
