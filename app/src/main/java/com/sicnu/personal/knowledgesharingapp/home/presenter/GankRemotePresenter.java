package com.sicnu.personal.knowledgesharingapp.home.presenter;

import com.sicnu.personal.knowledgesharingapp.home.contact.GankContact;
import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;
import com.sicnu.personal.knowledgesharingapp.home.model.datasource.GankDataSource;
import com.sicnu.personal.knowledgesharingapp.home.model.datasource.GankResponse;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class GankRemotePresenter implements GankContact.GankPresenter {
    GankContact.GankView gankView ;
    GankResponse gankResponse;
    public GankRemotePresenter(GankContact.GankView view){
        this.gankView = view;
        gankResponse = new GankResponse();
    }
    @Override
    public void getGankRemoteData(String type,int count, int page, final boolean isRefresh) {
       gankResponse.getRemoteGankData(type,count, page, new GankDataSource.GankDataCallBack() {
           @Override
           public void onLoadedSuccessful(GankKnowledgeDataBean gankKnowledgeDataBean) {
               if (isRefresh){
                   gankView.showRefreshPage(gankKnowledgeDataBean);
               }else{
                   gankView.showLoadMorePage(gankKnowledgeDataBean);
               }
               gankView.showDataPage();
           }

           @Override
           public void onLoadedfailed() {
                gankView.showErrorPage();
           }
       });
    }

    @Override
    public void firstRequstData() {

    }

    @Override
    public void getMessageData() {

    }
}
