package com.bozhengjianshe.shenghuobang.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.activity.MerchantOrderActivity;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperUserBean;
import com.bozhengjianshe.shenghuobang.ui.bean.UserInfoBean;
import com.bozhengjianshe.shenghuobang.ui.index.MainActivity;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.SharePreManager;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.sql.Timestamp;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-27.
 */

public class LoginUtils {
    static Call<SuperUserBean<UserInfoBean>> loginCall;
    static Call<SuperBean<UserInfoBean>> thirdLoginCall;
    static boolean isSuccess;

    public static void commitlogin(final Context context, final String tel, String password, final String type) {
        DialogUtils.showDialog(context, "登陆...", false);
        RequestBody formBody = new FormBody.Builder()
                .add("phone", tel)
                .add("password", password).add("act", "1").add("status", type)
                .build();

        loginCall = RestAdapterManager.getApi().login(formBody);
        loginCall.enqueue(new JyCallBack<SuperUserBean<UserInfoBean>>() {
            @Override
            public void onSuccess(Call<SuperUserBean<UserInfoBean>> call, Response<SuperUserBean<UserInfoBean>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    BaseContext.getInstance().setUserInfo(response.body().getData());
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    SharePreManager.instance(context).setLoginTime(now.getTime());
                    SharePreManager.instance(context).setUserInfo(response.body().getData());
                    SharePreManager.instance(context).setUserInfo(response.body().getData());
                    EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOGIN_SUCCESS));
                    Intent jmActivityIntent = null;
                    if (type.equals("1")) {

                        jmActivityIntent = new Intent(context, MainActivity.class);
                    } else if (type.equals("3")) {
                        jmActivityIntent = new Intent(context, MerchantOrderActivity.class);
                    }
                    context.startActivity(jmActivityIntent);
                    ((Activity) context).finish();
                } else {
                    try {
                        UIUtil.showToast(response.body().getMsg());
                    } catch (Exception e) {
                    }

                }
            }

            @Override
            public void onError(Call<SuperUserBean<UserInfoBean>> call, Throwable t) {
                UIUtil.showToast("登陆失败~请稍后重试");
                DialogUtils.closeDialog();
            }

            @Override
            public void onError(Call<SuperUserBean<UserInfoBean>> call, Response<SuperUserBean<UserInfoBean>> response) {
                try {
                    DialogUtils.closeDialog();
                    ErrorMessageUtils.taostErrorMessage(context, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
