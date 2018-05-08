package com.bozhengjianshe.shenghuobang.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by zhao.wenchao on 2015/12/16.
 * 文件上传
 */
public class UploadFile {

    /**
     * 图片装进MultipartBody
     *
     * @param listPath
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBody(List<String> listPath) {
        if (null == listPath) {
            return null;
        }
        List<MultipartBody.Part> bodys = new ArrayList<>();
        for (String path : listPath) {
            File file = new File(path);
            if (!file.exists()) {
                break;
            }
            String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/" + fileType), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("1", file.getName(), requestBody);

            bodys.add(body);
        }
        return bodys;
    }

    public static RequestBody filesToMultipartBody1(List<String> listPath) {
        if (null == listPath) {
            return null;
        }
        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        mbody.addFormDataPart("type", "1");
        List<File> fileList = new ArrayList<File>();
        int i = 0;
        for (String path : listPath) {
            File file = new File(path);
            if (!file.exists()) {
                break;
            }
            fileList.add(file);
            String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
            mbody.addFormDataPart("image" + i, file.getName(), RequestBody.create(MediaType.parse("fileType"), file));

            i++;
        }
        RequestBody requestBody = mbody.build();
        return requestBody;
    }


}
