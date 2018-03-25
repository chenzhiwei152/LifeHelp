package com.bozhengjianshe.shenghuobang.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;


/**
 * Created by zhao.wenchao on 2017/11/13.
 * email: zhao.wenchao@jyall.com
 * introduce:
 */

public class MenuItem extends FrameLayout {
    private TextView leftText;
    private TextView rightText;
    private ImageView rightEnter;
    private ImageView rightImage;
    private Context mContext;
    private RelativeLayout rl_content;

    public MenuItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        inflateView(context);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MenuItem);
//        setMaxHeight(array.getDimension(R.styleable.MenuItem_autoHeight, -25f));
        setLeftText(array.getString(R.styleable.MenuItem_leftText));
        setLeftTextColor(array.getResourceId(R.styleable.MenuItem_leftTextColor, -1));
        setLeftTextSize(array.getDimension(R.styleable.MenuItem_leftTextSize, -1f));
        setRightText(array.getString(R.styleable.MenuItem_rightText));
        setRightTextColor(array.getResourceId(R.styleable.MenuItem_rightTextColor, -1));
        setRightTextGravity(array.getResourceId(R.styleable.MenuItem_rightTextGravity, -1));
        setRightTextSize(array.getDimension(R.styleable.MenuItem_rightTextSize, -1f));
        setRightTextHint(array.getString(R.styleable.MenuItem_rightHintText));
        int rImage = array.getResourceId(R.styleable.MenuItem_rightImage, -1);
        if (-1 != rImage) {
            setRightImage(rImage);
        }
        int eImage = array.getResourceId(R.styleable.MenuItem_enterImage, -1);
        if (-1 != eImage) {
            setRightEnter(eImage);
        }
        int leftImage = array.getResourceId(R.styleable.MenuItem_leftTextDrawableImage, -1);
        if (-1 != leftImage) {
            setLeftTextDrawableImage(leftImage);
        }
    }

    private void inflateView(Context context) {
        inflate(context, R.layout.menu_item, this);
        leftText = findViewById(R.id.left_text);
        rightText = findViewById(R.id.right_text);
        rightEnter = findViewById(R.id.enter);
        rightImage = findViewById(R.id.right_image);
        rl_content = findViewById(R.id.rl_content);
    }

    public TextView getLeftText() {
        return leftText;
    }

    public void setLeftText(String text) {
        leftText.setText(text);
    }

    public void setLeftTextColor(int text) {
        if (-1 != text) {

            leftText.setTextColor(text);
        }
    }

    public void setMaxHeight(float height) {
        if (25 != height) {
            ViewGroup.LayoutParams layoutParams = rl_content.getLayoutParams();
            layoutParams.height = UIUtil.dip2px(mContext, height);
            rl_content.setLayoutParams(layoutParams);

        }
    }

    public void setLeftTextSize(float text) {
        if (-1 != text) {
            leftText.setTextSize(UIUtil.sp2px(mContext, text));
        }
    }

    public void setLeftTextDrawableImage(int image) {
        leftText.setCompoundDrawablesWithIntrinsicBounds(image, 0, 0, 0);
    }

    public TextView getRightText() {
        return rightText;
    }

    public void setRightText(String text) {
        rightText.setText(text);
    }

    public void setRightTextColor(int color) {
        if (-1 != color) {
            rightText.setTextColor(color);
        }
    }
    public void setRightTextGravity(int gravity) {
        if (-1 != gravity) {
            rightText.setGravity(gravity);
        }
    }

    public void setRightTextSize(float color) {
        if (-1 != color) {
            rightText.setTextSize(UIUtil.sp2px(mContext, color));
        }
    }

    public void setRightTextHint(String text) {
        rightText.setHint(text);
    }

    public ImageView getRightImage() {
        return rightImage;
    }

    /**
     * @param res 本地资源 网络资源 请 使用imageloader  getRightImage获取控件
     */
    public void setRightImage(int res) {
        rightImage.setImageResource(res);
    }

    public ImageView getRightEnter() {
        return rightEnter;
    }

    public void setRightEnter(int res) {
        rightEnter.setImageResource(res);
    }

}
