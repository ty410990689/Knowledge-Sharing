package com.sicnu.personal.knowledgesharingapp.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.home.activity.WebActivity;
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

public class AndroidFragment extends Fragment implements GankContact.GankView, SwipeRefreshLayout.OnRefreshListener, KnowledgeRcAdapter.KnowledgeClickListenter {
    @BindView(R.id.rc_home_main_item)
    RecyclerView rcHomeMainItem;
    Unbinder unbinder;
    KnowledgeRcAdapter mAdapter;
    List<GankKnowledgeDataBean.ResultsBean> mDataBean;
    GankRemotePresenter mPresenter;
    private GridLayoutManager manager;

    @BindView(R.id.swl_knowledge_home)
    SwipeRefreshLayout swlKnowledgeHome;
    private  int lastVisiblePostion = 0;
    private  int page = 1;
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
        mPresenter.firstRequstData("Android");
        manager = new GridLayoutManager(getActivity(),1);
        rcHomeMainItem.setLayoutManager(manager);
        mAdapter.setItemClickListener(this);
        rcHomeMainItem.setAdapter(mAdapter);
        swlKnowledgeHome.setRefreshing(false);
        swlKnowledgeHome.setOnRefreshListener(this);
        rcHomeMainItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                    YLog.d("last :"+lastVisiblePostion);
                    YLog.d("count :"+mAdapter.getItemCount());
                    if(mAdapter.isFade()==false&&lastVisiblePostion+1==mAdapter.getItemCount()){
                        //加载数据
                        mPresenter.getGankRemoteData("Android",Constant.GANK_KNOWLEDGE_COUNT,page,false);
                    }
                    if(mAdapter.isFade()==true&&lastVisiblePostion==mAdapter.getItemCount()){
                        //加载数据
                        mPresenter.getGankRemoteData("Android",Constant.GANK_KNOWLEDGE_COUNT,page,false);
                    }
                    YLog.d("PPP_lastVisiblePostion: "+lastVisiblePostion);
                    YLog.d("PPP_getItemCount : "+mAdapter.getItemCount());
                    YLog.d("PPP_isFade : "+mAdapter.isFade());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisiblePostion = manager.findLastVisibleItemPosition();
            }
        });
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
            page=1;
            swlKnowledgeHome.setRefreshing(false);
            List<GankKnowledgeDataBean.ResultsBean> data = dataBean.getResults();
            mAdapter.refreshData(data);
        }
    }

    @Override
    public void showLoadMorePage(GankKnowledgeDataBean dataBean) {
        if (!dataBean.isError()) {
            YLog.d("LoadMore");
            page+=1;
            List<GankKnowledgeDataBean.ResultsBean> data = dataBean.getResults();
            if (data!=null && data.size()>0){
                mDataBean.addAll(data);
            }
            mAdapter.loadMoreData(data);
        }
    }

    @Override
    public void showErrorPage(Throwable throwable) {
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
        mPresenter.getGankRemoteData("Android",10,1,true);
    }

    @Override
    public void onKnowledgeClickListener(View view, int pos) {
        Intent intent = new Intent(getActivity(),WebActivity.class);
        intent.putExtra(Constant.INTENT_WEB_URL,mDataBean.get(pos).getUrl());
        startActivity(intent);
    }

    @Override
    public void onKnowledgeLongClickListener(View view, int pos) {

    }
}
