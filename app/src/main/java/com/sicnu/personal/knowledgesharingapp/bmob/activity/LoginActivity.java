package com.sicnu.personal.knowledgesharingapp.bmob.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.bmob.callback.BmobCallBack;
import com.sicnu.personal.knowledgesharingapp.bmob.presenter.RegisterPresenter;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.gank.knowledge.activity.HomeActivity;
import com.sicnu.personal.knowledgesharingapp.utils.SharedPreferencesUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class LoginActivity extends AppCompatActivity implements BmobCallBack {
    @BindView(R.id.edy_login_account)
    EditText edyLoginAccount;
    @BindView(R.id.edt_login_password)
    EditText edtLoginPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    RegisterPresenter persenter;
    @BindView(R.id.ll_root_layout)
    RelativeLayout llRootLayout;
    @BindView(R.id.avi_loading)
    AVLoadingIndicatorView aviLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Bmob.initialize(this, "395da80df8c7610fd2c094e9def84a2c");
        aviLoading.hide();
        setViewsClickale(true);
        YLog.d("Bmob Oncreate");
        persenter = new RegisterPresenter(this);
        AutoLoginUser();
    }


    @OnClick({R.id.btn_login, R.id.btn_register})
    public void btnClickListener(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginByCustom();
                break;
            case R.id.btn_register:
                startRegisterActivity();
                break;
        }
    }

    private void startRegisterActivity() {
        startActivityForResult(new Intent(this, RegisterActivity.class), Constant.REGISTER_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        YLog.d("Bmob OnResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        YLog.d("Bmob onActivityResult");
        if (requestCode == Constant.REGISTER_CODE && resultCode == RESULT_OK) {
            YLog.d("Bmob onActivityResult");
            String userName = data.getStringExtra(Constant.USERNAME);
            String passWord = data.getStringExtra(Constant.PASSWORD);
            persenter.loginUserByPassWord(userName, passWord, this);
        }
    }

    public void loginByCustom() {
        String account = edyLoginAccount.getText().toString();
        String passWord = edtLoginPassword.getText().toString();
        if (account != null && passWord != null && account.length() > 3 && account.length() <= 12
                && passWord.length() > 3 && passWord.length() <= 12) {
            aviLoading.smoothToShow();
            persenter.loginUserByPassWord(account, passWord, this);
        } else {
            Snackbar.make(llRootLayout, "账号或者密码错误", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void AutoLoginUser() {
        String userName = (String) SharedPreferencesUtils.getParam(this, Constant.USERNAME, "null");
        String passWord = (String) SharedPreferencesUtils.getParam(this, Constant.PASSWORD, "null");
        if (!userName.equals("null") && !passWord.equals("null")){
            YLog.d("Bmob AutoLoginUser");
            aviLoading.smoothToShow();
            setViewsClickale(false);
            persenter.loginUserByPassWord(userName, passWord, this);
        }
    }

    @Override
    public void RequestSuccessful(String userName, String passWord) {
        YLog.d("Bmob Id : " + BmobUser.getCurrentUser(this).getObjectId());
        persenter.getUserPhoto(BmobUser.getCurrentUser(this).getObjectId(), this);
    }

    @Override
    public void RequestFailed(int code, String reason) {
        aviLoading.hide();
        setViewsClickale(true);
        YLog.d("Bmob login error : " + reason);
        Snackbar.make(llRootLayout, "登录出错 :" + reason, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void RequestPhotoSuccessful(String photoUrl) {
        aviLoading.hide();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void RequestPhotoFailed(int code, String reason) {
        aviLoading.hide();
        setViewsClickale(true);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public  void setViewsClickale(boolean b){
        btnLogin.setClickable(b);
        btnRegister.setClickable(b);
        edtLoginPassword.setClickable(b);
        edyLoginAccount.setClickable(b);
    }
}
