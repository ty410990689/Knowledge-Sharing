package com.sicnu.personal.knowledgesharingapp.gank.model.datasource;


import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public interface GankDataSource {
    interface GankDataCallBack{
        void onLoadedSuccessful(GankDataBean gankDataBean);
        void onLoadedfailed(Throwable error);
    }
    void getRemoteGankData(String type, int count, int page, GankDataCallBack dataCallBack);
}
