package com.sicnu.personal.knowledgesharingapp.gank.relaxmedia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentFrameLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.collection.contact.CollectContact;
import com.sicnu.personal.knowledgesharingapp.collection.model.databean.CollectDataBean;
import com.sicnu.personal.knowledgesharingapp.collection.presenter.CollectHanldPresenter;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.gank.contact.GankContact;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity.WebActivity;
import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;
import com.sicnu.personal.knowledgesharingapp.gank.presenter.GankRemotePresenter;
import com.sicnu.personal.knowledgesharingapp.gank.relaxmedia.adapter.RelaxMediaRcAdapter;
import com.sicnu.personal.knowledgesharingapp.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/4/12 0012.
 */

public class RelaxMediaActivity extends Activity implements GankContact.GankView, SwipeRefreshLayout.OnRefreshListener, RelaxMediaRcAdapter.KnowledgeClickListenter, CollectContact.CollectView {

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
    @BindView(R.id.fl_toolbar)
    PercentFrameLayout flToolbar;
    @BindView(R.id.dl_rootview)
    DrawerLayout dlRootview;
    private GankRemotePresenter gankRemotePresenter;
    RelaxMediaRcAdapter mediaRcAdapter;
    List<GankDataBean.ResultsBean> mData;
    GridLayoutManager manager;
    private int lastVisiblePostion = 0;
    private int page = 1;
    CollectHanldPresenter presenter;

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
        presenter = new CollectHanldPresenter(this, this);
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
        gankRemotePresenter.getGankRemoteData(getString(R.string.relax_media), 10, 0, true);
        swlRelaxmedia.setRefreshing(true);
    }

    @Override
    public void onKnowledgeClickListener(View view, int pos) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(Constant.INTENT_WEB_URL, mData.get(pos).getUrl());
        intent.putExtra(Constant.INTENT_WEB_TYPE, Constant.INTENT_WEB_VIDEO_TYPE);
        startActivity(intent);
    }

    @Override
    public void onKnowledgeLongClickListener(View view, int pos) {
        CollectDataBean dataBean = new CollectDataBean();
        dataBean.setArticleType("Media");
        dataBean.setArticleDesc(mData.get(pos).getDesc());
        dataBean.setArticleLink(mData.get(pos).getUrl());
        dataBean.setUserName(BmobUser.getCurrentUser(this).getUsername());
        dataBean.setArticleMold("RelaxMedia");
        if (mData.get(pos).getWho() != null) {
            dataBean.setArticleAuthor(mData.get(pos).getWho().toString());
        } else {
            dataBean.setArticleAuthor("UnKnown");
        }
        CommonUtils.showFunctionDialog(this, presenter, dataBean);
    }

    @Override
    public void showQueryCollectDatasPage(List<CollectDataBean> dataBeen) {
    }

    @Override
    public void showCollectDataIsExitedPage() {
        Snackbar.make(rcRelaxMedia,"已经存在",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showCollectSuccessfulPage() {
        Snackbar.make(rcRelaxMedia,"收藏成功",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showCollectErrorPage(int code, String reason) {
        Snackbar.make(rcRelaxMedia,"收藏失败",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showQueryErrorPage(int code, String readson) {
        Snackbar.make(rcRelaxMedia,"异常错误",Snackbar.LENGTH_SHORT).show();
    }
}
