package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.activity.AboutActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.CollectionActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.LoginActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.MerchantOrderActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.OrderListActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.PersonInformationActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.QuestionNormalActivity;
import com.bozhengjianshe.shenghuobang.ui.activity.RegistersServiceActivity;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.ImageLoadedrManager;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-6-19.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_member)
    TextView tvMember;
    @BindView(R.id.tv_order)
    TextView tv_order;
    @BindView(R.id.iv_safe)
    ImageView ivSafe;
    @BindView(R.id.rl_safety)
    RelativeLayout rlSafety;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    //    @BindView(R.id.rl_location)
//    RelativeLayout rlLocation;
    @BindView(R.id.iv_mumber)
    ImageView ivMumber;
    @BindView(R.id.rl_member)
    RelativeLayout rlMember;
    @BindView(R.id.iv_real_name)
    ImageView ivRealName;
    @BindView(R.id.rl_order)
    RelativeLayout rl_order;
    @BindView(R.id.iv_abount)
    ImageView ivAbount;
    @BindView(R.id.tv_about)
    TextView tv_about;
    @BindView(R.id.tv_setting)
    TextView tv_setting;
    @BindView(R.id.rl_quit_login)
    RelativeLayout rl_quit_login;
    @BindView(R.id.rl_help)
    RelativeLayout rl_help;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.rb_rank)
    RatingBar rb_rank;
    @BindView(R.id.tv_info)
    TextView tv_info;
    @BindView(R.id.tv_collection)
    TextView tv_collection;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.rl_merchant_order)
    RelativeLayout rl_merchant_order;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    protected void initViewsAndEvents() {
        tv_order.setOnClickListener(this);
        rlSafety.setOnClickListener(this);
        rl_order.setOnClickListener(this);
        rlMember.setOnClickListener(this);
        tv_about.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        rl_quit_login.setOnClickListener(this);
        tv_info.setOnClickListener(this);
        rl_merchant_order.setOnClickListener(this);
        rl_merchant_order.setVisibility(View.GONE);
        tv_collection.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        initTitle();
    }

    @Override
    protected void loadData() {
        setUserInfo();
    }

    private void setUserInfo() {
        if (BaseContext.getInstance().getUserInfo() != null) {
            rl_quit_login.setVisibility(View.VISIBLE);
            tv_name.setText(BaseContext.getInstance().getUserInfo().name);
            ImageLoadedrManager.getInstance().displayCycle(getActivity(), BaseContext.getInstance().getUserInfo().head, ivHead);
        } else {
            rl_quit_login.setVisibility(View.GONE);
            tv_user_name.setText("点击登录");
            ivHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
//            ImageLoadedrManager.getInstance().display(getActivity(), "", ivHead, R.mipmap.ic_head_default, R.mipmap.ic_head_default);
            ivHead.setImageResource(R.mipmap.ic_head_default);
        }
    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (eventBusCenter != null) {
            if (eventBusCenter.getEvenCode() == Constants.LOGIN_SUCCESS || eventBusCenter.getEvenCode() == Constants.LOGIN_FAILURE) {
                //登陆成功,或者退出
                setUserInfo();
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order:
                //订单
                if (BaseContext.getInstance().getUserInfo() == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), OrderListActivity.class));
                break;
            case R.id.rl_merchant_order:
                //订单
                if (BaseContext.getInstance().getUserInfo() == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MerchantOrderActivity.class));
                break;
            case R.id.rl_safety:
                //常见问题
                if (BaseContext.getInstance().getUserInfo() == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), QuestionNormalActivity.class));
//                startActivity(new Intent(getActivity(), EaseBaiduMapActivity.class));
                break;
            case R.id.rl_order:
//                //加入我们
                startActivity(new Intent(getActivity(), RegistersServiceActivity.class));
                break;
            case R.id.tv_collection:
                //收藏
                if (BaseContext.getInstance().getUserInfo() == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), CollectionActivity.class));
                break;
//            case R.id.rl_real_name:
//                //实名制
//                if (BaseContext.getInstance().getUserInfo() == null) {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                    return;
//                }
//                startActivity(new Intent(getActivity(), CommitRealNameActivity.class));
//                break;
            case R.id.tv_about:
                //关于
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.tv_info:
                //设置
                if (BaseContext.getInstance().getUserInfo() == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), PersonInformationActivity.class));
                break;
            case R.id.rl_quit_login:
                //退出登录
                if (BaseContext.getInstance().getUserInfo() == null) {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                showExitDialog();
                break;
            case R.id.rl_help:
                //客服
                UIUtil.call(getActivity(),"10086");
                break;
        }
    }

    private void showExitDialog() {
        DialogUtils.showOrderCancelMsg(getActivity(), "确定要退出登录吗？", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag().equals("确定")) {
                    BaseContext.getInstance().Exit();
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                    EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOGIN_FAILURE));
                }

            }

//            @Override
//            public void callBack() {//退出登录
//
//            }
        });
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle(R.string.app_name);
    }
}
