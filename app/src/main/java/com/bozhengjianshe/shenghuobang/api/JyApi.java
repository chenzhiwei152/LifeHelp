package com.bozhengjianshe.shenghuobang.api;


import com.bozhengjianshe.shenghuobang.bean.ErrorBean;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceContentBean;
import com.bozhengjianshe.shenghuobang.ui.bean.AllServiceTypeBean;
import com.bozhengjianshe.shenghuobang.ui.bean.BuyOrderListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.CardListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.CollectionItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsFilterBean;
import com.bozhengjianshe.shenghuobang.ui.bean.GoodsListBean;
import com.bozhengjianshe.shenghuobang.ui.bean.MemberRankBean;
import com.bozhengjianshe.shenghuobang.ui.bean.OrderDetailBean;
import com.bozhengjianshe.shenghuobang.ui.bean.ShoppingAddressListItemBean;
import com.bozhengjianshe.shenghuobang.ui.bean.ShopsFilterBean;
import com.bozhengjianshe.shenghuobang.ui.bean.SuperBean;
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


    /**
     * 登陆
     */
    @POST("/api/user/login")
    Call<SuperBean<UserInfoBean>> login(@Body Map<String, String> map);

    /**
     * 第三方登录
     */
    @POST("/api/user/userAuthLogin")
    Call<SuperBean<UserInfoBean>> authLogin(@Body Map<String, String> map);

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @GET("/resource/sms/getCheckCode")
    Call<ErrorBean> getCheckCode(@Query("phone") String phone);

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @POST("/api/user/registerUser")
    Call<String> reister(@Body Map<String, String> map);

    /**
     * 忘记密码
     *
     * @param map
     * @return
     */
    @POST("/api/user/updateUserPhone")
    Call<ErrorBean> commitNewPassword(@Body Map<String, String> map);

    /**
     * 修改密码
     *
     * @param map
     * @return
     */
    @POST("/api/user/updateUserPwd")
    Call<ErrorBean> accountSafety(@Body Map<String, String> map);


    /**
     * 增加收货地址
     *
     * @param map
     * @return
     */
    @POST("/api/deliveryAddress/addAddress")
    Call<String> addAddress(@Body Map<String, String> map);

    /**
     * 编辑收货地址
     *
     * @param map
     * @return
     */
    @POST("/api/deliveryAddress/updateDeliveryAddress")
    Call<String> editAddress(@Body Map<String, String> map);

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
     * @param userid
     * @return
     */
    @GET("/api/deliveryAddress/getAllDeliveryAddress")
    Call<SuperBean<List<ShoppingAddressListItemBean>>> getAddressList(@Query("userid") String userid);


    /**
     * 获取商品类型筛选条件
     *
     * @return
     */
    @GET("/api/goodstype/appGetAllList")
    Call<SuperBean<List<GoodsFilterBean>>> getAllFilterType();

    /**
     * 获取商品门店筛选条件
     *
     * @return
     */
    @POST("/api/shop/appGetAllList")
    Call<SuperBean<List<ShopsFilterBean>>> getAllFilterShops();

    /**
     * 获取首页列表
     *
     * @param type
     * @return
     */
    @GET("/resource/appIndex/getAppIndexData")
    Call<GoodsListBean> getGoodsList(@Query("type") String type);

    /**
     * 实名认证
     *
     * @param map
     * @return
     */
    @POST("/api/user/userAuth")
    Call<String> commitRealName(@Body Map<String, String> map);

    /**
     * 获取会员登记信息
     *
     * @return
     */
    @GET("/api/viptype/appGetAllList")
    Call<SuperBean<List<MemberRankBean>>> getMemberRank();

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
    @POST("/api/user/updateUserName")
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
     * 押金支付获取订单号
     *
     * @param map
     * @return
     */
    @POST("/api/vipDeposit/insert")
    Call<SuperBean<String>> getDepositOrder(@Body Map<String, String> map);


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
     * @param userId
     * @return
     */
    @GET("/api/order/getOrderListByUserId")
    Call<SuperBean<List<BuyOrderListItemBean>>> getRentOrderList(@Query("userId") String userId);

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
     * 获取收藏
     */
    @GET("/api/collection/getAllCollection")
    Call<SuperBean<List<CollectionItemBean>>> getCollection(@Query("userId") String userId);

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
