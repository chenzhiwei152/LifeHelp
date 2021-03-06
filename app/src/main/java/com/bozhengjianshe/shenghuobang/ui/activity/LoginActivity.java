package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.bean.UserInfoBean;
import com.bozhengjianshe.shenghuobang.ui.index.MainActivity;
import com.bozhengjianshe.shenghuobang.ui.utils.LoginUtils;
import com.bozhengjianshe.shenghuobang.utils.NetUtil;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.CleanableEditText;
import com.bozhengjianshe.shenghuobang.view.CustomProgressDialog;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import butterknife.BindString;
import butterknife.BindView;
import retrofit2.Call;


/**
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.ll_login)
    LinearLayout ll_login;
    //titleView
    @BindView(R.id.title_view)
    TitleBar titleView;
    //用户名，手机号码
    @BindView(R.id.user_name)
    CleanableEditText userName;
    //密码
    @BindView(R.id.pass_word)
    EditText passWord;
    @BindView(R.id.iv_check_code)
    ImageView ivCheckCode;
    //图形验证码
    @BindView(R.id.et_check_code)
    EditText et_check_code;
    @BindView(R.id.ll_check_code)
    LinearLayout llCheckCode;
    //登陆
    @BindView(R.id.login)
    Button login;
    //忘记密码
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    //注册
    @BindView(R.id.regist)
    TextView regist;
    @BindView(R.id.regist_service_provider)
    TextView regist_service_provider;
    /**
     * 是否清除密码
     */
    private boolean isClearPwd;
    //进度窗
    private CustomProgressDialog mDialog;
    //注册成功标识
    public static final int REGIST_CODE = 2;
    @BindString(R.string.login)
    String logins;

    Call<SuperBean<UserInfoBean>> loginCall;
    @BindView(R.id.rg_group)
    RadioGroup rg_group;
    @BindView(R.id.rb_user)
    RadioButton rb_user;
    private String type = "1";

    @Override
    public int getContentViewLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        mDialog = new CustomProgressDialog(this, "登录中...");

        login.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        regist.setOnClickListener(this);
        ivCheckCode.setOnClickListener(this);
        regist_service_provider.setOnClickListener(this);
        rb_user.setChecked(true);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_user:
                        type = "1";
                        break;
                    case R.id.rb_proper:
                        type = "2";
                        break;
                    case R.id.rb_service:
                        type = "3";
                        break;
                }
            }
        });
    }

    @Override
    public void loadData() {
        UserInfoBean userInfo = BaseContext.getInstance().getUserInfo();
        if (null != userInfo) {
            if (userInfo.status == 1) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, MerchantOrderActivity.class));
            }
            finish();
        }
//        if (null != userInfo) {
//            userName.setText(userInfo.phone);
//            userName.setSelection(userName.getText().length());
//        }
//        setLoginState();
    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                //登陆
                UIUtil.ShowOrHideSoftInput(this, false);
                if (!NetUtil.isNetworkConnected(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    return;
                } else {
//                    startActivity(new Intent(this, MainActivity.class));
                    Login();
                }
                break;
            case R.id.regist:
                //注册
                Intent registIntent = new Intent(this, RegisterActivity.class);
                startActivityForResult(registIntent, REGIST_CODE);
                break;
            case R.id.forget_password:
                //忘记密码
                Intent forgetIntent = new Intent(this, FindPasswordActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.regist_service_provider:
                startActivity(new Intent(this, RegistersServiceActivity.class));
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String username = data.getStringExtra("user_name");
            String password = data.getStringExtra("password");
            userName.setText(username);
            passWord.setText(password);
//            setLoginState();
        }
    }

    /**
     * 登陆
     */
    private void Login() {
        final String telNumber = userName.getText().toString();
        final String password = passWord.getText().toString();
        String checkCode = "";
        if (TextUtils.isEmpty(telNumber) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "账号或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (telNumber.trim().length() != 11) {
            Toast.makeText(this, "请输入11位账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "密码至少为6位", Toast.LENGTH_SHORT).show();
            return;
        }
        LoginUtils.commitlogin(this, userName.getText().toString().trim(), passWord.getText().toString().trim(), type);
//        commitlogin();

    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventCenter) {
        if (null != eventCenter) {
            if (Constants.LOGIN_SUCCESS == eventCenter.getEvenCode()) {
                finish();
            }

        }
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        titleView.setTitle("登陆");
//        titleView.setTitleColor(Color.WHITE);
//        titleView.setBackgroundColor(getResources().getColor(R.color.color_ff6900));
//        titleView.setImmersive(true);
    }


}
