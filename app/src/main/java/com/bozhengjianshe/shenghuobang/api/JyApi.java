package com.bozhengjianshe.shenghuobang.api;


import com.bozhengjianshe.shenghuobang.bean.ErrorBean;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceContentBean;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceTypeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.CardListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.CollectionBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.OrderDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.ShoppingAddressListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperAddressListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperGoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperOrderListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.UserInfoBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


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
    Call<SuperBean<UserInfoBean>> login(@Body Map<String, String> map);


    /**
     * 注册用户发送短信验证码
     *
     * @param
     * @return
     */
    @POST(api +"sendzcdx"+ suffix)
    Call<SuperBean<String>> getCheckCode(@Body Map<String, String> map);
    /**
     * 找回密码发送短信验证码
     *
     * @param
     * @return
     */
    @POST(api +"sendDxFindPswd"+ suffix)
    Call<SuperBean<String>> getCheckCodeForFPW(@Body Map<String, String> map);
    /**
     * 修改手机号码发送短信验证码
     *
     * @param
     * @return
     */
    @POST(api +"sendDxFindPswd"+ suffix)
    Call<SuperBean<String>> getCheckCodeForcPhone(@Body Map<String, String> map);

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @POST(api+"insertMember"+suffix)
    Call<SuperBean<String>> reister(@Body Map<String, String> map);

    /**
     * 忘记密码
     *
     * @param map
     * @return
     */
    @POST(api+"findPassword"+suffix)
    Call<SuperBean<String>> commitNewPassword(@Body Map<String, String> map);
    /**
     * 修改手机号码
     *
     * @param map
     * @return
     */
    @POST(api+"updateMPhone"+suffix)
    Call<SuperBean<String>> commitNewPhone(@Body Map<String, String> map);

    /**
     * 修改密码
     *
     * @param map
     * @return
     */
    @POST(api+"updateMPassword"+suffix)
    Call<ErrorBean> accountSafety(@Body Map<String, String> map);


    /**
     * 增加收货地址
     *
     * @param map
     * @return
     */
    @POST(api+"updateMAddress"+suffix)
    Call<SuperBean<String>> addAddress(@Body Map<String, String> map);

    /**
     * 编辑收货地址
     *
     * @param map
     * @return
     */
    @POST("/api/deliveryAddress/updateDeliveryAddress")
    Call<SuperBean<String>> editAddress(@Body Map<String, String> map);

    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    @GET("/api/deliveryAddress/deleteById")
    Call<ErrorBean> deleteAddress(@Query("id") String id);

    /**
     * 获取地址列表
     *
     * @param
     * @return
     */
    @POST(api+"getMAddress"+suffix)
    Call<SuperAddressListBean<List<ShoppingAddressListItemBean>>> getAddressList(@Body Map<String, String> map);



    /**
     * 获取首页列表
     *
     * @param
     * @return
     */
    @POST(api+"getComList"+suffix)
    Call<SuperGoodsListBean<List<GoodsListBean>>> getGoodsList(@Body Map<String, String> map);



    /**
     * 广告位数据
     */
    @GET("/api/goods/adList")
    Call<SuperBean<List<GoodsListBean>>> getAdList(@Query("userId") String userId);

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("/sys/uploadUserImg")
    Call<SuperBean<String>> uploadFile(@Part MultipartBody.Part file);

    /**
     * 上传个人信息
     *
     * @param map
     * @return
     */
    @POST("/api/user/updateUserHeadImg")
    Call<String> upLoadInfo(@Body Map<String, String> map);

    /**
     * 修改姓名
     *
     * @param map
     * @return
     */
    @POST(api+"updateMName"+suffix)
    Call<SuperBean<String>> updateName(@Body Map<String, String> map);

    /**
     * 获取商品详细信息
     *
     * @param id
     * @param type
     * @return
     */
    @GET("/api/data/appGetProductDetailById")
    Call<SuperBean<GoodsDetailBean>> getGoodsDetail(@Query("id") String id, @Query("type") String type);

    /*** 添加到购物车
     *
     * @return
     */
    @POST("/api/cart/addCart")
    Call<SuperBean<String>> addTocard(@Body Map<String, String> map);

    /*** 获取购物车列表
     *
     * @return
     */
    @GET("/api/cart/getCartList")
    Call<SuperBean<List<CardListItemBean>>> getCardList(@Query("userId") String userId);

    /*** 删除购物车数据
     *
     * @return
     */
    @GET("/api/cart/deleteCart")
    Call<SuperBean<String>> deleteCardList(@Query("id") String id);

    /**
     * 提交购买订单
     *
     * @param map
     * @return
     */
    @POST("/api/buyorder/insert")
    Call<SuperBean<String>> getCommitOrder(@Body Map<String, String> map);

    /**
     * 获取购买订单列表
     *
     * @param map
     * @return
     */
    @POST("/api/buyorder/appGetAllList")
    Call<SuperBean<BuyOrderListItemBean>> getBuyOrderList(@Body Map<String, String> map);

    /**
     * 获取支付加密后的信息
     *
     * @param map
     * @return
     */
    @POST("/api/pay/payConfirm")
    Call<SuperBean<String>> getRsaOrderInfo(@Body Map<String, String> map);

    /**
     * 获取买卖订单详情
     *
     * @param id
     * @return
     */
    @GET("/api/buyorder/appGetBuyOrderDetail")
    Call<SuperBean<OrderDetailBean>> getOrderDetails(@Query("id") String id);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    @GET("/api/order/getOrderDetail")
    Call<SuperBean<OrderDetailBean>> getRentOrderDetails(@Query("orderId") String orderId);

    /**
     * 取消订单
     * *
     *
     * @param orderId
     * @return
     */
    @GET("/api/order/cancleOrder")
    Call<SuperBean<OrderDetailBean>> quitOrder(@Query("orderId") String orderId);




    /**
     * 提交订单
     *
     * @param map
     * @return
     */
    @POST("/api/order/submitOrder")
    Call<SuperBean<String>> getRentOrder(@Body Map<String, String> map);


    /**
     * 获取订单列表
     *
     * @param
     * @return
     */
    @POST(api+"getOrderList"+suffix)
    Call<SuperOrderListBean<List<BuyOrderListItemBean>>> getRentOrderList(@Body Map<String, String> map);

    /**
     * 获取所有服务分类
     */
    @GET("/api/productType/getProductClassify/{parentId}/{type}")
    Call<SuperBean<List<AllServiceTypeBean>>> getAllServiceTypeList(@Path("parentId") String parentId, @Path("type") String type);

    /**
     * 服务类列表获取
     */
    @POST("/api/data/appGetProductList")
    Call<SuperBean<AllServiceContentBean>> getAllServiceContentList(@Body Map<String, String> map);

    /**
     * 添加收藏
     */
    @POST("/api/collection/addCollection")
    Call<SuperBean<String>> addCollection(@Body Map<String, String> map);

    /**
     * 获取收藏id
     */
    @POST(api+"updateMCollects"+suffix)
    Call<CollectionBean> getCollection(@Body Map<String, String> map);

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
    Call<SuperBean<String>> addMerchantInfo(@Body Map<String, String> map);
}
