package com.sicnu.personal.knowledgesharingapp.pretty.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.pretty.adapter.PrettyRcAdapter;
import com.sicnu.personal.knowledgesharingapp.pretty.contact.PrettyPictureContact;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.pretty.model.datasource.PrettyDataSource;
import com.sicnu.personal.knowledgesharingapp.pretty.model.datasource.PrettyResponse;
import com.sicnu.personal.knowledgesharingapp.pretty.presenter.PrettyPicturePresenter;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class PrettyPictureActivity extends Activity implements PrettyPictureContact.PrettyView {
    @BindView(R.id.toolbar_pretty)
    Toolbar toolbarPretty;
    @BindView(R.id.rc_pretty_picture)
    RecyclerView rcPrettyPicture;
    private List<PrettyDataBean.ResultsBean> data;
    private PrettyPicturePresenter prettyPicturePresenter;
    private PrettyRcAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_picture);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        data = new ArrayList<>();
        prettyPicturePresenter = new PrettyPicturePresenter(this);
        adapter = new PrettyRcAdapter(this,data);
        rcPrettyPicture.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rcPrettyPicture.setAdapter(adapter);
        prettyPicturePresenter.firstRequstData(1);
    }

    @Override
    public void showRefreshPage(PrettyDataBean dataBean) {
        adapter.onRefreshData(dataBean.getResults());
    }

    @Override
    public void showLoadMorePage(PrettyDataBean dataBean) {
        adapter.onLoadMoreData(dataBean.getResults());
    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showDataPage() {

    }
}
