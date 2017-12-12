package com.bozhengjianshe.shenghuobang.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;

import java.util.regex.Pattern;

/**
 * Created by zhao.wenchao on 2017/11/13.
 * email: zhao.wenchao@jyall.com
 * introduce:
 */

public class MenuItemEdittext extends FrameLayout {
    private TextView leftText;
    private CleanableEditText edit;

    public MenuItemEdittext(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuItemEdittext(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(context);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MenuItemEdittext);
        setLeftText(array.getString(R.styleable.MenuItemEdittext_leftText1));
        setEditTextHint(array.getString(R.styleable.MenuItemEdittext_editTextHint));
        setEditText(array.getString(R.styleable.MenuItemEdittext_editText));
        setEditTextMaxLength(array.getInt(R.styleable.MenuItemEdittext_editTextMaxLength, 50));
    }

    private void inflateView(Context context) {
        inflate(context, R.layout.menu_item_edittext, this);
        leftText = findViewById(R.id.left_text);
        edit = findViewById(R.id.edit);
    }

    public TextView getLeftText() {
        return leftText;
    }

    public void setLeftText(String text) {
        leftText.setText(text);
    }

    public void setEditText(String text) {
        edit.setText(text);
    }

    public void setEditTextMaxLength(int num) {
        edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)}); //即限定最大输入字符数
    }

    public CleanableEditText getEditText() {
        return edit;
    }

    public void setEditTextHint(String text) {
        edit.setHint(text);
    }

    public void setFilteres(InputFilter[] filters) {
        edit.setFilters(filters);
    }

    public void setFiltereRuler(final String filter) {
        edit.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                        if (!TextUtils.isEmpty(filter) && !TextUtils.isEmpty(charSequence)) {
//                            String regex = "^[\u4E00-\u9FA5]+$";
                            boolean isChinese = Pattern.matches(filter, charSequence.toString());
                            if (!Character.isLetterOrDigit(charSequence.charAt(i)) || isChinese) {
                                return "";
                            }
                        }
                        return null;
                    }
                }
        });
    }
    public void setFiltereRuler(final String filter, int maxLength) {
        edit.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                        if (!TextUtils.isEmpty(filter) && !TextUtils.isEmpty(charSequence)) {
//                            String regex = "^[\u4E00-\u9FA5]+$";
                            boolean isChinese = Pattern.matches(filter, charSequence.toString());
                            if (!Character.isLetterOrDigit(charSequence.charAt(i)) || isChinese) {
                                return "";
                            }
                        }
                        return null;
                    }
                }
        ,new InputFilter.LengthFilter(maxLength)});
    }
}
