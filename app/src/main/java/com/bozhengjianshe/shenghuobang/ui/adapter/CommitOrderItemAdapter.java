package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.utils.ImageLoadedrManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenzhiwei 2016/6/14.
 */
public class CommitOrderItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<GoodsListBean> list;
    private static Context context;
    private boolean isLight;
    private final LayoutInflater mLayoutInflater;

    public CommitOrderItemAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public CommitOrderItemAdapter(Context context, List<GoodsListBean> items) {
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(items);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<GoodsListBean> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    public void ClearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public static List<GoodsListBean> getEntities() {
        return list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.activity_commit_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (list != null) {
            ((ImageViewHolder) viewHolder).tv_title.setText(list.get(position).getCname());
            ((ImageViewHolder) viewHolder).tv_price.setText("￥" + list.get(position).getProfit() + "");


            ((ImageViewHolder) viewHolder).tv_all_price.setText("￥" + list.get(position).getProfit()*list.get(position).getNum() + "");
            if (list.get(position).getLb() == 1) {
                // 1 商品 2 服务
                ((ImageViewHolder) viewHolder).tv_goods_type.setText("商品");
                ((ImageViewHolder) viewHolder).tv_number.setText("共"+list.get(position).getNum()+"件商品");
            } else {
                ((ImageViewHolder) viewHolder).tv_goods_type.setText("服务");
                ((ImageViewHolder) viewHolder).tv_number.setText("服务预约款");
            }


            ImageLoadedrManager.getInstance().display(context, list.get(position).getThumbnail(), ((ImageViewHolder) viewHolder).iv_goods);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_number_per)
        TextView tv_number_per;
        @BindView(R.id.tv_number)
        TextView tv_number;
        @BindView(R.id.tv_all_price)
        TextView tv_all_price;
        @BindView(R.id.tv_goods_type)
        TextView tv_goods_type;
        @BindView(R.id.iv_goods)
        ImageView iv_goods;

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
