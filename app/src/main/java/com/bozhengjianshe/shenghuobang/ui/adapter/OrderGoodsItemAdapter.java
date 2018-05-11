package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.ui.activity.GoodsDetailsActivity;
import com.bozhengjianshe.shenghuobang.ui.bean.OrderDetailBean;
import com.bozhengjianshe.shenghuobang.utils.ImageLoadedrManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenzhiwei 2016/6/14.
 */
public class OrderGoodsItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<OrderDetailBean.DetailBean> list;
    private static Context context;
    private boolean isLight;
    private final LayoutInflater mLayoutInflater;
    private boolean clickAble = true;

    public boolean isClickAble() {
        return clickAble;
    }

    public void setClickAble(boolean clickAble) {
        this.clickAble = clickAble;
    }

    public OrderGoodsItemAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public OrderGoodsItemAdapter(Context context, List<OrderDetailBean.DetailBean> items) {
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(items);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<OrderDetailBean.DetailBean> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    public void ClearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public static List<OrderDetailBean.DetailBean> getEntities() {
        return list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.order_goods_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (list != null) {
            ImageLoadedrManager.getInstance().display(context, list.get(position).getThumbnail(), ((ImageViewHolder) viewHolder).iv_goods);
            ((ImageViewHolder) viewHolder).tv_count.setText("x" + list.get(position).getNum() + "");
            ((ImageViewHolder) viewHolder).tv_price.setText(list.get(position).getDj() + "");
            ((ImageViewHolder) viewHolder).tv_goods_name.setText(list.get(position).getName());
            if (clickAble) {

                ((ImageViewHolder) viewHolder).rl_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, GoodsDetailsActivity.class);
                        intent.putExtra("orderId", list.get(position).getId() + "");
                        context.startActivity(intent);
                    }
                });
                ((ImageViewHolder) viewHolder).iv_more.setVisibility(View.VISIBLE);
            } else {
                ((ImageViewHolder) viewHolder).rl_content.setOnClickListener(null);
                ((ImageViewHolder) viewHolder).iv_more.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_content)
        RelativeLayout rl_content;
        @BindView(R.id.tv_count)
        TextView tv_count;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_goods_name)
        TextView tv_goods_name;
        @BindView(R.id.iv_goods)
        ImageView iv_goods;
        @BindView(R.id.iv_more)
        ImageView iv_more;

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
