package com.sicnu.personal.knowledgesharingapp.pretty.model.datasource;

import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public interface PrettyDataSource {
    interface PrettyDataCallBack{
        void onLoadedSuccessful(PrettyDataBean prettyDataBean);
        void onLoadedFailed(Throwable throwable);
    }
    void getRemotePrettyData(int page,PrettyDataCallBack callBack);
}
