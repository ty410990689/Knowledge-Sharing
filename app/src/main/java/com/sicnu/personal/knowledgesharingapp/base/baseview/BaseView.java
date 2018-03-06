package com.sicnu.personal.knowledgesharingapp.base.baseview;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public interface BaseView  {
    void showErrorPage(String errorMessage);
    void showLoadingPage();
    void showSuccessfulPage();
}
