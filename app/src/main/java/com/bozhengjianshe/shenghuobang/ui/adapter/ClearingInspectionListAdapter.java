package com.bozhengjianshe.shenghuobang.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.ui.bean.ClearingInspectionBean;
import com.bozhengjianshe.shenghuobang.ui.listerner.CommonOnClickListerner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenzhiwei 2016/6/14.
 */
public class ClearingInspectionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static List<ClearingInspectionBean> list;
    private static Context context;
    private String type;
    private int selectedPosition;
    private CommonOnClickListerner onClickListerner;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
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


    public ClearingInspectionListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public ClearingInspectionListAdapter(Context context, List<ClearingInspectionBean> items) {
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(items);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<ClearingInspectionBean> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    public void ClearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public static List<ClearingInspectionBean> getEntities() {
        return list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mLayoutInflater.inflate(R.layout.item_clearing_inspection, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (list != null) {
//            if (selectedPosition != position) {
//                ((ImageViewHolder) viewHolder).tv_title.setTextColor(context.getResources().getColor(R.color.color_999999));
//                ((ImageViewHolder) viewHolder).vv_divider.setBackgroundColor(context.getResources().getColor(R.color.color_ffffff));
//            } else {
//                ((ImageViewHolder) viewHolder).tv_title.setTextColor(context.getResources().getColor(R.color.color_000000));
//                ((ImageViewHolder) viewHolder).vv_divider.setBackgroundColor(context.getResources().getColor(R.color.color_000000));
//            }
//            ((ImageViewHolder) viewHolder).tv_title.setText(list.get(position).getText() + "");
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.vv_divider)
        View vv_divider;

        ImageViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedPosition = getPosition();
                    if (onClickListerner != null) {
                        onClickListerner.myOnClick(list.get(selectedPosition));
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

}
