package com.sicnu.personal.knowledgesharingapp.home.model.datasource;

import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public interface GankDataSource {
    interface GankDataCallBack{
        void onLoadedSuccessful(GankKnowledgeDataBean gankKnowledgeDataBean);
        void onLoadedfailed(Throwable error);
    }
    void getRemoteGankData(String type,int count,int page, GankDataCallBack dataCallBack);
}
