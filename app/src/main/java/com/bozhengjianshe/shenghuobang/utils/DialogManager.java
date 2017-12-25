package com.bozhengjianshe.shenghuobang.utils;

import android.content.Context;
import android.text.TextUtils;

import com.bozhengjianshe.shenghuobang.utils.timepicker.TimePickerView;
import com.bozhengjianshe.shenghuobang.view.BottomSheetListDialog;
import com.bozhengjianshe.shenghuobang.view.ConfirmDialog;

import java.util.Date;

/**
 * Created by sun.luwei on 2017/11/10.
 */

public class DialogManager {
    private static volatile DialogManager instance;

    private DialogManager() {
    }

    public static DialogManager getInstance() {
        if (null == instance) {
            synchronized (DialogManager.class) {
                if (null == instance) {
                    instance = new DialogManager();
                }
            }
        }
        return instance;
    }


    /**
     * 确认弹框
     *
     * @param context
     * @param message
     * @return
     */
    public ConfirmDialog creatConfirmDialog(Context context, String message) {
        ConfirmDialog dialog = new ConfirmDialog(context, message);
        return dialog;
    }



    /**
     * @param context
     * @param resId           列表item资源id
     * @param values          填充数组
     * @param bottomItemClick item点击回调
     *                        example : String[] array={"购买商品类","预约商品类","取消"};
     *                        DialogManager.getInstance().creatBottomSheetListDialog(getContext(), R.layout.dialog_bottom_list_item, array, new BottomSheetListDialog.BottomItemClick() {
     * @return
     * @Override public void itemClick(int poi) {}}).show();
     */
    public BottomSheetListDialog creatBottomSheetListDialog(Context context, int resId, String[] values, BottomSheetListDialog.BottomItemClick bottomItemClick) {
        BottomSheetListDialog dialog = new BottomSheetListDialog(context, resId, values, bottomItemClick);
        return dialog;
    }

    /**
     * 时间选择器
     *
     * @param context
     * @param listener
     */
    public void showTimePcikerDialog(final Context context, String title, final TimePickerView.OnTimeSelectListener listener) {
        showTimePcikerDialog(context, null, title, listener);
    }

    /**
     * 时间选择器
     *
     * @param context
     * @param listener
     * @param startYearFromNow 是否从当前年份开始显示
     */
    public void showTimePcikerDialog(final Context context, String title, boolean startYearFromNow, final TimePickerView.OnTimeSelectListener listener) {
        showTimePcikerDialog(context, null, title, startYearFromNow, listener);
    }

    /**
     *
     * @param context
     * @param type 显示类型
     * @param title
     * @param listener
     */
    public void showTimePcikerDialog(final Context context, TimePickerView.Type type, String title, final TimePickerView.OnTimeSelectListener listener) {
        showTimePcikerDialog(context, type, title, false, listener);
    }

    public void showTimePcikerDialog(final Context context, TimePickerView.Type type, String title, boolean startYearFromNow, final TimePickerView.OnTimeSelectListener listener) {
        //时间选择器
        if (type == null) {
            type = TimePickerView.Type.YEAR_MONTH_DAY;
        }
        final TimePickerView pvTime = new TimePickerView(context, type);
        pvTime.setRange(Integer.parseInt(UIUtil.timeStamp2Date(new Date(), "yyyy")), Integer.parseInt(UIUtil.timeStamp2Date(new Date(), "yyyy")) + 50);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        if (TextUtils.isEmpty(title)) {
            title = "请选择时间";
        }
        pvTime.setTitle(title);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                if (listener != null) {
                    listener.onTimeSelect(date);
                    pvTime.dismiss();
                }
            }
        });
        pvTime.show();
    }

    /**
     * 选择弹框
     *
     * @param context
     * @param title
     * @param data
     * @param singalSelect true 单选  false 多选
     * @return
     */
//    public SelectDialog creatSelectDialog(Activity context, String title, List<String> data, boolean singalSelect) {
//        SelectDialog da = new SelectDialog(context, title, data, singalSelect);
//        return da;
//    }
}
