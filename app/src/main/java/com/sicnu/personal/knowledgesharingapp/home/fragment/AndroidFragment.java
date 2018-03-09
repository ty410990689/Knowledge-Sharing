package com.sicnu.personal.knowledgesharingapp.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;
import com.sicnu.personal.knowledgesharingapp.home.model.datasource.GankDataSource;
import com.sicnu.personal.knowledgesharingapp.home.model.datasource.GankResponse;
import com.sicnu.personal.knowledgesharingapp.home.presenter.GankRemotePresenter;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class AndroidFragment extends Fragment {
    GankResponse response;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_main,container,false);
        response = new GankResponse();
        response.getRemoteGankData("Android", 10, 1, new GankDataSource.GankDataCallBack() {
            @Override
            public void onLoadedSuccessful(GankKnowledgeDataBean gankKnowledgeDataBean) {
                YLog.d("data is success : "+gankKnowledgeDataBean.isError());
            }

            @Override
            public void onLoadedfailed(Throwable errorMessage) {
                YLog.d("data is failed");
            }
        });
        return view;
    }
}
