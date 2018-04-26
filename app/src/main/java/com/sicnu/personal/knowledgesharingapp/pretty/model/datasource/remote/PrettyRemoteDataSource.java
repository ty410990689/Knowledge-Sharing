package com.sicnu.personal.knowledgesharingapp.pretty.model.datasource.remote;

import com.sicnu.personal.knowledgesharingapp.net.RetrofitClient;
import com.sicnu.personal.knowledgesharingapp.net.ServerApi;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.pretty.model.datasource.PrettyDataSource;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class PrettyRemoteDataSource implements PrettyDataSource {
    @Override
    public void getRemotePrettyData(int page, final PrettyDataCallBack callBack) {
        String requestPage = page+".cfg";
        RetrofitClient.getInstance().createReq(ServerApi.class)
                .getPrettyPictureData(requestPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrettyDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        callBack.onLoadedFailed(e);
                    }

                    @Override
                    public void onNext(PrettyDataBean prettyDataBean) {

                        callBack.onLoadedSuccessful(prettyDataBean);
                    }
                });

    }
}
