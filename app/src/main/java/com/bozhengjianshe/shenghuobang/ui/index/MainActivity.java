package com.bozhengjianshe.shenghuobang.ui.index;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.AppManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.fragment.BuildingMaterialsFragment;
import com.bozhengjianshe.shenghuobang.ui.fragment.IndexFragment;
import com.bozhengjianshe.shenghuobang.ui.fragment.MyFragment;
import com.bozhengjianshe.shenghuobang.ui.fragment.ShoppingCartFragment;
import com.bozhengjianshe.shenghuobang.ui.utils.BdLocationUtil;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;
import com.bozhengjianshe.shenghuobang.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;


public class MainActivity extends BaseActivity {
    private static final int BAIDU_ACCESS_COARSE_LOCATION = 100;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    /**
     * 是否退出
     **/
    private boolean isWaitingExit = false;
    Class[] fragments = {IndexFragment.class, BuildingMaterialsFragment.class, ShoppingCartFragment.class, MyFragment.class};//, PropertyFragment.class
    private int[] tabNames = {R.string.main_tab_name_index, R.string.main_tab_name_shopping_building, R.string.main_tab_name_shopping_cart, R.string.main_tab_name_me};//,R.string.main_tab_name_property
    private int[] tabIcons = {R.drawable.selector_main_tab_index, R.drawable.selector_main_tab_mathedrinal, R.drawable.selector_main_tab_cards, R.drawable.selector_main_tab_mine};//,R.drawable.selector_main_tab_property

    //    private List<BaseFragment> fragmentList;
    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViewsAndEvents() {

//        fragmentList = new ArrayList<>();
//        fragmentList.add(new IndexFragment());
//        fragmentList.add(new MyFragment());
        for (int i = 0; i < tabNames.length; i++) {
            View tabView = View.inflate(this, R.layout.layout_tab_item, null);
            TextView textView = (TextView) tabView.findViewById(R.id.tab_title);
            textView.setText(tabNames[i]);
            // 利用这种办法设置图标是为了解决默认设置图标和文字出现的距离较大问题
            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[i], 0, 0);
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(textView));
        }
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public Fragment getItem(int position) {
                return Fragment.instantiate(MainActivity.this, fragments[position].getName());
            }

        });
        // Tablayout选择tab监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = mTabLayout.getSelectedTabPosition();//tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        myPermissionRequest();
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
        if (null != eventBusCenter) {
        }
    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    /**
     * 再按一次退出程序。
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isWaitingExit) {
                isWaitingExit = false;
//                Intent intent = new Intent(mContext, TabIconService.class);
//                stopService(intent);
                AppManager.getAppManager().AppExit(MainActivity.this);
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出程序...", Toast.LENGTH_SHORT).show();

                isWaitingExit = true;

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isWaitingExit = false;
                    }
                }, 3000);
                return true;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 动态请求权限，安卓手机版本在5.0以上时需要
     */
    private void myPermissionRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查是否拥有权限，申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, BAIDU_ACCESS_COARSE_LOCATION);
            } else {
                // 已拥有权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                myLocation();
            }
        } else {
            // 安卓手机版本在5.0时，配置清单中已申明权限，作相应处理，此处正对sdk版本低于23的手机
            myLocation();
        }
    }

    /**
     * 百度地图定位的请求方法   拿到 国 省 市  区
     */
    private void myLocation() {
        BdLocationUtil.getInstance().requestLocation(new BdLocationUtil.MyLocationListener() {
            @Override
            public void myLocation(BDLocation location) {
                if (location == null) {
                    return;
                }
                if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    String mCounty = location.getCountry();        //获取国家
                    String mProvince = location.getProvince();     //获取省份
                    String mCity = location.getCity();             //获取城市
                    String mDistrict = location.getDistrict();     //获取区
                    LogUtils.e("==requestLocation===", "myLocation: " + mCounty + "=" + mProvince + "=" + mCity + "=" + mDistrict);
                    BaseContext.getInstance().city=location.getCity();
                    BaseContext.getInstance().lastLocation=location;
                    EventBus.getDefault().post(new EventBusCenter<Integer>(Constants.LOCATION_CITY_SUCCESS));
                }
            }
        }, this);
    }

    /**
     * 权限请求的返回结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_ACCESS_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 第一次获取到权限，请求定位
                    myLocation();
                } else {
                    // 没有获取到权限，做特殊处理
                    LogUtils.e("", "请求权限失败");
                }
                break;

            default:
                break;
        }
    }

}
