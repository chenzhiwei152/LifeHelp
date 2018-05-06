package com.bozhengjianshe.shenghuobang.ui.utils;

import android.content.Context;
import android.text.TextUtils;

import com.bozhengjianshe.shenghuobang.api.JyCallBack;
import com.bozhengjianshe.shenghuobang.api.RestAdapterManager;
import com.bozhengjianshe.shenghuobang.base.BaseContext;
import com.bozhengjianshe.shenghuobang.base.Constants;
import com.bozhengjianshe.shenghuobang.base.EventBusCenter;
import com.bozhengjianshe.shenghuobang.ui.bean.CollectionBean;
import com.bozhengjianshe.shenghuobang.ui.bean.UserInfoBean;
import com.bozhengjianshe.shenghuobang.utils.ErrorMessageUtils;
import com.bozhengjianshe.shenghuobang.utils.UIUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class CollectionUtils {
    /**
     * 获取收藏
     * <p>
     * type       delete 删除收藏     add   增加收藏
     */
    public static void updateColloction(final Context context, String id, String type) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        final String ids = handleID(id, type);
        RequestBody formBody = new FormBody.Builder()
                .add("collects", ids)
                .add("id", BaseContext.getInstance().getUserInfo().id)
                .build();


        Call<CollectionBean> getCollection = RestAdapterManager.getApi().getCollection(formBody);
        getCollection.enqueue(new JyCallBack<CollectionBean>() {
            @Override
            public void onSuccess(Call<CollectionBean> call, Response<CollectionBean> response) {
                UIUtil.showToast(response.body().message);
                if (response.body().state == Constants.successCode) {
                    UserInfoBean userInfo = BaseContext.getInstance().getUserInfo();
                    userInfo.collects = ids;
                    BaseContext.getInstance().setUserInfo(userInfo);
                    EventBus.getDefault().post(new EventBusCenter<>(Constants.UPDATE_COLLECTION_SUCCESS));
                } else {

                }
            }

            @Override
            public void onError(Call<CollectionBean> call, Throwable t) {
            }

            @Override
            public void onError(Call<CollectionBean> call, Response<CollectionBean> response) {
                try {
                    ErrorMessageUtils.taostErrorMessage(context, response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static String handleID(String id, String type) {
        String ids = BaseContext.getInstance().getUserInfo().collects;

        if ("delete".equals(type)) {
            if (!TextUtils.isEmpty(ids)) {
                if (getIsInCollection(id)) {
                    String ss[] = ids.split(",");
                    if (ss != null) {
                        String after = "";
                        for (int i = 0; i < ss.length; i++) {
                            if (!ss[i].equals(id)) {
                                after += ss[i];
                                after += ",";
                            }
                        }
                        ids = after;
                    }
                }
            }
        } else if ("add".equals(type)) {
            if (!TextUtils.isEmpty(ids)) {
                if (!getIsInCollection(id)) {
                    ids += id;
                    ids += ",";
                }
            }
        }
        return ids;
    }

    public static boolean getIsInCollection(String id) {
        String ids = BaseContext.getInstance().getUserInfo().collects;
        if (TextUtils.isEmpty(ids) || TextUtils.isEmpty(id)) {
            return false;
        }
        return ids.contains(id);
    }
}
