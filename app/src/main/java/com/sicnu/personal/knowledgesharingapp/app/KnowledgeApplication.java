package com.sicnu.personal.knowledgesharingapp.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class KnowledgeApplication extends Application implements Application.ActivityLifecycleCallbacks{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Window window = activity.getWindow();
        //设置Window为全透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
