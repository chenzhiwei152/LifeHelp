package com.bozhengjianshe.shenghuobang.ui.activity;

import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.TelephoneUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.CleanableEditText;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

import static com.bozhengjianshe.shenghuobang.R.id.et_check_code;

/**
 * 找回密码
 */
public class EditPhoneActivity extends BaseActivity {

    @BindView(R.id.title_view)
    TitleBar titleView;
    CountDownTimer timer;
    @BindView(R.id.user_name)
    CleanableEditText etPhone;
    @BindView(R.id.user_old_phone)
    CleanableEditText user_old_phone;
    @BindView(et_check_code)
    EditText etCode;
    @BindView(R.id.tv_check_code)
    TextView tvGetCode;
    @BindView(R.id.user_new_pass)
    CleanableEditText user_new_pass;
    @BindView(R.id.tv_next)
    Button tvNext;
    private Call<SuperBean<String>> call;
    boolean isCodeSended = false;
    Call<SuperBean<String>> getCheckCodeCall;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.mine_edittext_phone_activity_layout;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        etCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});


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
    }

    private void initTitle() {

        titleView.setTitle("修改手机号码");
        titleView.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {
                onNextClick(view);
            }
        });
        titleView.setImmersive(true);
    }


    @Override
    public void loadData() {

    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (null != eventBusCenter) {

        }
    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    @OnClick(R.id.tv_check_code)
    public void onGetCodeClick(View view) {

        String phoneNumber = etPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            UIUtil.showToast("请输入手机号");
            return;
        }
        if (phoneNumber.trim().length() != 11) {
            Toast.makeText(this, "请输入11位账号", Toast.LENGTH_SHORT).show();
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
        Map<String,String> map=new HashMap<>();
        map.put("phone",phone);
        getCheckCodeCall = RestAdapterManager.getApi().getCheckCodeForcPhone(map);
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


    @OnClick(R.id.tv_next)
    public void onNextClick(View view) {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast("网络连接失败，请检查您的网络");
            return;
        }
        if (TextUtils.isEmpty(user_old_phone.getText())) {
            UIUtil.showToast("旧手机号码不能为空");
            return;
        }
        String phoneNumber = etPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            UIUtil.showToast("请输入手机号");
            return;
        }
        if (phoneNumber.trim().length() != 11||user_old_phone.getText().toString().trim().length()!=11) {
            Toast.makeText(this, "请输入11位账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TelephoneUtils.isMobile(phoneNumber)||!TelephoneUtils.isMobile(user_old_phone.getText().toString().trim())) {
            UIUtil.showToast("手机号格式错误");
            return;
        }

        if (TextUtils.isEmpty(etCode.getText())) {
            UIUtil.showToast("验证码不能为空");
            return;
        }

        commitData();
    }

    private void commitData() {
        Map<String, String> map = new HashMap<>();
        map.put("dxcode", etCode.getText().toString());
        map.put("phone", user_old_phone.getText().toString());
        map.put("nphone", etPhone.getText().toString());
        call = RestAdapterManager.getApi().commitNewPhone(map);
        call.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                if (response != null && response.body() != null) {
                    if (response.body().state == Constants.successCode) {
                        UIUtil.showToast(response.body().message);
                        finish();
                    } else {
                        UIUtil.showToast("修改手机号码失败~请稍后重试");
                    }
                } else {
                    UIUtil.showToast("修改手机号码失败~请稍后重试");
                }
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {
                UIUtil.showToast("修改手机号码失败~请稍后重试");
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                try {
                    ErrorMessageUtils.taostErrorMessage(EditPhoneActivity.this, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        if (call != null) {
            call.cancel();
        }
        if (getCheckCodeCall != null) {
            getCheckCodeCall.cancel();
        }
        super.onDestroy();
    }

}
