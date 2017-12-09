package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.ui.bean.CardListItemBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.ShoppingAddressItemOnClickListerner;
import com.bozhengjianshe.shenghuobang.utils.ImageLoadedrManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenzhiwei 2016/6/14.
 */
public class ShoppingCardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<CardListItemBean> list;
    private static Context context;
    private String type;
    private ShoppingAddressItemOnClickListerner onClickListerner;

//    public ShoppingAddressItemDeleteListerner getOnDeleteListerner() {
//        return onDeleteListerner;
//    }
//
//    public void setOnDeleteListerner(ShoppingAddressItemDeleteListerner onDeleteListerner) {
//        this.onDeleteListerner = onDeleteListerner;
//    }
//
//    private ShoppingAddressItemDeleteListerner onDeleteListerner;

    public ShoppingAddressItemOnClickListerner getOnClickListerner() {
        return onClickListerner;
    }

    public void setOnClickListerner(ShoppingAddressItemOnClickListerner onClickListerner) {
        this.onClickListerner = onClickListerner;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private final LayoutInflater mLayoutInflater;


    public ShoppingCardListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public ShoppingCardListAdapter(Context context, List<CardListItemBean> items) {
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(items);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<CardListItemBean> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    public void ClearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public static List<CardListItemBean> getEntities() {
        return list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.fragment_shopping_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (list != null) {

            ((ImageViewHolder) viewHolder).tv_number.setText(list.get(position).getProductCount()+"");
            ((ImageViewHolder) viewHolder).tv_price.setText(list.get(position).getProductPrice()+"");
            ((ImageViewHolder) viewHolder).tv_goods_name.setText(list.get(position).getProductName());
            ImageLoadedrManager.getInstance().display(context, list.get(position).getProductImg(), ((ImageViewHolder) viewHolder).iv_pic);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_number)
        TextView tv_number;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_goods_name)
        TextView tv_goods_name;
        @BindView(R.id.rb_check)
        CheckBox rb_check;
        @BindView(R.id.iv_pic)
        ImageView iv_pic;

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
