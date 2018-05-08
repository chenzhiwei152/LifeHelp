package com.bozhengjianshe.shenghuobang.ui.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.CommitOrderBean;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.CardCacheBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperShoppingCardsBean;
import com.bozhengjianshe.shenghuobang.ui.bean.UserInfoBean;
import com.bozhengjianshe.shenghuobang.utils.ParserUtil;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/6 0006.
 */

public class ShoppingCardsUtils {
    public static void updateShoppingCards(final List<GoodsListBean> ids, final String type) {

        RequestBody formBody = new FormBody.Builder()
                .add("id", BaseContext.getInstance().getUserInfo().id + "")
                .add("commodities", handleId(changeBean(ids), type))
                .build();
        Call<SuperShoppingCardsBean<String>> updateShoppingCards = RestAdapterManager.getApi().updateShoppingCards(formBody);
        updateShoppingCards.enqueue(new JyCallBack<SuperShoppingCardsBean<String>>() {
            @Override
            public void onSuccess(Call<SuperShoppingCardsBean<String>> call, Response<SuperShoppingCardsBean<String>> response) {
                UIUtil.showToast(response.body().getMsg());
                if (response.body().getCode() == Constants.successCode) {
                    UserInfoBean userInfo = BaseContext.getInstance().getUserInfo();
                    userInfo.commodities = handleId(changeBean(ids), type);
                    BaseContext.getInstance().setUserInfo(userInfo);
                    EventBus.getDefault().post(new EventBusCenter<>(Constants.ADD_TO_CARD));
                }
            }

            @Override
            public void onError(Call<SuperShoppingCardsBean<String>> call, Throwable t) {

            }

            @Override
            public void onError(Call<SuperShoppingCardsBean<String>> call, Response<SuperShoppingCardsBean<String>> response) {

            }
        });
    }

    private static String handleId(List<CardCacheBean> content, String type) {
        String ids = BaseContext.getInstance().getUserInfo().commodities;
        List<CardCacheBean> goodsListBean = new ArrayList<>();

        goodsListBean = (List<CardCacheBean>) ParserUtil.parseArray(ids, CardCacheBean.class);
        List<CardCacheBean> goodsListBeanAll = new ArrayList<>();

        goodsListBeanAll = (List<CardCacheBean>) ParserUtil.parseArray(ids, CardCacheBean.class);
        if (goodsListBean == null || goodsListBean.size() == 0) {
            goodsListBean = new ArrayList<>();
            for (int i = 0; i < content.size(); i++) {
                goodsListBean.add(content.get(i));
            }
            return JSON.toJSONString(goodsListBean);
        }
        if (type.equals("delete")) {
            for (int i = 0; i < goodsListBeanAll.size(); i++) {
                for (int j = 0; j < content.size(); j++) {
                    if (goodsListBeanAll.get(i).getId() .equals(content.get(j).getId()) ) {
                        goodsListBean.remove(content.get(j));
                    }
                }
            }
        } else {
            List<CardCacheBean> cache = new ArrayList<>();
            for (int j = 0; j < content.size(); j++) {
                if (goodsListBean.contains(content.get(j))) {
                    for (int i = 0; i < goodsListBean.size(); i++) {
                        if (goodsListBean.get(i).getId() .equals(content.get(j).getId()) ) {
                            int u = goodsListBean.get(i).getNum();
                            u += 1;
                            goodsListBean.get(i).setNum(u);
                        }
                    }
                } else {
                    cache.add(content.get(j));
                }
            }
            if (cache.size() > 0) {
                for (int i = 0; i < cache.size(); i++) {
                    goodsListBean.add(cache.get(i));
                }
            }
        }
        return JSON.toJSONString(goodsListBean);
    }

    /**
     * 商品详情转换成购物车的数据
     *
     * @param content
     * @return
     */
    public static List<CardCacheBean> changeBean(List<GoodsListBean> content) {
        List<CardCacheBean> CardCacheBean = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            CardCacheBean cardCacheBean = new CardCacheBean();
            cardCacheBean.setCname(content.get(i).getCname());
            cardCacheBean.setDj(content.get(i).getProfit()+"");
            cardCacheBean.setId(content.get(i).getId()+"");
            cardCacheBean.setThumbnail(content.get(i).getThumbnail());
            CardCacheBean.add(cardCacheBean);
        }
        return CardCacheBean;

    }

    /**
     * 获取当前用户下的购物车id
     *
     * @return
     */
    public static String getIds() {
        String content = BaseContext.getInstance().getUserInfo().commodities;
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        List<CardCacheBean> goodsListBean = new ArrayList<>();

        goodsListBean = (List<CardCacheBean>) ParserUtil.parseArray(content, CardCacheBean.class);
        String ids = "";
        if (goodsListBean != null) {
            for (int i = 0; i < goodsListBean.size(); i++) {
                ids += goodsListBean.get(i).getId();
                ids += ",";
            }
        }
        return ids;
    }

    /**
     * 商品详情转换成提交订单所需要的数据
     */

    public static List<CommitOrderBean> changeCommitBean(List<GoodsListBean> content) {
        List<CommitOrderBean> CardCacheBean = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            CommitOrderBean cardCacheBean = new CommitOrderBean();
            cardCacheBean.setName(content.get(i).getCname());
            cardCacheBean.setDj(content.get(i).getProfit()+"");
            cardCacheBean.setId(content.get(i).getId()+"");
            cardCacheBean.setNum(content.get(i).getNum()+"");
            cardCacheBean.setZj(content.get(i).getProfit() * content.get(i).getNum()+"");
            CardCacheBean.add(cardCacheBean);
        }
        return CardCacheBean;
    }
}
