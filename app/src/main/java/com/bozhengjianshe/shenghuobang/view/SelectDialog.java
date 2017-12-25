package com.bozhengjianshe.shenghuobang.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;

import java.util.List;

/**
 * @author sun.luwei
 * @date 2017/11/17.
 * @email sun.luwei@jyall.com
 * Copyright is 金色家园网络科技有限公司
 */


public class SelectDialog extends AppCompatDialog implements View.OnClickListener {
    RecyclerView recyler;
    View view;
    private Activity context;
    List<String> data;
    boolean singalSelect;

    ResultListener resultListener;

    String title;

    TextView tvConfirm;
    StringAdapter adapter;

    public void setResultListener(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    public SelectDialog(Context context) {
        super(context);
    }

    public SelectDialog(Context context, int theme) {
        super(context, theme);
    }

    public SelectDialog(Activity context, String title, List<String> data, boolean singalSelect) {
        super(context);
        this.context = context;
        this.data = data;
        this.singalSelect = singalSelect;
        this.title = title;

        init();
    }

    private void init() {
        view = View.inflate(context, R.layout.dialog_select, null);
        recyler = view.findViewById(R.id.rv_data);
        TextView tvTitle = (TextView) view.findViewById(R.id.confirm_dialog_message);
        tvTitle.setText(title);

        tvConfirm = view.findViewById(R.id.confirm);
        tvConfirm.setOnClickListener(this);
        adapter = new StringAdapter(data, context, singalSelect);
        LinearLayoutManager lm = new LinearLayoutManager(context);
        recyler.setAdapter(adapter);
        recyler.setLayoutManager(lm);
        getWindow().setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = UIUtil.dip2px(context, 280);
        window.setAttributes(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (null != resultListener) {
                    resultListener.confirmClik(adapter.getStatus());

                    dismiss();
                }
                break;
        }
    }

    /**
     * 设置选中项
     *
     * @param select 单选时只传一个数
     */
    public void setSelect(int select) {
        for (int i = 0; i < adapter.status.length; i++) {
            adapter.status[i] = false;
        }
        if(select>=0) {
            adapter.status[select] = true;
            recyler.smoothScrollToPosition(select);
        }
    }

    public void setSelect2(int[] selects) {
        for (int i = 0; i < adapter.status.length; i++) {
            adapter.status[i] = false;
        }
        for (int select : selects) {
            adapter.status[select] = true;
        }
    }

    public void setSelect3(List<Integer> selects) {
        for (int i = 0; i < adapter.status.length; i++) {
            adapter.status[i] = false;
        }
        for (int select : selects) {
            adapter.status[select] = true;
        }
    }

    /**
     * @param isReset 是否刷新
     */
    public void show(boolean isReset) {
        super.show();
        if (isReset) {
            adapter.notifyDataSetChanged();
        }
    }

    class StringAdapter extends RecyclerView.Adapter<StringAdapter.TextViewHolder> {

        List<String> data;
        boolean[] status;

        public boolean[] getStatus() {
            return status;
        }

        Context context;
        boolean singalSelect;

        public StringAdapter(List<String> data, Context context, boolean singalSelect) {
            this.data = data;
            this.singalSelect = singalSelect;
            status = new boolean[data.size()];
            for (int i = 0; i < data.size(); i++) {
                status[i] = false;
            }
            this.context = context;
        }

        @Override
        public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextViewHolder viewHolder = new TextViewHolder(View.inflate(context, R.layout.item_select_dialog, null));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final TextViewHolder holder, final int position) {

            holder.checkBox.setText(data.get(position));
            holder.checkBox.setChecked(status[position]);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!holder.checkBox.isChecked()) {
                        status[position] = false;
                    } else {
                        if (singalSelect) {
                            for (int i = 0; i < data.size(); i++) {
                                status[i] = false;
                            }
                        }
                        status[position] = true;

                    }
                    notifyDataSetChanged();
                }
            });

        }


        class TextViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;

            public TextViewHolder(View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.checkbox);

            }
        }

        @Override
        public int getItemCount() {
            return null == data ? 0 : data.size();
        }
    }

    public interface ResultListener {
        void confirmClik(boolean[] status);
    }
}
