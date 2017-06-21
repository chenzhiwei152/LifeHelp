package com.yuanchangyuan.wanbei.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuanchangyuan.wanbei.R;
import com.yuanchangyuan.wanbei.base.BaseActivity;
import com.yuanchangyuan.wanbei.base.BaseContext;
import com.yuanchangyuan.wanbei.base.Constants;
import com.yuanchangyuan.wanbei.base.EventBusCenter;
import com.yuanchangyuan.wanbei.ui.index.MainActivity;
import com.yuanchangyuan.wanbei.utils.NetUtil;
import com.yuanchangyuan.wanbei.utils.UIUtil;
import com.yuanchangyuan.wanbei.view.CleanableEditText;
import com.yuanchangyuan.wanbei.view.CustomProgressDialog;
import com.yuanchangyuan.wanbei.view.TitleBar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnTextChanged;


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
    boolean isCodeNeeded = false;//验证码是否必须
    //登陆
    @BindView(R.id.login)
    Button login;
    //忘记密码
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    //注册
    @BindView(R.id.regist)
    TextView regist;
    /**
     * 是否清除密码
     */
    private boolean isClearPwd;
    //是否显示密码
    private boolean showStatus;
    //进度窗
    private CustomProgressDialog mDialog;
    //注册成功标识
    public static final int REGIST_CODE = 2;
    @BindString(R.string.login)
    String logins;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        mDialog = new CustomProgressDialog(this, "登录中...");

        //清除密码
        passWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && isClearPwd) {
//                    passWord.setText("");
                    isClearPwd = false;
                }
            }
        });
        login.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        regist.setOnClickListener(this);
        ivCheckCode.setOnClickListener(this);

    }

    @Override
    public void loadData() {
        SharedPreferences share = BaseContext.getInstance().getSharedPreferences("LAST_USERNAME", Activity.MODE_PRIVATE);
        if (null != share && share.contains("name")) {
            userName.setText(share.getString("name", ""));
            userName.setSelection(userName.getText().length());
        }
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
                    startActivity(new Intent(this, MainActivity.class));
//                    Login();
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


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String username = data.getStringExtra("user_name");
            String password = data.getStringExtra("password");
            userName.setText(username);
            passWord.setText(password);
            setLoginState();
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
       /* if (!TelephoneUtils.isMobile(telNumber)) {
            Toast.makeText(this, "手机号格式错误", Toast.LENGTH_SHORT).show();
            return;
        }*/ //不校验手机号格式（医生账号 不是手机号）
        if (password.length() < 6) {
            Toast.makeText(this, "密码至少为6位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isCodeNeeded) {
            checkCode = et_check_code.getText().toString();
            if (TextUtils.isEmpty(checkCode)) {
                Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
//        LoginUtils.login(this, telNumber.trim(), password.trim(), mDialog, checkCode);

    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventCenter) {
        if (null != eventCenter) {
            if (Constants.Tag.SHOW_CHECK_CODE == eventCenter.getEvenCode()) {
                isCodeNeeded = true;
                login.setEnabled(false);
                llCheckCode.setVisibility(View.VISIBLE);


            } else if (Constants.Tag.HIDE_CHECK_CODE == eventCenter.getEvenCode()) {
                llCheckCode.setVisibility(View.GONE);
                isCodeNeeded = false;
            } else if (eventCenter.getEvenCode() == Constants.Tag.REGIST_SUCCESS) {
//                resgisBean = (RegisteBean) eventCenter.getData();
//                if (null != resgisBean) {
//                    LoginUtils.login(this, resgisBean.getLoginName(), resgisBean.getPassword(), mDialog, "");
//                }
            } else if (eventCenter.getEvenCode() == Constants.Tag.LOGIN_FAILURE) {
                isClearPwd = true;
            }

        }
    }

    /**
     * 初始化标题
     */
    private void initTitle() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.color_ff6900);


        titleView.setTitle("登陆");
        titleView.setTitleColor(Color.WHITE);
        titleView.setBackgroundColor(getResources().getColor(R.color.color_ff6900));
        titleView.setImmersive(true);
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * @param s 用户名输入
     */
    @OnTextChanged(value = R.id.user_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterNameTextChanged(Editable s) {
        if (s.length() == 0) {
            login.setEnabled(false);
        } else {
            setLoginState();

        }
    }

    /**
     * @param s 密码输入
     */
    @OnTextChanged(value = R.id.pass_word, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterPassTextChanged(Editable s) {
        if (s.length() == 0) {
            login.setEnabled(false);
        } else {
            setLoginState();

        }
    }

    /**
     * @param s 验证码输入
     */
    @OnTextChanged(value = R.id.et_check_code, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterCodeTextChanged(Editable s) {
        if (isCodeNeeded) {
            if (s.length() == 0) {
                login.setEnabled(false);
            } else {
                setLoginState();
            }
        }
    }

    /**
     * 设置登陆按钮状态
     */
    private void setLoginState() {
        if (passWord.getText().length() > 0 && userName.getText().length() > 0) {
            login.setEnabled(true);
        } else {
            login.setEnabled(false);
        }
    }


}
