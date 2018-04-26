package com.sicnu.personal.knowledgesharingapp.collection.presenter;

import android.content.Context;

import com.sicnu.personal.knowledgesharingapp.collection.contact.CollectContact;
import com.sicnu.personal.knowledgesharingapp.collection.model.CollectBmobCallBack;
import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;
import com.sicnu.personal.knowledgesharingapp.collection.model.datasource.CollectResponse;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class CollectHanldPresenter implements CollectContact.CollectPresenter {
    private CollectDataBean data;
    private Context context;
    private CollectResponse response;
    private CollectContact.CollectView baseView;
    public CollectHanldPresenter(Context context,CollectContact.CollectView view){
        this.context =context;
        this.baseView = view;
        response = new CollectResponse(context);
    }


    @Override
    public void quertUserCollectDatasFromBmob(String userName) {
        response.queryCollectDataFromBmob(userName, new CollectBmobCallBack.QueryCollectCallBack() {
            @Override
            public void queryCollectionSuccessful(List<CollectDataBean> dataBeen) {
                baseView.showQueryCollectDatasPage(dataBeen);
            }

            @Override
            public void queryCollectFailed(int code, String reason) {
                baseView.showQueryErrorPage(code,reason);
            }

        });
    }

    @Override
    public void addCollectToBmob(CollectDataBean dataBean) {
        response.addCollectDataToBmob(dataBean, new CollectBmobCallBack.CollectCallBack() {
            @Override
            public void theCollectDataIsExited() {
                baseView.showCollectDataIsExitedPage();
            }

            @Override
            public void collectSuccessful() {
                baseView.showCollectSuccessfulPage();
            }

            @Override
            public void collectFailed(int code, String reaon) {
                baseView.showCollectErrorPage(code,reaon);
            }

            @Override
            public void queryCollectFailed(int code, String reason) {
                baseView.showQueryErrorPage(code,reason);
            }
        });
    }


}
