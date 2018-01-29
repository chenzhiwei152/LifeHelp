package com.bozhengjianshe.shenghuobang.ui.index;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.AppManager;
import com.bozhengjianshe.shenghuobang.base.BaseActivity;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.fragment.BuildingMaterialsFragment;
import com.bozhengjianshe.shenghuobang.ui.fragment.IndexFragment;
import com.bozhengjianshe.shenghuobang.ui.fragment.MyFragment;
import com.bozhengjianshe.shenghuobang.ui.fragment.PropertyFragment;
import com.bozhengjianshe.shenghuobang.ui.fragment.ShoppingCartFragment;
import com.bozhengjianshe.shenghuobang.view.NoScrollViewPager;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    /**
     * 是否退出
     **/
    private boolean isWaitingExit = false;
    Class[] fragments = {IndexFragment.class, BuildingMaterialsFragment.class,ShoppingCartFragment.class, MyFragment.class, PropertyFragment.class};
    private int[] tabNames = {R.string.main_tab_name_index,R.string.main_tab_name_shopping_building,R.string.main_tab_name_shopping_cart, R.string.main_tab_name_me,R.string.main_tab_name_property};
    private int[] tabIcons = {R.drawable.selector_main_tab_index,R.drawable.selector_main_tab_mathedrinal,R.drawable.selector_main_tab_cards, R.drawable.selector_main_tab_mine,R.drawable.selector_main_tab_mine};
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
        viewPager.setOffscreenPageLimit(5);
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
}
