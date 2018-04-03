package com.sicnu.personal.knowledgesharingapp.home.model.datasource.remote;

import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;
import com.sicnu.personal.knowledgesharingapp.home.model.datasource.GankDataSource;
import com.sicnu.personal.knowledgesharingapp.net.RetrofitClient;
import com.sicnu.personal.knowledgesharingapp.net.ServerApi;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class GankRemoteDataSource implements GankDataSource {
    @Override
    public void getRemoteGankData(String type, int count, int page, final GankDataCallBack dataCallBack) {
        RetrofitClient.getInstance().createReq(ServerApi.class)
                .getGankKnowledgeData(type,count,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankKnowledgeDataBean>() {
                    @Override
                    public void onCompleted() {
                        YLog.d("Gank Datas is onCompleted : ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        YLog.d("Gank Datas is error : "+e.getMessage());
                        dataCallBack.onLoadedfailed(e);
                    }

                    @Override
                    public void onNext(GankKnowledgeDataBean gankKnowledgeDataBean) {
                        dataCallBack.onLoadedSuccessful(gankKnowledgeDataBean);
                        YLog.d("Gank Datas is OK : "+gankKnowledgeDataBean.getResults().size());
                    }
                });
    }
}
