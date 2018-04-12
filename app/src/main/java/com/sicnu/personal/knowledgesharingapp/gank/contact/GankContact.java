package com.sicnu.personal.knowledgesharingapp.gank.contact;

import com.sicnu.personal.knowledgesharingapp.base.basepresenter.BasePresenter;
import com.sicnu.personal.knowledgesharingapp.base.baseview.BaseView;
import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;


/**
 * Created by Administrator on 2018/3/6 0006.
 */

public interface GankContact {
    interface GankView extends BaseView{
        void showRefreshPage(GankDataBean dataBean);
        void showLoadMorePage(GankDataBean dataBean);
        void showErrorPage(Throwable throwable);
        void showDataPage();
    }
    interface GankPresenter extends BasePresenter{
        void getGankRemoteData(String type, int count, int page, boolean isRefresh);
        void firstRequstData(String type);
    }
}
