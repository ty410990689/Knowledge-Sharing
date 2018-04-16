package com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.utils.NetConnectUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class WebActivity extends Activity {
    @BindView(R.id.webview_knowledge)
    WebView webviewKnowledge;
    String oldUrl ;
    int intentType = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        String intentUrl = getIntent().getStringExtra(Constant.INTENT_WEB_URL);
        YLog.d("WebView : "+intentUrl);
        intentType = getIntent().getIntExtra(Constant.INTENT_WEB_TYPE,Constant.INTENT_WEB_KNOWLEDGE_TYPE);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webviewKnowledge.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webviewKnowledge.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webviewKnowledge.getSettings().setPluginState(WebSettings.PluginState.ON);
        webviewKnowledge.getSettings().setUseWideViewPort(true);
        webviewKnowledge.getSettings().setLoadWithOverviewMode(true);
        webviewKnowledge.getSettings().setDisplayZoomControls(true);
        webviewKnowledge.getSettings().setSupportMultipleWindows(true);
        webviewKnowledge.getSettings().setSupportZoom(true);
        webviewKnowledge.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

               if(url==null){return false;}
                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        YLog.d("WebView url 111: ");
                        oldUrl = url;
                        view.loadUrl(url);
                        return true;
                    } else {
                        YLog.d("WebView url 222: ");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(oldUrl));
                        startActivity(intent);
                        ComponentName componentName = new ComponentName("com.sicnu.personal.knowledgesharingapp","com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity.WebActivity");
                        Intent newIntent = new Intent();
                        newIntent.setComponent(componentName);
                        startActivity(newIntent);
                        return true;
                    }
                }catch (Exception e){
                    YLog.d("WebView : "+e.getLocalizedMessage());
                    e.printStackTrace();
                    return false;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && webviewKnowledge.canGoBack() && intentType!=Constant.INTENT_WEB_VIDEO_TYPE){
            webviewKnowledge.goBack();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(webviewKnowledge!=null){
            webviewKnowledge.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(webviewKnowledge!=null){
            webviewKnowledge.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webviewKnowledge!=null){
            webviewKnowledge.destroy();
            webviewKnowledge=null;
        }
    }
}
