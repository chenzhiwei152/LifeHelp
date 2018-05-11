package com.bozhengjianshe.shenghuobang.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.MerchantServiceContentBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperUrlBean;
import com.bozhengjianshe.shenghuobang.ui.bean.UserInfoBean;
import com.bozhengjianshe.shenghuobang.utils.DialogUtils;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.ImageLoadedrManager;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.utils.UploadFile;
import com.bozhengjianshe.shenghuobang.utils.photoTool.PhotoPicActivity;
import com.bozhengjianshe.shenghuobang.utils.photoTool.TakingPicturesActivity;
import com.bozhengjianshe.shenghuobang.view.MyDialog;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-6-22.
 */

public class PersonInformationActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.tv_sick_name)
    TextView tv_sick_name;
    @BindView(R.id.rl_name)
    RelativeLayout rl_name;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_birthday)
    TextView tv_birthday;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.bt_commit)
    Button bt_commit;
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.rl_service_content)
    RelativeLayout rl_service_content;
    @BindView(R.id.rl_address)
    RelativeLayout rl_address;
    @BindView(R.id.tv_service)
    TextView tv_service;
    @BindView(R.id.rl_quit_login)
    RelativeLayout rl_quit_login;
    @BindView(R.id.vv_v)
    View vv_v;

    private Call<SuperUrlBean<String>> upLoadImageCall;
    private Call<SuperUrlBean<String>> upLoadInfoCall;
    private String headimg;
    private String nickname;
    private String sex;
    private String birthday;

    List<String> list = new ArrayList<String>();

    private static final int resultCode_Photos = 10;//跳转到相册
    private static final int resultCode_Camera = 11;//跳转到相机
    private String from = "";


    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_person_information;
    }

    @Override
    public void initViewsAndEvents() {
        from = getIntent().getStringExtra("from");
        if (!TextUtils.isEmpty(from) && from.equals("Merchant")) {
            rl_service_content.setVisibility(View.VISIBLE);
            rl_address.setVisibility(View.GONE);
            getServiceContent();
            rl_quit_login.setVisibility(View.VISIBLE);
            vv_v.setVisibility(View.VISIBLE);
        } else {
            rl_quit_login.setVisibility(View.GONE);
            rl_service_content.setVisibility(View.GONE);
            vv_v.setVisibility(View.GONE);
            rl_address.setVisibility(View.VISIBLE);
        }

        initTitle();
        bt_commit.setOnClickListener(this);
        tv_sick_name.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        tv_sex.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        rl_quit_login.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        if (BaseContext.getInstance().getUserInfo() != null) {

            tv_sick_name.setText(BaseContext.getInstance().getUserInfo().name);
            ImageLoadedrManager.getInstance().displayCycle(this, BaseContext.getInstance().getUserInfo().head, iv_head, R.mipmap.ic_head_default);
        }
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

    private void upLoadInfo() {

//        if (TextUtils.isEmpty(nickname) && TextUtils.isEmpty(sex) && TextUtils.isEmpty(birthday) && TextUtils.isEmpty(headimg)) {
//            UIUtil.showToast("信息没有更改");
//            return;
//        }
        DialogUtils.showDialog(this, "上传中", false);

        RequestBody body = new FormBody.Builder()
                .add("id", BaseContext.getInstance().getUserInfo().id)
                .add("url", headimg)
                .build();


        upLoadInfoCall = RestAdapterManager.getApi().upLoadInfo(body);
        upLoadInfoCall.enqueue(new JyCallBack<SuperUrlBean<String>>() {
            @Override
            public void onSuccess(Call<SuperUrlBean<String>> call, Response<SuperUrlBean<String>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null) {
                    UIUtil.showToast(response.body().message);
                    if (response.body().state == Constants.successCode) {
                        UserInfoBean userInfo = BaseContext.getInstance().getUserInfo();
                        if (userInfo != null) {
                            if (!TextUtils.isEmpty(headimg)) {
                                userInfo.head = headimg;
                            }
                            BaseContext.getInstance().updateUserInfo(userInfo);
                        }
                        sex = "";
                        headimg = "";
                        nickname = "";
                        birthday = "";
                        EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOGIN_SUCCESS));
                    } else UIUtil.showToast("修改失败");
                } else {
                    UIUtil.showToast("修改失败");
                }


            }

            @Override
            public void onError(Call<SuperUrlBean<String>> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast("修改失败");
            }

            @Override
            public void onError(Call<SuperUrlBean<String>> call, Response<SuperUrlBean<String>> response) {
                UIUtil.showToast("修改失败");
                DialogUtils.closeDialog();
            }
        });
    }

    private void upLoadImage() {
        DialogUtils.showDialog(this, "上传中", false);
        upLoadImageCall = RestAdapterManager.getApi().uploadFile(UploadFile.filesToMultipartBody1(list));
        upLoadImageCall.enqueue(new JyCallBack<SuperUrlBean<String>>() {
            @Override
            public void onSuccess(Call<SuperUrlBean<String>> call, Response<SuperUrlBean<String>> response) {
//                UIUtil.showToast(response.body());
                DialogUtils.closeDialog();
                UIUtil.showToast(response.body().getMsg());
                if (response != null && response.body() != null && response.body().getCode() == Constants.successCode) {
                    if (!TextUtils.isEmpty(response.body().getData())) {
                        //上传图片成功
                        headimg = response.body().getData();
                        upLoadInfo();
                    }
                }
            }

            @Override
            public void onError(Call<SuperUrlBean<String>> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast("上传头像失败");
            }

            @Override
            public void onError(Call<SuperUrlBean<String>> call, Response<SuperUrlBean<String>> response) {
                try {
                    DialogUtils.closeDialog();
                    ErrorMessageUtils.taostErrorMessage(PersonInformationActivity.this, response.errorBody().string(), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        list.clear();
        if (resultCode == resultCode_Camera) {
            //相机返回图片
            Bundle b = data.getExtras();
            String fileName = b.getString("picture");
            list.add(fileName);
        } else if (resultCode == resultCode_Photos) {
            // 图库中选择
            if (data == null || "".equals(data)) {
                return;
            }
            list = data.getExtras().getStringArrayList("photo");
            LogUtils.e("image路径--" + list.get(0));
        }
//        headIsChange = true;
        if (list.size() > 0) {
            ImageLoadedrManager.getInstance().displayNoFilter(this, list.get(0), iv_head);
            bt_commit.performClick();
        }

    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("我的账号");
        title_view.setShowDefaultRightValue();
        title_view.setImmersive(true);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_commit:
                //保存
                if (list.size() > 0) {
                    if (!TextUtils.isEmpty(headimg)) {
                        upLoadInfo();
                    } else
                        upLoadImage();
                } else {
                    upLoadInfo();
                }
                break;
            case R.id.tv_sex:
//zhanghao密码
                startActivity(new Intent(this, AccountSafetyActivity.class));
                break;
            case R.id.rl_name:
//修改用户名
                startActivity(new Intent(this, EditNameActivity.class));
                break;
            case R.id.tv_birthday:
                //修改手机号码
                startActivity(new Intent(this, EditPhoneActivity.class));
                break;
            case R.id.iv_head:
                photodialog();
                break;
            case R.id.tv_address:
                startActivity(new Intent(this, ShoppingAddressActivity.class));
                break;
            case R.id.rl_quit_login:
                if (BaseContext.getInstance().getUserInfo() == null) {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                showExitDialog();
                break;
        }
    }
    private void showExitDialog() {
        DialogUtils.showOrderCancelMsg(this, "确定要退出登录吗？", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag().equals("确定")) {
                    BaseContext.getInstance().Exit();
                    Intent i = new Intent(PersonInformationActivity.this, LoginActivity.class);
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

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 选择图片dialog
     */
    public void photodialog() {

        View view = View.inflate(PersonInformationActivity.this, R.layout.dialog_publish_photo, null);
        showdialog(view);

        final TextView photo = (TextView) view.findViewById(R.id.photo);
        final TextView picture = (TextView) view.findViewById(R.id.picture);
        // 从图库中选择
        photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (photo.getText().toString().contains(getResources().getString(R.string.publish_photo))) {

                    Intent intent = new Intent(PersonInformationActivity.this, PhotoPicActivity.class);
                    intent.putExtra("max", 9);

                    startActivityForResult(intent, resultCode_Photos);
                    myDialog.dismiss();
                }

            }
        });
        // 拍照
        picture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (picture.getText().toString().contains(getResources().getString(R.string.publish_picture))) {
                    Intent intent = new Intent(PersonInformationActivity.this, TakingPicturesActivity.class);

                    startActivityForResult(intent, resultCode_Camera);
                    myDialog.dismiss();
                }
            }
        });

    }

    /**
     * 性别dialog
     */
    public void sexDialog() {

        View view = View.inflate(PersonInformationActivity.this, R.layout.dialog_publish_photo, null);
        showdialog(view);

        final TextView photo = (TextView) view.findViewById(R.id.photo);
        final TextView picture = (TextView) view.findViewById(R.id.picture);
        photo.setText("男");
        picture.setText("女");
        // 从图库中选择
        photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setSexValue("男");
                myDialog.dismiss();

            }
        });
        // 拍照
        picture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setSexValue("女");
                myDialog.dismiss();
            }
        });

    }

    private void setSexValue(String sex) {

        if (!TextUtils.isEmpty(sex)) {
            this.sex = ("男".equals(sex) ? "1" : "0");
            tv_sex.setText(sex);
        }
    }

    private MyDialog myDialog;

    /**
     * 弹出dialog
     *
     * @param view
     */
    private void showdialog(View view) {

        myDialog = new MyDialog(this, 0, 0, view, R.style.dialog);
        myDialog.show();
    }

    private void getServiceContent() {
        RequestBody body = new FormBody.Builder()
                .add("memberid", BaseContext.getInstance().getUserInfo().id)
                .build();

        Call<MerchantServiceContentBean> getServiceContent = RestAdapterManager.getApi().getServiceContent(body);
        getServiceContent.enqueue(new JyCallBack<MerchantServiceContentBean>() {
            @Override
            public void onSuccess(Call<MerchantServiceContentBean> call, Response<MerchantServiceContentBean> response) {
                if (response.body().getState() == Constants.successCode) {
                    String content = "";
                    if (response.body().getEjfl() != null) {
                        for (String ss : response.body().getEjfl()) {
                            content += ss;
                            content += ",";
                        }
                    }
                    tv_service.setText(content);
                }
            }

            @Override
            public void onError(Call<MerchantServiceContentBean> call, Throwable t) {

            }

            @Override
            public void onError(Call<MerchantServiceContentBean> call, Response<MerchantServiceContentBean> response) {

            }
        });
    }
}
