package com.sicnu.personal.knowledgesharingapp.home.contact;

import com.sicnu.personal.knowledgesharingapp.base.basepresenter.BasePresenter;
import com.sicnu.personal.knowledgesharingapp.base.baseview.BaseView;
import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public interface GankContact {
    interface GankView extends BaseView{
        void showRefreshPage(GankKnowledgeDataBean dataBean);
        void showLoadMorePage(GankKnowledgeDataBean dataBean);
        void showErrorPage();
        void showDataPage();
    }
    interface GankPresenter extends BasePresenter{
        void getGankRemoteData(String type,int count,int page,boolean isRefresh);
        void firstRequstData(String type);
    }
}
