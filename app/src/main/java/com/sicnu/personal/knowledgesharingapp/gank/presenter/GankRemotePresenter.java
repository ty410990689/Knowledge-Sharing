package com.sicnu.personal.knowledgesharingapp.gank.presenter;

import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.gank.contact.GankContact;
import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;
import com.sicnu.personal.knowledgesharingapp.gank.model.datasource.GankDataSource;
import com.sicnu.personal.knowledgesharingapp.gank.model.datasource.GankResponse;


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
           public void onLoadedSuccessful(GankDataBean gankDataBean) {
               if (isRefresh){
                   gankView.showRefreshPage(gankDataBean);
               }else{
                   gankView.showLoadMorePage(gankDataBean);
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
        getGankRemoteData(type, Constant.GANK_KNOWLEDGE_COUNT,1,false);
    }

}
