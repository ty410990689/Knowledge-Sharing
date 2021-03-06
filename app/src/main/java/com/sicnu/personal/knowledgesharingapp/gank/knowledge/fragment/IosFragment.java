package com.sicnu.personal.knowledgesharingapp.gank.knowledge.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.collection.contact.CollectContact;
import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;
import com.sicnu.personal.knowledgesharingapp.collection.presenter.CollectHanldPresenter;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.gank.contact.GankContact;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity.WebActivity;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.adapter.KnowledgeRcAdapter;
import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;
import com.sicnu.personal.knowledgesharingapp.gank.presenter.GankRemotePresenter;
import com.sicnu.personal.knowledgesharingapp.utils.CommonUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;

;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class IosFragment extends Fragment implements GankContact.GankView, SwipeRefreshLayout.OnRefreshListener, KnowledgeRcAdapter.KnowledgeClickListenter, CollectContact.CollectDataView {
    @BindView(R.id.rc_home_main_item)
    RecyclerView rcHomeMainItem;
    Unbinder unbinder;
    KnowledgeRcAdapter mAdapter;
    List<GankDataBean.ResultsBean> mDataBean;
    GankRemotePresenter mPresenter;
    @BindView(R.id.fl_rootview)
    FrameLayout flRootview;
    @BindView(R.id.view_stub)
    ViewStub viewStub;
    TextView tvErrorContent;
    ImageView ivErrorIcon;
    private int lastVisiblePostion = 0;
    private int page = 1;
    @BindView(R.id.swl_knowledge_home)
    SwipeRefreshLayout swlKnowledgeHome;

    GridLayoutManager manager;
    private CollectHanldPresenter collectPresenter;

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
        mPresenter.firstRequstData(getString(R.string.knowledge_ios));
        manager = new GridLayoutManager(getActivity(), 1);
        rcHomeMainItem.setLayoutManager(manager);
        rcHomeMainItem.setAdapter(mAdapter);
        mAdapter.setItemClickListener(this);
        swlKnowledgeHome.setRefreshing(false);
        swlKnowledgeHome.setOnRefreshListener(this);
        collectPresenter = new CollectHanldPresenter(getActivity());
        rcHomeMainItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mAdapter.isFade() == false && lastVisiblePostion + 1 == mAdapter.getItemCount()) {
                        //加载数据
                        mPresenter.getGankRemoteData(getString(R.string.knowledge_ios), Constant.GANK_KNOWLEDGE_COUNT, page, false);
                    }
                    if (mAdapter.isFade() == true && lastVisiblePostion == mAdapter.getItemCount()) {
                        //加载数据
                        mPresenter.getGankRemoteData(getString(R.string.knowledge_ios), Constant.GANK_KNOWLEDGE_COUNT, page, false);
                    }
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
    public void showRefreshPage(GankDataBean dataBean) {
        if (!dataBean.isError()) {
            page = 2;
            swlKnowledgeHome.setRefreshing(false);
            List<GankDataBean.ResultsBean> data = dataBean.getResults();
            mAdapter.refreshData(data);
        }
    }

    @Override
    public void showLoadMorePage(GankDataBean dataBean) {
        if (!dataBean.isError()) {
            page += 1;
            List<GankDataBean.ResultsBean> data = dataBean.getResults();
            mAdapter.loadMoreData(data);
        }
    }

    @Override
    public void showErrorPage(Throwable throwable) {
        showViewStubPage(Constant.SHOW_ERROR_PAGE);
    }

    @Override
    public void showDataPage() {
        if(viewStub.getVisibility()==View.VISIBLE){
            viewStub.setVisibility(View.GONE);
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        swlKnowledgeHome.setRefreshing(true);
        mPresenter.getGankRemoteData(getString(R.string.knowledge_ios), 10, 1, true);
    }

    @Override
    public void onKnowledgeClickListener(View view, int pos) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(Constant.INTENT_WEB_URL, mDataBean.get(pos).getUrl());
        intent.putExtra(Constant.INTENT_WEB_TYPE, Constant.INTENT_WEB_KNOWLEDGE_TYPE);
        startActivity(intent);
    }

    @Override
    public void onKnowledgeLongClickListener(View view, int pos) {
        CollectDataBean dataBean = new CollectDataBean();
        dataBean.setUserName(BmobUser.getCurrentUser(getActivity()).getUsername());
        dataBean.setArticleDesc(mDataBean.get(pos).getDesc());
        dataBean.setArticleLink(mDataBean.get(pos).getUrl());
        dataBean.setArticleMold(getString(R.string.knownledge_api_mold));
        if (mDataBean.get(pos).getWho() != null) {
            dataBean.setArticleAuthor(mDataBean.get(pos).getWho().toString());
        } else {
            dataBean.setArticleAuthor(getString(R.string.author_unknown));
        }
        dataBean.setArticleTitle(mDataBean.get(pos).getDesc());
        dataBean.setArticleType(getString(R.string.knowledge_ios));
        CommonUtils.showFunctionDialog(getActivity(), collectPresenter, this,dataBean);
    }




    public void showCollectDataIsExitedPage() {
        Snackbar.make(flRootview, getString(R.string.collect_data_is_exited), Snackbar.LENGTH_SHORT).show();
    }


    public void showCollectSuccessfulPage() {
        Snackbar.make(flRootview, getString(R.string.collect_successful), Snackbar.LENGTH_SHORT).show();
    }


    public void showCollectErrorPage(int code, String reason) {
        Snackbar.make(flRootview, getString(R.string.collect_failed) + reason, Snackbar.LENGTH_SHORT).show();
    }


    public void showQueryErrorPage(int code, String reason) {
        Snackbar.make(flRootview, getString(R.string.collect_error) + reason, Snackbar.LENGTH_SHORT).show();
    }


    public void showViewStubPage(int type){
        View ivStubView = viewStub.inflate();
        tvErrorContent = ivStubView.findViewById(R.id.tv_warning_content);
        ivErrorIcon = ivStubView.findViewById(R.id.iv_error_icon);

        if(type== Constant.QUERY_ERROR){
            tvErrorContent.setText(getResources().getString(R.string.query_collect_datas_is_null));
            ivErrorIcon.setImageResource(R.mipmap.icon_error_blue);
        }else if(type==Constant.SHOW_ERROR_PAGE)
        {
            tvErrorContent.setText(getResources().getString(R.string.error_page_tip));
            ivErrorIcon.setImageResource(R.mipmap.icon_red_error);
        }
        viewStub.setVisibility(View.VISIBLE);
    }
}

