package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.RegistCodeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.utils.login.LoginApi;
import com.bozhengjianshe.shenghuobang.ui.utils.login.OnLoginListener;
import com.bozhengjianshe.shenghuobang.ui.utils.login.UserInfo;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.TelephoneUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.CleanableEditText;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by chen.zhiwei on 2016/7/14.
 * 注册
 */
public class RegisterActivity extends BaseActivity implements
        View.OnClickListener {
    @BindView(R.id.user_name)
    CleanableEditText etPhone;
    @BindView(R.id.et_check_code)
    EditText etCode;
    @BindView(R.id.tv_check_code)
    TextView tvGetCode;

    @BindView(R.id.checkBox)
    CheckBox cbAgree;


    @BindView(R.id.tv_next)
    TextView tvNext;

    @BindView(R.id.title_view)
    TitleBar titleView;

    @BindString(R.string.regist)
    String regist;

    @BindView(R.id.user_nick_name)
    CleanableEditText user_nick_name;
    @BindView(R.id.user_password)
    CleanableEditText user_password;
    @BindView(R.id.iv_weixin)
    ImageView iv_weixin;
    @BindView(R.id.iv_qq)
    ImageView iv_qq;
    @BindView(R.id.iv_sina)
    ImageView iv_sina;
    @BindView(R.id.tv_to_login)
    TextView tv_to_login;


    CountDownTimer timer;

    boolean isCodeSended = false;
    Call<SuperBean<String>> call;//注册
    Call<RegistCodeBean> getCheckCodeCall;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.mine_regist_activity_layout;
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
        iv_sina.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        tv_to_login.setOnClickListener(this);
    }

    @Override
    public void loadData() {

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


    private void getCode(String phoneNumber) {


        if (TextUtils.isEmpty(phoneNumber)) {
            UIUtil.showToast("请输入手机号");
            return;
        } else if (!TelephoneUtils.isMobile(phoneNumber)) {
            UIUtil.showToast("手机号格式错误");
            return;
        }

        timer.start();
        RequestBody formBody = new FormBody.Builder()
                .add("phone", phoneNumber)
                .build();
        Map<String ,String> map=new HashMap<>();
        map.put("phone",phoneNumber);
        getCheckCodeCall = RestAdapterManager.getApi().getCheckCode(map);
        getCheckCodeCall.enqueue(new JyCallBack<RegistCodeBean>() {
            @Override
            public void onSuccess(Call<RegistCodeBean> call, Response<RegistCodeBean> response) {
//                if (response != null && response.body().getState() == Constants.successCode) {
                UIUtil.showToast(response.body().getMessage());
//                } else {
//                    UIUtil.showToast("发送验证码失败");
//                }
            }

            @Override
            public void onError(Call<RegistCodeBean> call, Throwable t) {

            }

            @Override
            public void onError(Call<RegistCodeBean> call, Response<RegistCodeBean> response) {

            }
        });


    }

    private void initTitle() {

        titleView.setTitle(regist);
//        titleView.setTitleColor(Color.WHITE);
//        titleView.setBackgroundColor(getResources().getColor(R.color.color_ff6900));
//        titleView.setImmersive(true);
    }


    @Override
    protected View isNeedLec() {
        return null;
    }

    @OnClick(R.id.userAgreement)
    public void agreeMentClick(View view) {

//        Intent web = new Intent(this, WebViewActivity.class);
//        Bundle webBundle = new Bundle();
//        webBundle.putString(Constants.STRING_TAG, AGREEMENT_URL);
//        webBundle.putBoolean("show_title", true);
//        web.putExtras(webBundle);
//        startActivity(web);

    }

//    @OnTextChanged(value = R.id.user_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    public void afterNameTextChanged(Editable s) {
//        if (s.length() == 0) {
//            tvNext.setEnabled(false);
//        } else {
//            if (cbAgree.isChecked() && isCodeSended && !TextUtils.isEmpty(etCode.getText().toString())) {
//                tvNext.setEnabled(true);
//            } else {
//                tvNext.setEnabled(false);
//            }
//
//        }
//    }
//
//    @OnTextChanged(value = R.id.et_check_code, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    public void afterCodeTextChanged(Editable s) {
//        if (s.length() == 0) {
//            tvNext.setEnabled(false);
//        } else {
//            if (cbAgree.isChecked() && isCodeSended && !TextUtils.isEmpty(etPhone.getText().toString())) {
//                tvNext.setEnabled(true);
//            } else {
//                tvNext.setEnabled(false);
//            }
//
//        }
//    }
//
//    @OnCheckedChanged(R.id.checkBox)
//    public void agrreeMentCheck(CompoundButton view, boolean isChecked) {
//        if (isChecked && isCodeSended && !TextUtils.isEmpty(etPhone.getText().toString()) && !TextUtils.isEmpty(etCode.getText().toString())) {
//            tvNext.setEnabled(true);
//        } else {
//            tvNext.setEnabled(false);
//        }
//    }

    @OnClick(R.id.tv_next)
    public void onNextClick(View view) {
        if (!NetUtil.isNetworkConnected(this)) {
            UIUtil.showToast("网络连接失败，请检查您的网络");
            return;
        }
        if (TextUtils.isEmpty(etPhone.getText())) {
            UIUtil.showToast("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(etCode.getText())) {
            UIUtil.showToast("验证码不能为空");
            return;
        }
//        if (TextUtils.isEmpty(user_nick_name.getText())) {
//            UIUtil.showToast("昵称不能为空");
//            return;
//        }
        if (TextUtils.isEmpty(user_password.getText())) {
            UIUtil.showToast("密码不能为空");
            return;
        }
        if (user_password.getText().length() < 6) {
            Toast.makeText(this, "密码至少为6位", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("phone", etPhone.getText().toString())
                .add("password", user_password.getText().toString())
                .add("dxcode", etCode.getText().toString())
                .add("status", "1")
                .build();
        call = RestAdapterManager.getApi().reister(formBody);

        call.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                try {

                    if (response != null && response.body() != null) {
                        if (response.body().state == Constants.successCode) {
                            Intent findPsIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            timer.cancel();
                            findPsIntent.putExtra("phone", etPhone.getText().toString());
                            findPsIntent.putExtra("pwd", user_password.getText().toString());
                            startActivity(findPsIntent);
                        }
                        UIUtil.showToast(response.body().message);
                    } else {
                        UIUtil.showToast("注册失败");
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {
                UIUtil.showToast("注册失败");
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                try {
                    ErrorMessageUtils.taostErrorMessage(RegisterActivity.this, response.errorBody().string(), "");
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


    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (null != eventBusCenter) {
            if (eventBusCenter.getEvenCode() == Constants.REGIST_SUCCESS) {
                finish();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_weixin:
                //微信登录
                //测试时，需要打包签名；sample测试时，用项目里面的demokey.keystore
                //打包签名apk,然后才能产生微信的登录

                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                if (wechat.isClientValid()) {

                    login(wechat.getName());
                } else {
                    UIUtil.showToast("未安装微信");
                }
                break;
            case R.id.iv_qq:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                login(qq.getName());
                break;
            case R.id.iv_sina:
                //新浪微博
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                login(sina.getName());
                break;
            case R.id.tv_to_login:
                finish();
                break;

        }
    }

    private void login(String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
                return true;
            }

            public boolean onRegister(UserInfo info) {
//                LoginUtils.thirdLogin(RegisterActivity.this, info);
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                return true;
            }
        });
        api.login(this);
    }


}
