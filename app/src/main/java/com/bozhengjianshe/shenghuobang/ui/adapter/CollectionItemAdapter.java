package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;
import com.bozhengjianshe.shenghuobang.utils.ImageLoadedrManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenzhiwei 2016/6/14.
 */
public class CollectionItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<GoodsListBean> list;
    private static Context context;
    private boolean isEditMode = false;
    private final LayoutInflater mLayoutInflater;
    private CommonOnClickListerner onClickListerner;

    public boolean isEditMode() {
        return isEditMode;
    }

    public CommonOnClickListerner getOnClickListerner() {
        return onClickListerner;
    }

    public void setOnClickListerner(CommonOnClickListerner onClickListerner) {
        this.onClickListerner = onClickListerner;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
        notifyDataSetChanged();
    }

    public CollectionItemAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public CollectionItemAdapter(Context context, List<GoodsListBean> items) {
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(items);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void remove(GoodsListBean bean) {
        this.list.remove(bean);
        notifyDataSetChanged();
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
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.adapter_collection_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (list != null) {
            try {
                ImageLoadedrManager.getInstance().display(context, list.get(position).getThumbnail(), ((ImageViewHolder) viewHolder).iv_pic);
            } catch (Exception e) {

            }
            if (!TextUtils.isEmpty(list.get(position).getCname())) {

                ((ImageViewHolder) viewHolder).tv_goods_name.setText(list.get(position).getCname());
                ((ImageViewHolder) viewHolder).tv_goods_name.setVisibility(View.VISIBLE);
            } else {
                ((ImageViewHolder) viewHolder).tv_goods_name.setVisibility(View.GONE);
            }
            ((ImageViewHolder) viewHolder).tv_price.setText(list.get(position).getFee()+"");
            if (isEditMode) {
                ((ImageViewHolder) viewHolder).iv_state.setImageResource(R.mipmap.ic_address_delect);
            } else {
                ((ImageViewHolder) viewHolder).iv_state.setImageResource(R.mipmap.ic_arrow_right);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView iv_pic;
        @BindView(R.id.iv_state)
        ImageView iv_state;
        @BindView(R.id.tv_goods_name)
        TextView tv_goods_name;
        @BindView(R.id.tv_price)
        TextView tv_price;

        ImageViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListerner != null) {
                        onClickListerner.myOnClick(list.get(getPosition()));
                    }
                }
            });
        }
    }

}
