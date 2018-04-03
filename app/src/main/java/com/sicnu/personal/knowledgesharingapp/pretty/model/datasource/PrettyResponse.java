package com.sicnu.personal.knowledgesharingapp.pretty.model.datasource;

import com.sicnu.personal.knowledgesharingapp.pretty.model.datasource.remote.PrettyRemoteDataSource;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class PrettyResponse implements PrettyDataSource {
    private PrettyRemoteDataSource prettyRemoteDataSource;
    public PrettyResponse(){
        prettyRemoteDataSource = new PrettyRemoteDataSource();
    }
    @Override
    public void getRemotePrettyData(int page, PrettyDataCallBack callBack) {
        prettyRemoteDataSource.getRemotePrettyData(page,callBack);
    }
}
