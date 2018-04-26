package com.sicnu.personal.knowledgesharingapp.collection.contact;

import com.sicnu.personal.knowledgesharingapp.base.basepresenter.BasePresenter;
import com.sicnu.personal.knowledgesharingapp.base.baseview.BaseView;
import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public interface CollectContact {
    public interface CollectView extends BaseView{
        void showQueryCollectDatasPage(List<CollectDataBean> dataBeen);
        void showCollectDataIsExitedPage();
        void showCollectSuccessfulPage();
        void showCollectErrorPage(int code,String reason);
        void showQueryErrorPage(int code,String readson);
    }

    public interface CollectPresenter extends BasePresenter{
        void quertUserCollectDatasFromBmob(String userName);
        void addCollectToBmob(CollectDataBean dataBean);
    }
}
