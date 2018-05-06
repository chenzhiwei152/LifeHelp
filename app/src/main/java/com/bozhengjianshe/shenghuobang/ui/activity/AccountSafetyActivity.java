package com.bozhengjianshe.shenghuobang.ui.activity;

import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.bean.ErrorBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.TelephoneUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.CleanableEditText;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 账号安全
 * Created by chen.zhiwei on 2017-6-22.
 */

public class AccountSafetyActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.et_check_code)
    EditText et_check_code;
    @BindView(R.id.user_password)
    CleanableEditText user_password;
    @BindView(R.id.user_phone)
    CleanableEditText user_phone;
    @BindView(R.id.user_new_password)
    EditText user_new_password;
    @BindView(R.id.bt_commit)
    Button bt_commit;
    @BindView(R.id.tv_check_code)
    TextView tvGetCode;
    private Call<ErrorBean> call;
    private Call<SuperBean<String>> getCheckCodeCall;
    CountDownTimer timer;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_account_safety;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
    }

    @Override
    public void loadData() {
        et_check_code.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});


        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText("重新发送" + "(" + millisUntilFinished / 1000 + "s)");
                tvGetCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                tvGetCode.setText("重新发送");
                tvGetCode.setEnabled(true);
            }
        };
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    commitData();
                }
            }
        });
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


    @OnClick(R.id.tv_check_code)
    public void onGetCodeClick(View view) {

        String phoneNumber = BaseContext.getInstance().getUserInfo().phone;
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumber = user_phone.getText().toString();
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            UIUtil.showToast("请输入手机号");
            return;
        }
        if (!TelephoneUtils.isMobile(phoneNumber)) {
            UIUtil.showToast("手机号格式错误");
            return;
        }

        if (!NetUtil.isNetworkConnected(this)) {

            UIUtil.showToast("网络连接失败，请检查您的网络");
            return;
        }
        getCode(phoneNumber);
    }


    private void getCode(String phone) {
        timer.start();
        RequestBody formBody = new FormBody.Builder()
                .add("phone", phone)
                .build();
        getCheckCodeCall = RestAdapterManager.getApi().getCheckCodeForFPW(formBody);
        getCheckCodeCall.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                if (response != null && response.body().state == Constants.successCode) {
                    UIUtil.showToast(response.body().message);
                } else {
                    UIUtil.showToast("发送验证码失败");
                }
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {

            }
        });

    }

    private void commitData() {

        RequestBody formBody = new FormBody.Builder()
                .add("phone", BaseContext.getInstance().getUserInfo().phone)
                .add("password", user_password.getText().toString())
                .add("qrnpassword", user_password.getText().toString())
                .add("npassword", user_password.getText().toString())
                .add("id", BaseContext.getInstance().getUserInfo().id)
                .build();

        call = RestAdapterManager.getApi().accountSafety(formBody);
        call.enqueue(new JyCallBack<ErrorBean>() {
            @Override
            public void onSuccess(Call<ErrorBean> call, Response<ErrorBean> response) {
                if (response != null && response.body() != null) {
                    if (response.body().state == Constants.successCode) {
                        UIUtil.showToast(response.body().message);
                        finish();
                    } else {
                        UIUtil.showToast("修改密码失败~请稍后重试");
                    }
                } else {
                    UIUtil.showToast("修改密码失败~请稍后重试");
                }
            }

            @Override
            public void onError(Call<ErrorBean> call, Throwable t) {
                UIUtil.showToast("修改密码失败~请稍后重试");
            }

            @Override
            public void onError(Call<ErrorBean> call, Response<ErrorBean> response) {
                try {
                    ErrorMessageUtils.taostErrorMessage(AccountSafetyActivity.this, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean checkData() {
//        String phoneNumber = BaseContext.getInstance().getUserInfo().phone;
//        if (TextUtils.isEmpty(phoneNumber)) {
//            phoneNumber = user_phone.getText().toString();
//        }

        if (TextUtils.isEmpty(user_phone.getText())) {
            UIUtil.showToast("当前密码不能为空");
            return false;
        }
//        if (phoneNumber.trim().length() != 11) {
//            Toast.makeText(this, "请输入11位账号", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (!TelephoneUtils.isMobile(phoneNumber)) {
//            UIUtil.showToast("手机号格式错误");
//            return false;
//        }
//        if (TextUtils.isEmpty(et_check_code.getText())) {
//            UIUtil.showToast("验证码不能为空");
//            return false;
//        }
        if (TextUtils.isEmpty(user_password.getText())) {
            UIUtil.showToast("新密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(user_new_password.getText())) {
            UIUtil.showToast("新密码不能为空");
            return false;
        }
        if (!user_new_password.getText().equals(user_password.getText())) {
            UIUtil.showToast("密码不一致");
        }
        return true;
    }


    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("修改密码");
        title_view.setShowDefaultRightValue();
        title_view.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {
                if (checkData()) {
                    commitData();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (call != null) {
            call.cancel();
        }
        if (getCheckCodeCall != null) {
            getCheckCodeCall.cancel();
        }
        super.onDestroy();
    }
}
