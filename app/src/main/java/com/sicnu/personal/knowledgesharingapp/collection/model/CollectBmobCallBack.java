package com.sicnu.personal.knowledgesharingapp.collection.model;

import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public interface CollectBmobCallBack {
   interface CollectCallBack{
       void theCollectDataIsExisted();
       void collectSuccessful();
       void collectFailed(int code,String reaon);
       void queryCollectFailed(int code,String reason);
   }
   interface QueryCollectCallBack{
        void queryCollectionSuccessful(List<CollectDataBean> dataBeen);
        void queryCollectFailed(int code,String reason);
    }
    interface DeleteCollectCallBack{
        void deleteCollectSuccessful();
        void deleteCollectFailed(int code,String reason);
    }
}
