package com.sicnu.personal.knowledgesharingapp.pretty.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.pretty.model.databean.PrettyDataBean;
import com.sicnu.personal.knowledgesharingapp.pretty.model.datasource.PrettyDataSource;
import com.sicnu.personal.knowledgesharingapp.pretty.model.datasource.PrettyResponse;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class PrettyPictureActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_picture);
        PrettyResponse response = new PrettyResponse();
        response.getRemotePrettyData(1, new PrettyDataSource.PrettyDataCallBack() {
            @Override
            public void onLoadedSuccessful(PrettyDataBean prettyDataBean) {
                YLog.d("Pretty_picture"+prettyDataBean.getResults().size());
            }

            @Override
            public void onLoadedFailed(Throwable throwable) {

            }
        });
    }
}
