package com.sicnu.personal.knowledgesharingapp.gank.relaxmedia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.gank.contact.GankContact;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity.WebActivity;
import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;
import com.sicnu.personal.knowledgesharingapp.gank.presenter.GankRemotePresenter;
import com.sicnu.personal.knowledgesharingapp.gank.relaxmedia.adapter.RelaxMediaRcAdapter;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/12 0012.
 */

public class RelaxMediaActivity extends Activity implements GankContact.GankView, SwipeRefreshLayout.OnRefreshListener, RelaxMediaRcAdapter.KnowledgeClickListenter {

    @BindView(R.id.iv_toolbar_back)
    public ImageView ivToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    public TextView tvToolbarTitle;
    @BindView(R.id.iv_toolbar_save)
    public ImageView ivToolbarSave;
    @BindView(R.id.toolbar_pretty)
    Toolbar toolbarPretty;
    @BindView(R.id.rc_relax_media)
    RecyclerView rcRelaxMedia;
    @BindView(R.id.swl_relaxmedia)
    SwipeRefreshLayout swlRelaxmedia;
    private GankRemotePresenter gankRemotePresenter;
    RelaxMediaRcAdapter mediaRcAdapter;
    List<GankDataBean.ResultsBean> mData;
    GridLayoutManager manager;
    private int lastVisiblePostion = 0;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_media);
        ButterKnife.bind(this);
        gankRemotePresenter = new GankRemotePresenter(this);
        gankRemotePresenter.firstRequstData("休息视频");
        initToolBar();
        manager = new GridLayoutManager(this, 1);
        mData = new ArrayList<>();
        mediaRcAdapter = new RelaxMediaRcAdapter(this, mData);
        rcRelaxMedia.setLayoutManager(manager);
        rcRelaxMedia.setAdapter(mediaRcAdapter);
        swlRelaxmedia.setOnRefreshListener(this);
        rcRelaxMedia.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mediaRcAdapter.isFade() == false && lastVisiblePostion + 1 == mediaRcAdapter.getItemCount()) {
                        //加载数据
                        gankRemotePresenter.getGankRemoteData(getString(R.string.relax_media), Constant.GANK_KNOWLEDGE_COUNT, page, false);
                    }
                    if (mediaRcAdapter.isFade() == true && lastVisiblePostion == mediaRcAdapter.getItemCount()) {
                        //加载数据
                        gankRemotePresenter.getGankRemoteData(getString(R.string.relax_media), Constant.GANK_KNOWLEDGE_COUNT, page, false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisiblePostion = manager.findLastVisibleItemPosition();
            }
        });
        mediaRcAdapter.setItemClickListener(this);
    }

    private void initToolBar() {
        tvToolbarTitle.setText(getString(R.string.relax_media));
        ivToolbarSave.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshPage(GankDataBean dataBean) {
        page = 0;
        swlRelaxmedia.setRefreshing(false);
        mediaRcAdapter.refreshData(dataBean.getResults());
    }

    @Override
    public void showLoadMorePage(GankDataBean dataBean) {
        mediaRcAdapter.loadMoreData(dataBean.getResults());
        page += 1;
    }

    @Override
    public void showErrorPage(Throwable throwable) {

    }

    @Override
    public void showDataPage() {

    }

    @Override
    public void onRefresh() {
        gankRemotePresenter.getGankRemoteData(getString(R.string.relax_media),10,0,true);
        swlRelaxmedia.setRefreshing(true);
    }

    @Override
    public void onKnowledgeClickListener(View view, int pos) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(Constant.INTENT_WEB_URL,mData.get(pos).getUrl());
        intent.putExtra(Constant.INTENT_WEB_TYPE,Constant.INTENT_WEB_VIDEO_TYPE);
        startActivity(intent);
    }

    @Override
    public void onKnowledgeLongClickListener(View view, int pos) {

    }
}
