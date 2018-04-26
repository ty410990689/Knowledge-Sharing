package com.sicnu.personal.knowledgesharingapp.pretty.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.pretty.adapter.PrettyRcAdapter;
import com.sicnu.personal.knowledgesharingapp.pretty.contact.PrettyPictureContact;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.pretty.presenter.PrettyPicturePresenter;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class PrettyPictureActivity extends AppCompatActivity implements PrettyPictureContact.PrettyView, PrettyRcAdapter.PrettyClickListener {
    @BindView(R.id.toolbar_pretty)
    Toolbar toolbarPretty;
    @BindView(R.id.rc_pretty_picture)
    RecyclerView rcPrettyPicture;
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_toolbar_save)
    ImageView ivToolbarSave;
    private List<PrettyDataBean.ResultsBean> data;
    private PrettyPicturePresenter prettyPicturePresenter;
    private PrettyRcAdapter adapter;
    private int lastVisiblePostion = 0;
    private int page = 1;
    private GridLayoutManager manager;
    private ArrayList<String> imageUrlList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_picture);
        ButterKnife.bind(this);
        initToolbar();
        init();
    }

    private void initToolbar() {
        tvToolbarTitle.setText(getString(R.string.prettypictures));
        ivToolbarSave.setVisibility(View.GONE);
    }

    private void init() {
        // setSupportActionBar(toolbarPretty);
        data = new ArrayList<>();
        prettyPicturePresenter = new PrettyPicturePresenter(this);
        adapter = new PrettyRcAdapter(this, data);
        adapter.setItemClickListener(this);
        manager = new GridLayoutManager(this, 1);
        rcPrettyPicture.setLayoutManager(manager);
        rcPrettyPicture.setAdapter(adapter);
        prettyPicturePresenter.firstRequstData(1);
        rcPrettyPicture.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    if (adapter.isFade() == false && lastVisiblePostion + 1 == adapter.getItemCount()) {
                        //加载数据
                        prettyPicturePresenter.getPrettyRemoteData(page, false);
                    }
                    if (adapter.isFade() == true && lastVisiblePostion == adapter.getItemCount()) {
                        //加载数据
                        prettyPicturePresenter.getPrettyRemoteData(page, false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisiblePostion = manager.findLastVisibleItemPosition();
            }
        });
    }

    @OnClick(R.id.iv_toolbar_back)
    public void setOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                this.finish();
                break;
        }
    }

    @Override
    public void showRefreshPage(PrettyDataBean dataBean) {
        page = 1;
        adapter.onRefreshData(dataBean.getResults());
    }

    @Override
    public void showLoadMorePage(PrettyDataBean dataBean) {
        page += 1;
        adapter.onLoadMoreData(dataBean.getResults());
    }

    @Override
    public void showErrorPage(Throwable throwable) {
        if (throwable.getMessage().equals(getString(R.string.net_not_found))) {
            adapter.setIfHaveMoreData(false);
        }
    }

    @Override
    public void showDataPage() {

    }

    @Override
    public void onPrettyClickListener(View view, int pos) {
        Intent intent = new Intent(this,PhotoViewerActivity.class);
        intent.putExtra("imageUrlList", (Serializable)data);
        intent.putExtra("position",pos);
        startActivity(intent);
    }

    @Override
    public void onPrettyLongClickListener(View view, int pos) {

    }
}
