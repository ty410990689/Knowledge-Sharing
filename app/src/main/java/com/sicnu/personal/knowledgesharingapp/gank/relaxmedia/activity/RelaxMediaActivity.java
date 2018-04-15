package com.sicnu.personal.knowledgesharingapp.gank.relaxmedia.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.gank.contact.GankContact;
import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;
import com.sicnu.personal.knowledgesharingapp.gank.presenter.GankRemotePresenter;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/12 0012.
 */

public class RelaxMediaActivity extends Activity implements GankContact.GankView {

    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_toolbar_save)
    ImageView ivToolbarSave;
    @BindView(R.id.toolbar_pretty)
    Toolbar toolbarPretty;
    @BindView(R.id.rc_relax_media)
    RecyclerView rcRelaxMedia;
    private GankRemotePresenter gankRemotePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_media);
        ButterKnife.bind(this);
        gankRemotePresenter = new GankRemotePresenter(this);
        gankRemotePresenter.firstRequstData("休息视频");
        initToolBar();
    }

    private void initToolBar() {
        tvToolbarTitle.setText(getString(R.string.relax_media));
        ivToolbarSave.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshPage(GankDataBean dataBean) {

    }

    @Override
    public void showLoadMorePage(GankDataBean dataBean) {
        YLog.d("MediaData : " + dataBean.getResults().get(0).getDesc());
    }

    @Override
    public void showErrorPage(Throwable throwable) {

    }

    @Override
    public void showDataPage() {

    }
}
