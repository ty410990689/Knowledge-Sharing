package com.sicnu.personal.knowledgesharingapp.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.home.adapter.KnowledgeRcAdapter;
import com.sicnu.personal.knowledgesharingapp.home.contact.GankContact;
import com.sicnu.personal.knowledgesharingapp.home.model.databean.GankKnowledgeDataBean;
import com.sicnu.personal.knowledgesharingapp.home.presenter.GankRemotePresenter;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class IosFragment extends Fragment implements GankContact.GankView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rc_home_main_item)
    RecyclerView rcHomeMainItem;
    Unbinder unbinder;
    KnowledgeRcAdapter mAdapter;
    ArrayList<GankKnowledgeDataBean.ResultsBean> mDataBean;
    GankRemotePresenter mPresenter;


    @BindView(R.id.swl_knowledge_home)
    SwipeRefreshLayout swlKnowledgeHome;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        mDataBean = new ArrayList<>();
        mAdapter = new KnowledgeRcAdapter(getActivity(), mDataBean);
        mPresenter = new GankRemotePresenter(this);
        mPresenter.firstRequstData("iOS");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcHomeMainItem.setLayoutManager(layoutManager);
        rcHomeMainItem.setAdapter(mAdapter);
        swlKnowledgeHome.setRefreshing(false);
        swlKnowledgeHome.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showRefreshPage(GankKnowledgeDataBean dataBean) {
        if(!dataBean.isError()){
            swlKnowledgeHome.setRefreshing(false);
            List<GankKnowledgeDataBean.ResultsBean> data = dataBean.getResults();
            mAdapter.refreshData(data);
        }
    }

    @Override
    public void showLoadMorePage(GankKnowledgeDataBean dataBean) {
        if (!dataBean.isError()) {
            List<GankKnowledgeDataBean.ResultsBean> data = dataBean.getResults();
            mAdapter.loadMoreData(data);
        }
    }

    @Override
    public void showErrorPage() {
        YLog.d("Error Page");

    }

    @Override
    public void showDataPage() {
        YLog.d("showDataPage ");

    }
    //刷新
    @Override
    public void onRefresh() {
        swlKnowledgeHome.setRefreshing(true);
        mPresenter.getGankRemoteData("iOS",10,1,true);
    }
}
