package com.bozhengjianshe.shenghuobang.ui.activity;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceTypeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.utils.DialogManager;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;
import com.bozhengjianshe.shenghuobang.view.MenuItem;
import com.bozhengjianshe.shenghuobang.view.MenuItemEdittext;
import com.bozhengjianshe.shenghuobang.view.SelectDialog;
import com.bozhengjianshe.shenghuobang.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by chen.zhiwei on 2017-12-25.
 */

public class JoinsUsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_view)
    TitleBar title_view;

    @BindView(R.id.mi_principal)
    MenuItemEdittext mi_principal;
    @BindView(R.id.mi_tel)
    MenuItemEdittext mi_tel;
    @BindView(R.id.mi_address)
    MenuItemEdittext mi_address;
    @BindView(R.id.mi_serviced)
    MenuItem mi_serviced;
    @BindView(R.id.mi_service_content)
    MenuItem mi_service_content;
    @BindView(R.id.mi_service_range)
    MenuItem mi_service_range;
    private List<AllServiceTypeBean> fatherServiceList;
    private List<AllServiceTypeBean> childServiceList;
    private String fatherId;
    private List<String> childId = new ArrayList<>();
    //    private List<String> childText;
    private String childText;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_join_us;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        mi_principal.setOnClickListener(this);
        mi_tel.setOnClickListener(this);
        mi_address.setOnClickListener(this);
        mi_serviced.setOnClickListener(this);
        mi_service_content.setOnClickListener(this);
        mi_service_range.setOnClickListener(this);
        mi_service_content.setRightTextGravity(Gravity.LEFT);
        mi_service_range.setRightTextGravity(Gravity.LEFT);
    }

    @Override
    public void loadData() {
        getInfo();
//        getTyleList("0");
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
    private void initTitle() {
        title_view.setTitle("加入生活帮");
        title_view.setShowDefaultRightValue();
        title_view.addAction(new TitleBar.TextAction("完成") {
            @Override
            public void performAction(View view) {
                commitINfo();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mi_service_range:
                if (fatherServiceList != null && fatherServiceList.size() > 0) {
                    initFatherServiceData();
                } else {
                    getTyleList("0");
                }

                break;
            case R.id.mi_service_content:
                if (!TextUtils.isEmpty(fatherId)) {
                    if (childServiceList != null && childServiceList.size() > 0) {
                        initchildServiceData();
                    } else {
                        getTyleList(fatherId);
                    }

                } else {
                    UIUtil.showToast("请选择服务范围");
                }
                break;
        }

    }

    private void initFatherServiceData() {
        List<String> value = new ArrayList<>();
        for (int i = 0; i < fatherServiceList.size(); i++) {
            value.add(fatherServiceList.get(i).getText());
        }
        SelectDialog selectDialog = DialogManager.getInstance().creatSelectDialog(JoinsUsActivity.this, "选择服务范围", value, true);
        selectDialog.setResultListener(new SelectDialog.ResultListener() {
            @Override
            public void confirmClik(boolean[] status) {
                if (status != null) {
                    fatherId = "";
                    for (int i = 0; i < status.length; i++) {
                        if (status[i]) {
                            fatherId = fatherServiceList.get(i).getId() + "";
                            mi_service_range.getRightText().setText(fatherServiceList.get(i).getText());
                        }
                    }
                }


            }
        });
        selectDialog.show();

    }

    private void initchildServiceData() {

        List<String> value = new ArrayList<>();
        for (int i = 0; i < childServiceList.size(); i++) {
            value.add(childServiceList.get(i).getText());
        }
        SelectDialog selectDialog = DialogManager.getInstance().creatSelectDialog(JoinsUsActivity.this, "选择服务内容", value, true);
        selectDialog.setResultListener(new SelectDialog.ResultListener() {
            @Override
            public void confirmClik(boolean[] status) {
                if (status != null) {
                    childText = "";
                    childId.clear();
                    for (int i = 0; i < status.length; i++) {
                        if (status[i]) {
                            childId.add(childServiceList.get(i).getId() + "");
                            childText += childServiceList.get(i).getText() + "、";
                        }
                    }
                    mi_service_content.getRightText().setText(childText);
                }


            }
        });
        selectDialog.show();
    }

    /**
     * 提交商家信息
     */
    private void commitINfo() {
        Map<String, String> map = new HashMap<>();
        map.put("companyAddress", mi_address.getEditText().getText().toString());
        map.put("contacts", mi_tel.getEditText().getText().toString());
        map.put("controller", mi_principal.getEditText().getText().toString());
        map.put("typeIds", JSONArray.toJSONString(childId));
        map.put("userId", BaseContext.getInstance().getUserInfo().id);
        Call<SuperBean<String>> addMerchantInfo = RestAdapterManager.getApi().addMerchantInfo(map);
        addMerchantInfo.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                UIUtil.showToast(response.body().getMsg());
//                finish();
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {

            }
        });

    }

    /**
     * 获取商家信息
     */
    private void getInfo() {
        if (BaseContext.getInstance().getUserInfo() == null) {
            return;
        }
        Call<SuperBean<String>> getMerchantInfo = RestAdapterManager.getApi().getMerchantInfo(BaseContext.getInstance().getUserInfo().id);
        getMerchantInfo.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                LogUtils.e(response.body().getData());
                LogUtils.e("userId:" + BaseContext.getInstance().getUserInfo().id);
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {

            }
        });
    }

    /**
     * 服务类型
     */
    private void getTyleList(final String id) {
        Call<SuperBean<List<AllServiceTypeBean>>> getAllServiceTypeList = RestAdapterManager.getApi().getAllServiceTypeList(id, "2");
        getAllServiceTypeList.enqueue(new JyCallBack<SuperBean<List<AllServiceTypeBean>>>() {
            @Override
            public void onSuccess(Call<SuperBean<List<AllServiceTypeBean>>> call, Response<SuperBean<List<AllServiceTypeBean>>> response) {
                if (response != null && response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                    if (id.equals("0")) {
                        fatherServiceList = response.body().getData();
                        initFatherServiceData();
                    } else {
                        childServiceList = response.body().getData();
                        initchildServiceData();
                    }
                }

            }

            @Override
            public void onError(Call<SuperBean<List<AllServiceTypeBean>>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperBean<List<AllServiceTypeBean>>> call, Response<SuperBean<List<AllServiceTypeBean>>> response) {

            }
        });
    }
}
