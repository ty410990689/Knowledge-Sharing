package com.sicnu.personal.knowledgesharingapp.home.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class WebActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String intentUrl = getIntent().getStringExtra(Constant.INTENT_WEB_URL);
    }
}
