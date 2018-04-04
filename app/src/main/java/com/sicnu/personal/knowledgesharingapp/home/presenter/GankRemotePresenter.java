package com.sicnu.personal.knowledgesharingapp.home.presenter;

import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.home.contact.GankContact;
import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;
import com.sicnu.personal.knowledgesharingapp.home.model.datasource.GankDataSource;
import com.sicnu.personal.knowledgesharingapp.home.model.datasource.GankResponse;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

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
               YLog.d("Here showLoadMorePage");
               if (isRefresh){
                   gankView.showRefreshPage(gankKnowledgeDataBean);
               }else{
                   YLog.d("ASDDSA : "+gankKnowledgeDataBean.getResults().get(0).getDesc());
                   gankView.showLoadMorePage(gankKnowledgeDataBean);
               }
               gankView.showDataPage();
           }

           @Override
           public void onLoadedfailed(Throwable errorMessage) {
                gankView.showErrorPage(errorMessage);
           }
       });
    }

    @Override
    public void firstRequstData(String type) {
        YLog.d("firstRequstData");
        getGankRemoteData(type, Constant.GANK_KNOWLEDGE_COUNT,1,false);
    }

}
