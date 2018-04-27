package com.sicnu.personal.knowledgesharingapp.collection.contact;

import com.sicnu.personal.knowledgesharingapp.base.basepresenter.BasePresenter;
import com.sicnu.personal.knowledgesharingapp.base.baseview.BaseView;
import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public interface CollectContact {
    interface CollectDataView extends BaseView {

        void showCollectDataIsExitedPage();
        void showQueryErrorPage(int code,String reason);
        void showCollectSuccessfulPage();

        void showCollectErrorPage(int code, String reason);


    }

    interface DeleteDataView extends BaseView {
        void showDeleteCollectFailedPage(int code, String reason);

        void showDeleteCollectSuccessfulPage();
    }

    interface QueryDataView extends BaseView {
        void showQueryCollectDatasPage(List<CollectDataBean> dataBeen);

        void showQueryErrorPage(int code, String reason);
    }

    interface CollectPresenter extends BasePresenter {
        void quertUserCollectDatasFromBmob(String userName, QueryDataView queryDataView);

        void addCollectToBmob(CollectDataBean dataBean, CollectDataView collectDataView);

        void deleteCollectDataForBmob(CollectDataBean dataBean, DeleteDataView deleteDataView);
    }
}
