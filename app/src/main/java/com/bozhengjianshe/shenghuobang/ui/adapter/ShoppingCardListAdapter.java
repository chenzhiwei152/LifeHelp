package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;
import com.bozhengjianshe.shenghuobang.utils.ImageLoadedrManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenzhiwei 2016/6/14.
 */
public class ShoppingCardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<GoodsListBean> list;
    private static Context context;
    private String type;
    private boolean isAllChecked;
    private boolean isEditMode = false;
    private CommonOnClickListerner onClickListerner;
    private CommonOnClickListerner onDeleteListerner;
    private int count = 1;
    private int mxCount = 200;

    public void setOnDeleteListerner(CommonOnClickListerner onDeleteListerner) {
        this.onDeleteListerner = onDeleteListerner;
    }

    public boolean isAllChecked() {
        return isAllChecked;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public void removeItem(GoodsListBean bean) {
        this.list.remove(bean);
        notifyDataSetChanged();
    }

    public void setAllChecked(boolean allChecked) {
        isAllChecked = allChecked;
    }

    public CommonOnClickListerner getOnClickListerner() {
        return onClickListerner;
    }

    public void setOnClickListerner(CommonOnClickListerner onClickListerner) {
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

    public ShoppingCardListAdapter(Context context, List<GoodsListBean> items) {
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(items);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<GoodsListBean> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    public List<GoodsListBean> getList() {
        return list;
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
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.fragment_shopping_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (list != null) {

            ((ImageViewHolder) viewHolder).tv_number.setText(list.get(position).getNum() + "");
            if (list.get(position).getLb() == 1) {

                ((ImageViewHolder) viewHolder).tv_price.setText(list.get(position).getFee() + "");
            } else {
                ((ImageViewHolder) viewHolder).tv_price.setText((list.get(position).getCost()
                        + list.get(position).getProfit()) + "");

            }
            ((ImageViewHolder) viewHolder).tv_goods_name.setText(list.get(position).getCname());
            if (isEditMode) {
                ((ImageViewHolder) viewHolder).rb_check.setVisibility(View.GONE);
                ((ImageViewHolder) viewHolder).iv_delete.setVisibility(View.VISIBLE);
            } else {
                ((ImageViewHolder) viewHolder).iv_delete.setVisibility(View.GONE);
                ((ImageViewHolder) viewHolder).rb_check.setVisibility(View.VISIBLE);
            }
            ((ImageViewHolder) viewHolder).rb_check.setChecked(list.get(position).isChecked());
            ((ImageViewHolder) viewHolder).rb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list.get(position).setChecked(b);
                    EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.UPDA_CARD_GOODS_SELECTED));
                }
            });
            //删除
            ((ImageViewHolder) viewHolder).iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeleteListerner != null) {
                        onDeleteListerner.myOnClick(list.get(position));
                    }
                }
            });
            ((ImageViewHolder) viewHolder).iv_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count = Integer.valueOf(((ImageViewHolder) viewHolder).tv_number.getText().toString());
                    if (count == 1) {
                        ((ImageViewHolder) viewHolder).iv_minus.setEnabled(false);
                        return;

                    }
                    ((ImageViewHolder) viewHolder).iv_add.setEnabled(true);

                    if (count > 1) {

                        if (count == 2) {
                            ((ImageViewHolder) viewHolder).iv_minus.setEnabled(false);
                        }
                        count--;
                        ((ImageViewHolder) viewHolder).tv_number.setText(count + "");
                        list.get(position).setNum(count);
                        EventBus.getDefault().post(new EventBusCenter<>(Constants.REDUCE_NUMBER_CARD,list.get(position).getId()+""));
                    }
                }
            });
            ((ImageViewHolder) viewHolder).iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ImageViewHolder) viewHolder).iv_minus.setEnabled(true);
                    count = Integer.valueOf(((ImageViewHolder) viewHolder).tv_number.getText().toString());
                    if (count < mxCount) {

                        if (count == mxCount - 1) {
                            ((ImageViewHolder) viewHolder).iv_add.setEnabled(false);
                        }
                        count++;
                        ((ImageViewHolder) viewHolder).tv_number.setText(count + "");
                        list.get(position).setNum(count);
                        EventBus.getDefault().post(new EventBusCenter<>(Constants.ADD_NUMBER_CARD,list.get(position).getId()+""));
                    }


                }
            });
            ImageLoadedrManager.getInstance().display(context, list.get(position).getThumbnail(), ((ImageViewHolder) viewHolder).iv_pic);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.iv_add)
        ImageView iv_add;
        @BindView(R.id.iv_minus)
        ImageView iv_minus;

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
