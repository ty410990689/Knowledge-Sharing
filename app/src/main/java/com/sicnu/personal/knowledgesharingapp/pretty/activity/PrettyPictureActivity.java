package com.sicnu.personal.knowledgesharingapp.pretty.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.pretty.adapter.PrettyRcAdapter;
import com.sicnu.personal.knowledgesharingapp.pretty.contact.PrettyPictureContact;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.pretty.presenter.PrettyPicturePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class PrettyPictureActivity extends AppCompatActivity implements PrettyPictureContact.PrettyView {
    @BindView(R.id.toolbar_pretty)
    Toolbar toolbarPretty;
    @BindView(R.id.rc_pretty_picture)
    RecyclerView rcPrettyPicture;
    @BindView(R.id.iv_pretty_back)
    ImageView ivPrettyBack;
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
        // setSupportActionBar(toolbarPretty);
        data = new ArrayList<>();
        prettyPicturePresenter = new PrettyPicturePresenter(this);
        adapter = new PrettyRcAdapter(this, data);
        rcPrettyPicture.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcPrettyPicture.setAdapter(adapter);
        prettyPicturePresenter.firstRequstData(1);
    }

    @OnClick(R.id.iv_pretty_back)
    public void setOnClickListener(View view){
        switch (view.getId()){
            case R.id.iv_pretty_back:
                this.finish();
                break;
        }
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
