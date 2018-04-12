package com.sicnu.personal.knowledgesharingapp.pretty.contact;

import com.sicnu.personal.knowledgesharingapp.base.basepresenter.BasePresenter;
import com.sicnu.personal.knowledgesharingapp.base.baseview.BaseView;

import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public interface PrettyPictureContact {
    interface PrettyView extends BaseView{
        void showRefreshPage(PrettyDataBean dataBean);
        void showLoadMorePage(PrettyDataBean dataBean);
        void showErrorPage(Throwable e);
        void showDataPage();
    }
    interface PrettyPresenter extends BasePresenter{
        void getPrettyRemoteData(int page,boolean isRefresh);
        void firstRequstData(int page);
    }
}
