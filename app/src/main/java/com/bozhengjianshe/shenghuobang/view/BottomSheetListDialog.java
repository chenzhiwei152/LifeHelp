package com.bozhengjianshe.shenghuobang.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;


/**
 * 底部弹窗
 */

public class BottomSheetListDialog extends Dialog {
    View view;
    LinearLayout mContainer;
    BottomItemClick bottomItemClick;
    public BottomSheetListDialog(Context context, int resId, String[] values, BottomItemClick bottomItemClick) {
        super(context, R.style.customDialogStyle);
        this.bottomItemClick=bottomItemClick;
        init(context, resId, values);
    }

    private void init(Context context, int resId, String[] values) {
        view = View.inflate(context, R.layout.dialog_bottom_sheet, null);
        mContainer = view.findViewById(R.id.content);
        addItemView(context, resId, values);
        getWindow().setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setWindowAnimations(R.style.bottom_in_out_alpha_stay);
        window.setAttributes(wlp);

    }

    public void addItemView(Context context, int resId, String[] values) {
        for (int i = 0; i < values.length; i++) {
            final View itemView = View.inflate(context, resId, null);
            mContainer.addView(itemView);
            TextView value = itemView.findViewById(R.id.value);
            View line = itemView.findViewById(R.id.line);
            value.setText(values[i]);
            itemView.setTag(i);
            if(i == values.length-1){
                line.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(null != bottomItemClick){
                        bottomItemClick.itemClick((int)itemView.getTag());
                    }
                    dismiss();
                }
            });
        }
    }


    /**
     * 点击位置poi 回调
     */
    public interface BottomItemClick {
        void itemClick(int poi);
    }

}
