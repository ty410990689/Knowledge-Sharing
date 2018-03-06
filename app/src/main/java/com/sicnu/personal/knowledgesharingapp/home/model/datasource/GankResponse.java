package com.sicnu.personal.knowledgesharingapp.home.model.datasource;

import com.sicnu.personal.knowledgesharingapp.home.model.datasource.remote.GankRemoteDataSource;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class GankResponse implements GankDataSource{
    GankRemoteDataSource remoteDataSource;

    public GankResponse(){
        remoteDataSource = new GankRemoteDataSource();
    }
    @Override
    public void getRemoteGankData(String type, int count, int page, GankDataCallBack dataCallBack) {
        remoteDataSource.getRemoteGankData(type,count,page,dataCallBack);
    }
}
