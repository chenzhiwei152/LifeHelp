package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.view.NoScrollWebView;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-12-5.
 */

public class GoodsDetailLeftFragment extends BaseFragment {
    @BindView(R.id.web_vv)
    NoScrollWebView web_vv;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_goodsdetail_left;
    }

    @Override
    protected void initViewsAndEvents() {
        WebSettings webSettings = web_vv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        web_vv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        web_vv.loadUrl("https://www.baidu.com");
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected View isNeedLec() {
        return null;
    }

    @Override
    public boolean isRegistEventBus() {
        return true;
    }

    @Override
    public void onMsgEvent(EventBusCenter eventBusCenter) {
        if (eventBusCenter != null) {
            if (eventBusCenter.getEvenCode() == Constants.UPDA_GOODS_DETAIL_H5) {
                initDate((String) eventBusCenter.getData());
            }
        }
    }

    private void initDate(String url) {

        web_vv.loadUrl(url);


    }
}
