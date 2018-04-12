package com.sicnu.personal.knowledgesharingapp.gank.relaxmedia.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.gank.contact.GankContact;
import com.sicnu.personal.knowledgesharingapp.gank.model.databean.GankDataBean;
import com.sicnu.personal.knowledgesharingapp.gank.presenter.GankRemotePresenter;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

/**
 * Created by Administrator on 2018/4/12 0012.
 */

public class RelaxMediaActivity extends Activity implements GankContact.GankView {

    private GankRemotePresenter gankRemotePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gankRemotePresenter = new GankRemotePresenter(this);
        gankRemotePresenter.firstRequstData("休息视频");
    }

    @Override
    public void showRefreshPage(GankDataBean dataBean) {

    }

    @Override
    public void showLoadMorePage(GankDataBean dataBean) {
        YLog.d("MediaData : "+dataBean.getResults().get(0).getDesc());
    }

    @Override
    public void showErrorPage(Throwable throwable) {

    }

    @Override
    public void showDataPage() {

    }
}
