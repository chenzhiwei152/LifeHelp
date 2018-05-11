package com.bozhengjianshe.shenghuobang.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
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
 * 编辑收货地址
 * Created by chen.zhiwei on 2017-6-23.
 */

public class ShoppingAddressEditActivity extends BaseActivity {
    @BindView(R.id.title_view)
    TitleBar title_view;
    @BindView(R.id.et_address_name)
    EditText etAddressName;
    @BindView(R.id.et_address_phone)
    EditText etAddressPhone;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;
    @BindView(R.id.et_address_selected)
    EditText et_address_selected;
    @BindView(R.id.rl_selected)
    RelativeLayout rl_selected;
    @BindView(R.id.bmapView)
    MapView bmapView;
    private String tag = "0";//0新增，1编辑
    private String id = "";
    Call<SuperBean<String>> call;
    @BindView(R.id.tv_commit)
    Button tv_commit;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_add_shopping_address;
    }

    @Override
    public void initViewsAndEvents() {
        initTitle();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tag = bundle.getString("flag", "0");

            etAddressName.setText(bundle.getString("name", ""));
            etAddressPhone.setText(bundle.getString("phone", ""));
            etAddressDetail.setText(bundle.getString("detail", ""));
            et_address_selected.setText(bundle.getString("selected", ""));
            id = bundle.getString("id", "");
        }
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData()) {
                    commitData();
                }
            }
        });
//        rl_selected.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ShoppingAddressEditActivity.this, EaseBaiduMapActivity.class));
//            }
//        });

    }

    @Override
    public void loadData() {
        getLocation();
    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (eventBusCenter != null) {
            if (eventBusCenter.getEvenCode() == Constants.LOCATION_CITY_SUCCESS){
                getLocation();
            }
        }
    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    private void commitData() {
        DialogUtils.showDialog(ShoppingAddressEditActivity.this, "上传中...", false);
        Map<String, String> map = new HashMap<>();
        map.put("lxdh", etAddressPhone.getText().toString());
        map.put("lxr", etAddressName.getText().toString());
        map.put("lxxq", etAddressDetail.getText().toString());
        map.put("lxdz", et_address_selected.getText().toString());//联系人地址
        map.put("memberid", BaseContext.getInstance().getUserInfo().id);
        if (tag.equals("1")) {
            map.put("id", id);//如果是编辑的话是之前的地址id
            call = RestAdapterManager.getApi().editAddress(map);
        } else {
            call = RestAdapterManager.getApi().addAddress(map);
        }
        call.enqueue(new JyCallBack<SuperBean<String>>() {
            @Override
            public void onSuccess(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
                if (response != null && response.body() != null) {
                    UIUtil.showToast(response.body().getMsg());
                    if (response.body().state == Constants.successCode) {
//                        if (tag.equals("1")) {
//                            //编辑
//                            UIUtil.showToast("编辑成功");
//                        } else {
//                            //增加
//                            UIUtil.showToast("增加成功");
//                        }
                        EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.AddressUpdateSuccess));
                        finish();
                    } else {
                    }
                } else {
                    UIUtil.showToast("修改失败，请稍后重试");
                }
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Throwable t) {
                DialogUtils.closeDialog();
                UIUtil.showToast("修改失败，请稍后重试");
            }

            @Override
            public void onError(Call<SuperBean<String>> call, Response<SuperBean<String>> response) {
                DialogUtils.closeDialog();
                UIUtil.showToast("修改失败，请稍后重试");
            }
        });
    }


    private boolean checkData() {
        if (TextUtils.isEmpty(etAddressName.getText().toString())) {
            UIUtil.showToast("姓名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(etAddressPhone.getText().toString())) {
            UIUtil.showToast("联系方式不能为空");
            return false;
        }
        if (TextUtils.isEmpty(etAddressDetail.getText().toString())) {
            UIUtil.showToast("详细地址不能为空");
            return false;
        }
        return true;
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        title_view.setTitle("编辑地址");
        title_view.setTitleColor(Color.WHITE);
        title_view.setLeftImageResource(R.mipmap.ic_title_back);
        title_view.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        title_view.addAction(new TitleBar.TextAction("确定") {
//            @Override
//            public void performAction(View view) {
//                if (checkData()) {
//                    commitData();
//                }
//            }
//        });
        title_view.setImmersive(true);
    }

    @Override
    protected void onDestroy() {
        if (call != null) {
            call.cancel();
        }
        super.onDestroy();
    }


    private void getLocation() {
        if (BaseContext.getInstance().lastLocation == null) {
            return;
        }
        BaiduMap mBaiduMap;
        mBaiduMap = bmapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        LatLng llA = new LatLng(BaseContext.getInstance().lastLocation.getLatitude(), BaseContext.getInstance().lastLocation.getLongitude());
        OverlayOptions ooA = new MarkerOptions().position(llA).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.ease_icon_marka))
                .zIndex(4).draggable(true);

        mBaiduMap.clear();
        mBaiduMap.addOverlay(ooA);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(llA));
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(llA, 17.0f);
        mBaiduMap.animateMapStatus(u);

    }
}
