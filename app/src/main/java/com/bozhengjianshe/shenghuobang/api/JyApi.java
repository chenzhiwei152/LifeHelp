package com.bozhengjianshe.shenghuobang.api;


import com.bozhengjianshe.shenghuobang.bean.AboutInfoBean;
import com.bozhengjianshe.shenghuobang.bean.ErrorBean;
import com.bozhengjianshe.shenghuobang.ui.bean.ADListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceTypeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.CardListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.CollectionBean;
import com.bozhengjianshe.shenghuobang.ui.bean.CommitOrderResultBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsCommentsItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.MerchantServiceContentBean;
import com.bozhengjianshe.shenghuobang.ui.bean.OrderDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.QuestionBean;
import com.bozhengjianshe.shenghuobang.ui.bean.RegistCodeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.ShoppingAddressListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperAddressListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperGoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperOrderBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperOrderListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperShoppingCardsBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperUrlBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperUserBean;
import com.bozhengjianshe.shenghuobang.ui.bean.UserInfoBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * 网络请求接口
 */

public interface JyApi {
    String api = "/life/api/";
    String suffix = ".html";

    /**
     * 登陆
     */
    @POST(api + "login" + suffix)
//    Call<SuperBean<UserInfoBean>> login(@Body Map<String, String> map);
    Call<SuperUserBean<UserInfoBean>> login(@Body() RequestBody map);


    /**
     * 注册用户发送短信验证码
     *
     * @param
     * @return
     */
    @GET(api + "sendzcdx" + suffix)
    Call<RegistCodeBean> getCheckCode(@QueryMap Map<String, String> map);

    /**
     * 找回密码发送短信验证码
     *
     * @param
     * @return
     */
    @POST(api + "sendDxFindPswd" + suffix)
    Call<SuperBean<String>> getCheckCodeForFPW(@Body RequestBody map);

    /**
     * 修改手机号码发送短信验证码
     *
     * @param
     * @return
     */
    @POST(api + "sendDxFindPswd" + suffix)
    Call<SuperBean<String>> getCheckCodeForcPhone(@Body RequestBody map);

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @POST(api + "insertMember" + suffix)
    Call<SuperBean<String>> reister(@Body RequestBody map);

    /**
     * 忘记密码
     *
     * @param map
     * @return
     */
    @POST(api + "findPassword" + suffix)
    Call<SuperBean<String>> commitNewPassword(@Body RequestBody map);

    /**
     * 修改手机号码
     *
     * @param map
     * @return
     */
    @POST(api + "updateMPhone" + suffix)
    Call<SuperBean<String>> commitNewPhone(@Body RequestBody map);

    /**
     * 修改密码
     *
     * @param map
     * @return
     */
    @POST(api + "updateMPassword" + suffix)
    Call<ErrorBean> accountSafety(@Body RequestBody map);


    /**
     * 增加收货地址
     *
     * @param map
     * @return
     */
    @GET(api + "updateMAddress" + suffix)
    Call<SuperBean<String>> addAddress(@QueryMap Map<String, String> map);

    /**
     * 编辑收货地址
     *
     * @param map
     * @return
     */
    @GET(api + "updateMUAddress" + suffix)
    Call<SuperBean<String>> editAddress(@QueryMap Map<String, String> map);

    /**
     * 删除收货地址
     *
     * @param
     * @return
     */
    @POST(api + "deleteMAddress" + suffix)
    Call<ErrorBean> deleteAddress(@Body RequestBody map);

    /**
     * 获取地址列表
     *
     * @param
     * @return
     */
    @GET(api + "getMAddress" + suffix)
    Call<SuperAddressListBean<List<ShoppingAddressListItemBean>>> getAddressList(@QueryMap Map<String, String> map);


    /**
     * 获取首页列表
     *
     * @param
     * @return
     */
    @GET(api + "getComList" + suffix)
    Call<SuperGoodsListBean<List<GoodsListBean>>> getGoodsList(@QueryMap Map<String, String> map);


    /**
     * 首页服务广告信息
     */
    @GET(api + "getFwgg" + suffix)
    Call<SuperListBean<List<ADListItemBean>>> getServiceAdList();

    /**
     * 首页建材广告信息
     */
    @GET(api + "getJcgg" + suffix)
    Call<SuperListBean<List<ADListItemBean>>> getBuilAdList();

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @POST(api + "up" + suffix)
    Call<SuperUrlBean<String>> uploadFile(@Body RequestBody file);

    /**
     * 修改用户头像
     *
     * @param map
     * @return
     */
    @POST(api + "updateMHead" + suffix)
    Call<SuperUrlBean<String>> upLoadInfo(@Body RequestBody map);

    /**
     * 修改姓名
     *
     * @param map
     * @return
     */
    @POST(api + "updateMName" + suffix)
    Call<SuperBean<String>> updateName(@Body RequestBody map);


    /*** 获取购物车列表id
     *
     *
     * @return
     */
    @POST(api + "updateMCommodities" + suffix)
    Call<CardListBean> getCardList(@Body RequestBody map);


    /**
     * 获取支付加密后的信息
     *
     * @param map
     * @return
     */
    @POST("/api/pay/payConfirm")
    Call<SuperBean<String>> getRsaOrderInfo(@Body Map<String, String> map);


    /**
     * 获取订单详情
     *
     * @param
     * @return
     */
    @POST(api + "getOrderDetail" + suffix)
    Call<SuperOrderBean<OrderDetailBean>> getRentOrderDetails(@Body RequestBody body);

    /**
     * 取消订单
     * *
     *
     * @param
     * @return
     */
    @POST(api + "getRegionList" + suffix)
    Call<SuperOrderBean<OrderDetailBean>> quitOrder(@Body RequestBody body);


    /**
     * 提交订单
     *
     * @param map
     * @return
     */
    @POST(api + "insertOrder" + suffix)
    Call<CommitOrderResultBean> getRentOrder(@Body RequestBody map);


    /**
     * 获取订单列表
     *
     * @param
     * @return
     */
    @POST(api + "getOrderList" + suffix)
    Call<SuperOrderListBean<List<BuyOrderListItemBean>>> getRentOrderList(@Body RequestBody map);

    /**
     * 获取所有服务分类
     */
    @GET(api + "getClistByPid" + suffix)
    Call<SuperGoodsListBean<List<AllServiceTypeBean>>> getAllServiceTypeList(@Query("pid") String pid);

//    /**
//     * 服务类列表获取
//     */
//    @POST("/api/data/appGetProductList")
//    Call<SuperBean<AllServiceContentBean>> getAllServiceContentList(@Body Map<String, String> map);

    /**
     * 添加收藏
     */
    @POST("/api/collection/addCollection")
    Call<SuperBean<String>> addCollection(@Body RequestBody map);

    /**
     * 获取收藏id
     */
    @POST(api + "updateMCollects" + suffix)
    Call<CollectionBean> getCollection(@Body RequestBody map);

    /**
     * 删除收藏
     */
    @GET("/api/collection/deleteCollection")
    Call<SuperBean<String>> deleteCollection(@Query("id") String id);

    /**
     * 获取商户信息
     */
    @GET("/api/provider/getProviderInfo")
    Call<SuperBean<String>> getMerchantInfo(@Query("userId") String userId);


    /**
     * 提交商户信息
     */
    @POST("/api/provider/add")
    Call<SuperBean<String>> addMerchantInfo(@Body RequestBody map);


    /**
     * 获取常见问题
     */
    @GET(api + "getCjwtList" + suffix)
    Call<SuperGoodsListBean<List<QuestionBean>>> getQuestion();

    /**
     * 获取评论
     *
     * @param map
     * @return
     */
    @GET(api + "getCommentList" + suffix)
    Call<SuperGoodsListBean<List<GoodsCommentsItemBean>>> getComments(@QueryMap Map<String, String> map);


    /**
     * 更新购物车
     */
    @POST(api + "updateMCommodities" + suffix)
    Call<SuperShoppingCardsBean<String>> updateShoppingCards(@Body RequestBody map);

    /**
     * 获取关于数据
     */
    @GET(api + "getAboutUs" + suffix)
    Call<AboutInfoBean> getAboutInfo();

    /**
     * 获取订单市场订单
     */
    @POST(api + "getOrderFwList" + suffix)
    Call<SuperOrderListBean<List<BuyOrderListItemBean>>> getOrderList(@Body RequestBody body);
    /**
     * 获取服务商服务内容
     */
    @POST(api + "getMFwnr" + suffix)
    Call<MerchantServiceContentBean> getServiceContent(@Body RequestBody body);


}
