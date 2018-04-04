package com.sicnu.personal.knowledgesharingapp.pretty.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.pretty.adapter.PrettyRcAdapter;
import com.sicnu.personal.knowledgesharingapp.pretty.contact.PrettyPictureContact;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.pretty.presenter.PrettyPicturePresenter;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

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
    private int lastVisiblePostion = 0;
    private  int page = 1;
    private GridLayoutManager manager;
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
        manager = new GridLayoutManager(this,1);
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
                        YLog.d("PPP_lastVisiblePostion_FALSE: "+lastVisiblePostion);
                        YLog.d("PPPAGE : "+page);
                        prettyPicturePresenter.getPrettyRemoteData(page,false);
                    }
                    if (adapter.isFade() == true && lastVisiblePostion == adapter.getItemCount()) {
                        //加载数据
                        YLog.d("PPP_lastVisiblePostion_TRUE: "+lastVisiblePostion);
                        YLog.d("PPP_getItemCount : "+adapter.getItemCount());
                        prettyPicturePresenter.getPrettyRemoteData(page,false);
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
        page=1;
        adapter.onRefreshData(dataBean.getResults());
    }

    @Override
    public void showLoadMorePage(PrettyDataBean dataBean) {
        YLog.d("PPP_showLoadMorePage");
        page+=1;
        adapter.onLoadMoreData(dataBean.getResults());
    }

    @Override
    public void showErrorPage(Throwable throwable) {
        if(throwable.getMessage().equals("HTTP 404 Not Found")){
            adapter.setIfHaveMoreData(false);
        }
    }

    @Override
    public void showDataPage() {

    }
}
