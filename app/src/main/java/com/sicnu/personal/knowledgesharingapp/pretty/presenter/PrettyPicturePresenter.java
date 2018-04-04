package com.sicnu.personal.knowledgesharingapp.pretty.presenter;

import com.sicnu.personal.knowledgesharingapp.pretty.contact.PrettyPictureContact;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.pretty.model.datasource.PrettyDataSource;
import com.sicnu.personal.knowledgesharingapp.pretty.model.datasource.PrettyResponse;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public class PrettyPicturePresenter implements PrettyPictureContact.PrettyPresenter {
    PrettyPictureContact.PrettyView prettyView;
    PrettyResponse response;
    public PrettyPicturePresenter(PrettyPictureContact.PrettyView view){
        this.prettyView = view;
        response = new PrettyResponse();
    }
    @Override
    public void getPrettyRemoteData(int page, final boolean isRefresh) {
        response.getRemotePrettyData(page, new PrettyDataSource.PrettyDataCallBack() {
            @Override
            public void onLoadedSuccessful(PrettyDataBean prettyDataBean) {
                if(isRefresh){
                    prettyView.showRefreshPage(prettyDataBean);
                }else{
                    prettyView.showLoadMorePage(prettyDataBean);
                }
                prettyView.showDataPage();
            }

            @Override
            public void onLoadedFailed(Throwable throwable) {
                prettyView.showErrorPage(throwable);
            }
        });
    }

    @Override
    public void firstRequstData(int page) {
        getPrettyRemoteData(1,false);
    }
}
