package com.sicnu.personal.knowledgesharingapp.home.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.utils.NetConnectUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class WebActivity extends Activity {
    @BindView(R.id.webview_knowledge)
    WebView webviewKnowledge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        String intentUrl = getIntent().getStringExtra(Constant.INTENT_WEB_URL);
        initWebView();
        if(intentUrl!=null && !intentUrl.equals("")){
            try {
                webviewKnowledge.loadUrl(intentUrl);
            }catch (Exception e){

            }
        }
    }

    private void initWebView() {

        webviewKnowledge.getSettings().setAllowUniversalAccessFromFileURLs(true);
        if(NetConnectUtils.getInstance(this).isNetWorkConnected()){
            webviewKnowledge.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            webviewKnowledge.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webviewKnowledge.setWebChromeClient(new WebChromeClient());
        webviewKnowledge.getSettings().setDomStorageEnabled(true);
        webviewKnowledge.getSettings().setDatabaseEnabled(true);
        webviewKnowledge.getSettings().setJavaScriptEnabled(true);
        webviewKnowledge.getSettings().setUseWideViewPort(true);
        webviewKnowledge.getSettings().setLoadWithOverviewMode(true);
        webviewKnowledge.getSettings().setDisplayZoomControls(true);
        webviewKnowledge.getSettings().setSupportMultipleWindows(true);
        webviewKnowledge.getSettings().setSupportZoom(true);
        webviewKnowledge.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && webviewKnowledge.canGoBack()){
            webviewKnowledge.goBack();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }

    }
}
