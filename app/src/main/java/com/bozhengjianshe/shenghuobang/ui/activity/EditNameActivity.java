package com.bozhengjianshe.shenghuobang.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-11-21.
 */

public class EditNameActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.et_name)
    EditText et_name;
    private Call<SuperBean<String>> upLoadInfoCall;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle("修改用户名");
    }

    @Override
    public void loadData() {

    }

    @Override
    public boolean isRegistEventBus() {
        return false;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {

    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    /**
     * 初始化标题
     */
    private void initTitle(String title) {
        title_view.setLeftImageResource(R.mipmap.ic_title_back);
        title_view.setTitle(title);
//        title_view.setLeftText("返回");
//        title_view.setLeftTextColor(Color.WHITE);
        title_view.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        title_view.setBackgroundColor(getResources().getColor(R.color.color_ff6900));
        title_view.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {
                if (!TextUtils.isEmpty(et_name.getText().toString())) {
                    upLoadInfo();
                } else {
                    UIUtil.showToast("用户名不能为空");
                }
            }
        });
        title_view.setImmersive(true);
    }

    private void upLoadInfo() {

        DialogUtils.showDialog(this, "上传中", false);
        Map<String, String> map = new HashMap<>();
        map.put("id", BaseContext.getInstance().getUserInfo().id);
        map.put("name", et_name.getText().toString());
        upLoadInfoCall = RestAdapterManager.getApi().updateName(map);
        upLoadInfoCall.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
                if (response.body().getCode() == Constants.successCode) {
                    EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOGIN_SUCCESS));
                    finish();
                }
                UIUtil.showToast(response.body().getMsg());
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (upLoadInfoCall != null) {
            upLoadInfoCall.cancel();
        }
        super.onDestroy();
    }
}
