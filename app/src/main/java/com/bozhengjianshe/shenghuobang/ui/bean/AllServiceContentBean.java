package com.bozhengjianshe.shenghuobang.ui.bean;

import java.util.List;

/**
 * Created by chen.zhiwei on 2017-12-11.
 */

public class AllServiceContentBean {

    /**
     * pageNo : 1
     * totalCount : 1
     * pageCount : 1
     * pageSize : 10
     * data : [{"id":5,"serviceName":"测试服务1","servicePrice":121,"serviceType":150,"isBanner":1,"serviceDetail":"<p><img src=\"/ueditor/jsp/upload/image/20171209/1512788855351091090.jpeg_800.jpg\" title=\"1512788855351091090.jpeg\"/><\/p><p><img src=\"/ueditor/jsp/upload/image/20171209/1512788855397082531.jpg_800.jpg\" title=\"1512788855397082531.jpg\"/><\/p><p><img src=\"/ueditor/jsp/upload/image/20171209/1512788855382048591.jpg_800.jpg\" title=\"1512788855382048591.jpg\"/><\/p><p><img src=\"/ueditor/jsp/upload/image/20171209/1512788856991014133.jpg_800.jpg\" title=\"1512788856991014133.jpg\"/><\/p><p><img src=\"/ueditor/jsp/upload/image/20171209/1512788857038057959.jpg_800.jpg\" title=\"1512788857038057959.jpg\"/><\/p><p><br/><\/p>","serviceImg":"http://47.92.24.138:8080/upload/goodsImg/20171209110834241.jpg","seeType":1,"isRec":1,"isDiscount":1,"discountPrice":121}]
     */

    private int pageNo;
    private int totalCount;
    private int pageCount;
    private int pageSize;
    private List<GoodsListBean> data;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
