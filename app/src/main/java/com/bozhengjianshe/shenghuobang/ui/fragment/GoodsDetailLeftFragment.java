package com.bozhengjianshe.shenghuobang.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.bozhengjianshe.shenghuobang.R;
import com.bozhengjianshe.shenghuobang.base.BaseFragment;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.adapter.GoodsDetailItemAdapter;
import com.bozhengjianshe.shenghuobang.utils.LogUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chen.zhiwei on 2017-12-5.
 */

public class GoodsDetailLeftFragment extends BaseFragment {
    @BindView(R.id.web_vv)
    WebView web_vv;
    @BindView(R.id.rvList)
    RecyclerView rvList;
    private GoodsDetailItemAdapter itemAdapter;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_goodsdetail_left;
    }

    @Override
    protected void initViewsAndEvents() {

        itemAdapter = new GoodsDetailItemAdapter(getContext());
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(itemAdapter);
        rvList.setNestedScrollingEnabled(false);



//        WebSettings webSettings = web_vv.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        web_vv.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//        });

    }

//    @JavascriptInterface
//    public void resize(final float height) {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                web_vv.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
//            }
//        });
//    }

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
                itemAdapter.addList((List<String>)eventBusCenter.getData());
            }
        }
    }

    private void initDate(String url) {

//        web_vv.loadUrl(url);
//        refresh(url);
        LogUtils.e("url:" + url);


    }

//    public void refresh(String url) {
//        //设置外层高度
//        final View parentView = (View) web_vv;
//        web_vv.getSettings().setJavaScriptEnabled(true);
//        web_vv.addJavascriptInterface(new HeightGetter(web_vv), "jo");
//        final Mobile mobile = new Mobile();
//        web_vv.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//
//            public void onPageFinished(WebView view, String url) {
//                web_vv.loadUrl("javascript:window.jo.run(document.documentElement.scrollHeight+'');");
//                mobile.onGetWebContentHeight();
//            }
//        });
//        if (!TextUtils.isEmpty(url)) {
//            web_vv.loadUrl(url);
//        }
//    }
//
//    private class Mobile {
//        @JavascriptInterface
//        public void onGetWebContentHeight() {
//            //重新调整webview高度
//            web_vv.post(new Runnable() {
//                @Override
//                public void run() {
//                    web_vv.measure(0, 0);
//                    int measuredHeight = web_vv.getMeasuredHeight();
//                    LogUtils.e("measure" + measuredHeight);
//                    EventBus.getDefault().post(new EventBusCenter<>(Constants.UPDA_DETAIL_WEBVIEW_HEIGHT, measuredHeight));
//                }
//            });
//        }
//    }
//
//    private class HeightGetter {
//        private final View parentView;
//
//        public HeightGetter(View parentView) {
//            this.parentView = parentView;
//        }
//
//        @JavascriptInterface
//        public void run(final String height) {
//            getActivity().runOnUiThread(new Runnable() {
//                public void run() { //获取到的高度 转换为dp
////                    parentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtil.px2dp(getActivity(), Integer.valueOf(height))));
////                    EventBus.getDefault().post(new EventBusCenter<>(Constants.AddressUpdateSuccess,height));
//                }
//            });
//        }
//    }
}
