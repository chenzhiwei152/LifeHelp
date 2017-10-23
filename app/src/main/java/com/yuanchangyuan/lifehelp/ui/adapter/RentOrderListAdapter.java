package com.yuanchangyuan.lifehelp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanchangyuan.lifehelp.R;
import com.yuanchangyuan.lifehelp.ui.activity.OrderDetailsActivity;
import com.yuanchangyuan.lifehelp.ui.bean.BuyOrderListItemBean;
import com.yuanchangyuan.lifehelp.utils.ImageLoadedrManager;
import com.yuanchangyuan.lifehelp.utils.UIUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenzhiwei 2016/6/14.
 */
public class RentOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<BuyOrderListItemBean.DataBean> list;
    private static Context context;
    private boolean isLight;
    private final LayoutInflater mLayoutInflater;

    public RentOrderListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public RentOrderListAdapter(Context context, List<BuyOrderListItemBean.DataBean> items) {
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(items);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<BuyOrderListItemBean.DataBean> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    public void ClearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public static List<BuyOrderListItemBean.DataBean> getEntities() {
        return list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.fragment_item_rent_order_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (list != null) {
            ImageLoadedrManager.getInstance().display(context, list.get(position).getGoodsimg(), ((ImageViewHolder) viewHolder).iv_goods);
            ((ImageViewHolder) viewHolder).tv_store_name.setText(list.get(position).getShopName());
            ((ImageViewHolder) viewHolder).tv_goods_name.setText(list.get(position).getGoodsName());
            ((ImageViewHolder) viewHolder).tv_goods_price.setText(list.get(position).getPrice() / 100.00 + "");
            ((ImageViewHolder) viewHolder).tv_goods_number.setText("x" + list.get(position).getCount());
            ((ImageViewHolder) viewHolder).tv_translate_state.setText(list.get(position).getOrderStatus());

            ((ImageViewHolder) viewHolder).tv_receive_store_name.setText(list.get(position).getShopName());
            ((ImageViewHolder) viewHolder).tv_back_store_name.setText(list.get(position).getShopName());
            ((ImageViewHolder) viewHolder).tv_deposit.setText("押金：￥" + list.get(position).getDeposit() / 100.00 + "");
            if (!TextUtils.isEmpty(list.get(position).getStarttime())) {
                ((ImageViewHolder) viewHolder).tv_start_time.setText(UIUtil.timeStamp2Date(list.get(position).getStarttime(), "yyyy-MM-dd HH") + "时");
                ((ImageViewHolder) viewHolder).tv_start_week.setText(UIUtil.getWeekOfDate(new Date(Long.valueOf(list.get(position).getStarttime()))));
            }else {
                ((ImageViewHolder) viewHolder).tv_start_time.setText("");
                ((ImageViewHolder) viewHolder).tv_start_week.setText("");
            }
            if (!TextUtils.isEmpty(list.get(position).getEndtime())) {

                ((ImageViewHolder) viewHolder).tv_end_time.setText(UIUtil.timeStamp2Date(list.get(position).getEndtime(), "yyyy-MM-dd HH") + "时");
                ((ImageViewHolder) viewHolder).tv_end_week.setText(UIUtil.getWeekOfDate(new Date(Long.valueOf(list.get(position).getEndtime()))));
            }else {
                ((ImageViewHolder) viewHolder).tv_end_time.setText("");
                ((ImageViewHolder) viewHolder).tv_end_week.setText("");
            }
            ((ImageViewHolder) viewHolder).tv_rent_days.setText(list.get(position).getDays() + "天");


            ((ImageViewHolder) viewHolder).ll_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra("orderId", list.get(position).getId() + "");
                    intent.putExtra("type", "rent");
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
        @BindView(R.id.iv_goods)
        ImageView iv_goods;//商品图片
        @BindView(R.id.tv_store_name)
        TextView tv_store_name;//店铺名称
        @BindView(R.id.tv_goods_name)
        TextView tv_goods_name;//商品名称
        @BindView(R.id.tv_goods_price)
        TextView tv_goods_price;//商品价格
        @BindView(R.id.tv_goods_number)
        TextView tv_goods_number;//商品数量
        @BindView(R.id.ll_content)//全局
                LinearLayout ll_content;
        @BindView(R.id.tv_translate_state)//交易状态
                TextView tv_translate_state;
        @BindView(R.id.tv_receive_store_name)
        TextView tv_receive_store_name;
        @BindView(R.id.tv_back_store_name)
        TextView tv_back_store_name;
        @BindView(R.id.tv_deposit)//押金
                TextView tv_deposit;
        @BindView(R.id.tv_start_time)//开始时间
                TextView tv_start_time;
        @BindView(R.id.tv_end_time)//结束时间
                TextView tv_end_time;
        @BindView(R.id.tv_rent_days)//租赁时长
                TextView tv_rent_days;
        @BindView(R.id.tv_start_week)//开始星期
                TextView tv_start_week;
        @BindView(R.id.tv_end_week)//结束星期
                TextView tv_end_week;

        ImageViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int[] startingLocation = new int[2];
//                    v.getLocationOnScreen(startingLocation);
//                    startingLocation[0] += v.getWidth() / 2;
//                    StoriesBean storiesEntity = new StoriesBean();
//                    storiesEntity.setId(MainNewsItemAdapter.getEntities().get(getPosition()).getId());
//                    storiesEntity.setTitle(MainNewsItemAdapter.getEntities().get(getPosition()).getTitle());
//                    Bundle bundle = new Bundle();
//                    bundle.putIntArray(Constant.START_LOCATION, startingLocation);
//                    bundle.putSerializable("entity",storiesEntity);
//                    bundle.putBoolean("isLight", ((MainActivity) context).isLight());
//                    LogUtils.e("isLight:"+((MainActivity) context).isLight());
//                    AppContext.getInstance().intentJump((Activity) MainNewsItemAdapter.context,LatestContentActivity.class,bundle);
                }
            });
        }
    }

}
