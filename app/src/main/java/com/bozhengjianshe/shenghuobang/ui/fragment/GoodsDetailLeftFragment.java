package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-12-5.
 */

public class GoodsDetailLeftFragment extends BaseFragment {
    @BindView(R.id.web_vv)
    WebView web_vv;

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

//        web_vv.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                web_vv.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
//                super.onPageFinished(view, url);
//            }
//        });
//        web_vv.addJavascriptInterface(this, "App");



//        web_vv.loadUrl("https://www.baidu.com");
        refresh("https://www.baidu.com");
    }
    @JavascriptInterface
    public void resize(final float height) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                web_vv.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
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

//        web_vv.loadUrl(url);


    }
    public void refresh(String url) {
        //设置外层高度
        final View parentView = (View) web_vv;
        web_vv.getSettings().setJavaScriptEnabled(true);
        web_vv.addJavascriptInterface(new HeightGetter(web_vv), "jo");
        web_vv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            public void onPageFinished(WebView view, String url) {
                web_vv.loadUrl("javascript:window.jo.run(document.documentElement.scrollHeight+'');");
            }
        });
        if (!TextUtils.isEmpty(url)) {
            web_vv.loadUrl(url);
        }
    }

    private class HeightGetter {
        private final View parentView;
        public HeightGetter(View parentView) {
            this.parentView = parentView;
        }

        @JavascriptInterface
        public void run(final String height) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() { //获取到的高度 转换为dp
//                    parentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.px2dp(getActivity(), Integer.valueOf(height))));
                    EventBus.getDefault().post(new EventBusCenter<>(Constants.AddressUpdateSuccess,height));
                }
            });
        }
    }
}
