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
    private CollectContact.CollectDataView collectDataView;
    private CollectContact.DeleteDataView deleteDataView;
    private CollectContact.QueryDataView queryDataView;
    public CollectHanldPresenter(Context context){
        this.context =context;
        response = new CollectResponse(context);
    }


    @Override
    public void quertUserCollectDatasFromBmob(String userName, final CollectContact.QueryDataView queryDataView) {
        if(this.queryDataView==null) {
            this.queryDataView = queryDataView;
        }
        response.queryCollectDataFromBmob(userName, new CollectBmobCallBack.QueryCollectCallBack() {
            @Override
            public void queryCollectionSuccessful(List<CollectDataBean> dataBeen) {
                queryDataView.showQueryCollectDatasPage(dataBeen);
            }

            @Override
            public void queryCollectFailed(int code, String reason) {
                queryDataView.showQueryErrorPage(code,reason);
            }

        });
    }

    @Override
    public void addCollectToBmob(CollectDataBean dataBean, final CollectContact.CollectDataView dataView) {
        if(this.collectDataView==null){
            this.collectDataView = dataView;
        }
        response.addCollectDataToBmob(dataBean, new CollectBmobCallBack.CollectCallBack() {
            @Override
            public void theCollectDataIsExisted() {
                dataView.showCollectDataIsExistedPage();
            }

            @Override
            public void collectSuccessful() {
                dataView.showCollectSuccessfulPage();
            }

            @Override
            public void collectFailed(int code, String reaon) {
                dataView.showCollectErrorPage(code,reaon);
            }

            @Override
            public void queryCollectFailed(int code, String reason) {
                dataView.showQueryErrorPage(code,reason);
            }
        });
    }

    public void deleteCollectDataForBmob(CollectDataBean dataBean, final CollectContact.DeleteDataView dataView){
        if(this.deleteDataView==null){
            this.deleteDataView =dataView;
        }
        response.deleteCollectDataForBmob(dataBean, new CollectBmobCallBack.DeleteCollectCallBack() {
            @Override
            public void deleteCollectSuccessful() {
                dataView.showDeleteCollectSuccessfulPage();
            }

            @Override
            public void deleteCollectFailed(int code, String reason) {
                dataView.showDeleteCollectFailedPage(code,reason);
            }
        });
    }

}
