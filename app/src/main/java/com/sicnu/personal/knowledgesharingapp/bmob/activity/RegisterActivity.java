package com.sicnu.personal.knowledgesharingapp.bmob.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.bmob.callback.BmobCallBack;
import com.sicnu.personal.knowledgesharingapp.bmob.presenter.RegisterPresenter;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.utils.CommonUtils;
import com.tbruyelle.rxpermissions.Permission;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class RegisterActivity extends Activity implements BmobCallBack {
    @BindView(R.id.iv_register_bg)
    ImageView ivRegisterBg;
    @BindView(R.id.edt_register_username)
    EditText edtRegisterUsername;
    @BindView(R.id.edt_register_password)
    EditText edtRegisterPassword;
    @BindView(R.id.btn_register_sure)
    Button btnRegister;
    private static final int IMAGE = 1;
    @BindView(R.id.ll_root_layout)
    LinearLayout llRootLayout;
    @BindView(R.id.avi_loading)
    AVLoadingIndicatorView aviLoading;
    private String imagePath;
    RegisterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        aviLoading.hide();
        setViewsClickale(true);
        presenter = new RegisterPresenter(this);
    }

    @OnClick({R.id.btn_register_sure, R.id.iv_register_bg})
    public void setClickListener(View view) {
        switch (view.getId()) {
            case R.id.iv_register_bg:
                openAlbumSelectePhoto();
                break;

            case R.id.btn_register_sure:
                registerUserFormBmob();
                break;
        }
    }

    private void openAlbumSelectePhoto() {
        CommonUtils.requestSdPermissiton(this, new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.granted) {
                    presenter.openAlbumSelectPhoto(RegisterActivity.this);
                } else if (permission.shouldShowRequestPermissionRationale) {
                    Snackbar.make(llRootLayout, getString(R.string.refuse_sdcard_permission), Snackbar.LENGTH_SHORT).show();
                } else {
                    CommonUtils.showPermissionDialog(RegisterActivity.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            this.imagePath = imagePath;
            showImage(imagePath);
            c.close();
        }
    }

    public void showImage(String path) {
        Bitmap bm = BitmapFactory.decodeFile(path);
        ivRegisterBg.setImageBitmap(bm);
    }

    private void registerUserFormBmob() {
        String account = edtRegisterUsername.getText().toString();
        String passWord = edtRegisterPassword.getText().toString();
        if (account != null && passWord != null && account.length() > 3 && account.length() <= 12
                && passWord.length() > 3 && passWord.length() <= 12 && imagePath != null) {
            aviLoading.smoothToShow();
            setViewsClickale(false);
            presenter.registerUser(account, passWord, imagePath, this);
        } else {
            if (imagePath != null) {
                Snackbar.make(llRootLayout, getString(R.string.userNameOrPasswordTip), Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(llRootLayout, getString(R.string.clickImageToSelectPhoto), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void RequestSuccessful(String userName, String passWord) {
        aviLoading.hide();
        setViewsClickale(true);
        Intent intent = new Intent();
        intent.putExtra(Constant.USERNAME, userName);
        intent.putExtra(Constant.PASSWORD, passWord);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void RequestFailed(int code, String reason) {
        aviLoading.hide();
        setViewsClickale(true);
        Snackbar.make(llRootLayout, "注册失败:" + reason, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void RequestPhotoSuccessful(String photoUrl) {

    }

    @Override
    public void RequestPhotoFailed(int code, String reason) {

    }
    private void setViewsClickale(boolean b){
        edtRegisterPassword.setClickable(b);
        edtRegisterUsername.setClickable(b);
        btnRegister.setClickable(b);
    }
}
