package com.yuanchangyuan.lifehelp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yuanchangyuan.lifehelp.R;
import com.yuanchangyuan.lifehelp.api.JyCallBack;
import com.yuanchangyuan.lifehelp.api.RestAdapterManager;
import com.yuanchangyuan.lifehelp.base.BaseActivity;
import com.yuanchangyuan.lifehelp.base.BaseContext;
import com.yuanchangyuan.lifehelp.base.Constants;
import com.yuanchangyuan.lifehelp.base.EventBusCenter;
import com.yuanchangyuan.lifehelp.ui.adapter.MemberRankItemAdapter;
import com.yuanchangyuan.lifehelp.ui.bean.MemberRankBean;
import com.yuanchangyuan.lifehelp.ui.bean.SuperBean;
import com.yuanchangyuan.lifehelp.utils.DialogUtils;
import com.yuanchangyuan.lifehelp.utils.UIUtil;
import com.yuanchangyuan.lifehelp.view.TitleBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-22.
 */

public class MyMemberRankActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.tv_payed_deposit)
    TextView tv_payed_deposit;
    @BindView(R.id.tv_commit_time)
    TextView tv_commit_time;
    @BindView(R.id.tv_effective_time)
    TextView tv_effective_time;
    @BindView(R.id.rb_rank)
    RatingBar rb_rank;
    @BindView(R.id.bt_go_commit)
    Button bt_go_commit;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    private Call<SuperBean<List<MemberRankBean>>> call;
    private MemberRankItemAdapter adapter;
    private List<MemberRankBean> list = new ArrayList<>();

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_member_rank;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();

        rv_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MemberRankItemAdapter(this);
        rv_list.setAdapter(adapter);
        rv_list.setNestedScrollingEnabled(false);


        bt_go_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list != null && list.size() > 0) {
                    Intent intent = new Intent(MyMemberRankActivity.this, PayDepositActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("RankList", (Serializable) list);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void loadData() {
        getMenberRankInfo();
        if (BaseContext.getInstance().getUserInfo() != null) {

            tv_payed_deposit.setText("￥" + BaseContext.getInstance().getUserInfo().deposit / 100.00);
            if (BaseContext.getInstance().getUserInfo().vipgrade > 0) {
                rb_rank.setNumStars(BaseContext.getInstance().getUserInfo().vipgrade);
                rb_rank.setVisibility(View.VISIBLE);
            } else {
                rb_rank.setVisibility(View.GONE);
            }
            tv_commit_time.setText(BaseContext.getInstance().getUserInfo().paytime);
            tv_effective_time.setText(BaseContext.getInstance().getUserInfo().endtime);
        }
    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (eventBusCenter != null) {
            if (eventBusCenter.getEvenCode() == Constants.PAY_MEMBER_SUCCESS) {
//                loadData();
            }
        }
    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    private void getMenberRankInfo() {
        DialogUtils.showDialog(MyMemberRankActivity.this, "加载中", false);
        call = RestAdapterManager.getApi().getMemberRank();
        call.enqueue(new JyCallBack<SuperBean<List<MemberRankBean>>>() {
            @Override
            public void onSuccess(Call<SuperBean<List<MemberRankBean>>> call, Response<SuperBean<List<MemberRankBean>>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    adapter.ClearData();
                    adapter.addList(response.body().getData());
                    list.addAll(response.body().getData());
                } else {
                    try {
                        UIUtil.showToast(response.body().getMsg());
                    } catch (Exception e) {
                    }

                }
            }

            @Override
            public void onError(Call<SuperBean<List<MemberRankBean>>> call, Throwable t) {
                UIUtil.showToast("获取会员信息失败");
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<SuperBean<List<MemberRankBean>>> call, Response<SuperBean<List<MemberRankBean>>> response) {
                UIUtil.showToast("获取会员信息失败");
                DialogUtils.closeDialog();
            }
        });
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("我的会员");
        title_view.setTitleColor(Color.WHITE);
        title_view.setLeftImageResource(R.mipmap.ic_title_back);
        title_view.setLeftText("返回");
        title_view.setLeftTextColor(Color.WHITE);
        title_view.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_view.setBackgroundColor(getResources().getColor(R.color.color_ff6900));
        title_view.setImmersive(true);
    }


    @Override
    protected void onDestroy() {
        if (call != null) {
            call.cancel();
        }
        super.onDestroy();
    }
}
