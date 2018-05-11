package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;
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
    private CommonOnClickListerner onClickListerner;

    public CommonOnClickListerner getOnClickListerner() {
        return onClickListerner;
    }

    public void setOnClickListerner(CommonOnClickListerner onClickListerner) {
        this.onClickListerner = onClickListerner;
    }

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
            ((ImageViewHolder) viewHolder).mi_service_price.getRightText().setText("0.00");
            if (list.get(position).getState() == Constants.STATE_TWO) {
                ((ImageViewHolder) viewHolder).bt_accept.setText("接单");
                ((ImageViewHolder) viewHolder).bt_accept.setVisibility(View.VISIBLE);
                ((ImageViewHolder) viewHolder).bt_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListerner != null) {
                            onClickListerner.myOnClick(list.get(position));
                        }
                    }
                });
            }else if(list.get(position).getState() == Constants.STATE_THREE||list.get(position).getState() == Constants.STATE_FOUR){
                ((ImageViewHolder) viewHolder).bt_accept.setText("结束服务");
                ((ImageViewHolder) viewHolder).bt_accept.setVisibility(View.VISIBLE);
                ((ImageViewHolder) viewHolder).bt_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListerner != null) {
                            onClickListerner.myOnClick(list.get(position));
                        }
                    }
                });
            } else {
                ((ImageViewHolder) viewHolder).bt_accept.setVisibility(View.GONE);
                ((ImageViewHolder) viewHolder).bt_accept.setOnClickListener(null);
            }


            if (list.get(position).getDetail() != null && list.get(position).getDetail().size() > 0) {
                LinearLayoutManager manager = new LinearLayoutManager(context);
                ((ImageViewHolder) viewHolder).rv_list.setLayoutManager(manager);
                OrderGoodsItemAdapter goodsItemAdapter;
                goodsItemAdapter = new OrderGoodsItemAdapter(context);
                goodsItemAdapter.setClickAble(false);
                ((ImageViewHolder) viewHolder).rv_list.setAdapter(goodsItemAdapter);
                goodsItemAdapter.addList(list.get(position).getDetail());


                double price = 0.00;
                for (int i = 0; i < list.get(position).getDetail().size(); i++) {
                    price += list.get(position).getDetail().get(i).getZj();
                    ((ImageViewHolder) viewHolder).mi_service_price.getRightText().setText(price + "");
                }
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
        @BindView(R.id.mi_service_price)
        MenuItem mi_service_price;
        @BindView(R.id.rv_list)
        RecyclerView rv_list;
        @BindView(R.id.bt_accept)
        Button bt_accept;

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
