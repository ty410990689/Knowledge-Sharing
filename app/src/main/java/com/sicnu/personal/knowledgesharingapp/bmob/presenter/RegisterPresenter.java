package com.sicnu.personal.knowledgesharingapp.bmob.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.Toast;

import com.sicnu.personal.knowledgesharingapp.bmob.callback.BmobCallBack;
import com.sicnu.personal.knowledgesharingapp.bmob.userbean.UserLogin;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.utils.SharedPreferencesUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static cn.bmob.v3.util.I.log;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class RegisterPresenter {
    public static final int IMAGE=1;
    private Context context;
    public RegisterPresenter(Activity activity){
        this.context = activity;
    }
    public void openAlbumSelectPhoto(Activity context){
        Intent selectoerPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(selectoerPhoto, IMAGE);
    }
    public void registerUser(final String name, final String passWord, String filePath, final BmobCallBack callback){
        YLog.d("Bmob : loginByCustom");
        final BmobFile file = new BmobFile(new File(filePath));
        file.uploadblock(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                YLog.d("Bmob : file onSuccess");
                UserLogin userLogin = new UserLogin();
                userLogin.setUsername(name);
                userLogin.setPassword(passWord);
                userLogin.setUserPhoto(file);
                userLogin.setPhotoPath(file.getFileUrl(context));
                userLogin.signUp(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        YLog.d("Bmob : onSuccess");
                        callback.RequestSuccessful(name,passWord);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        YLog.d("Bmob : onFailure : "+s);
                        YLog.d("Bmob : onFailure : "+i);
                        callback.RequestFailed(i,s);
                    }
                });
          }

            @Override
            public void onFailure(int i, String s) {
                YLog.d("Bmob : file : "+s);
                callback.RequestFailed(i,s);
            }
        });
    }
    public void loginUserByPassWord(final String userName, final String passWord, final BmobCallBack callBack){
        if(userName==null || passWord==null){
            callBack.RequestFailed(1,"data is null");
            return;
        }
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(userName);
        userLogin.setPassword(passWord);
        userLogin.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                SharedPreferencesUtils.setParam(context, Constant.USERNAME,userName);
                SharedPreferencesUtils.setParam(context,Constant.PASSWORD,passWord);
                callBack.RequestSuccessful(userName,passWord);
            }

            @Override
            public void onFailure(int i, String s) {
               callBack.RequestFailed(i,s);
            }
        });
    }
    public void getUserPhoto(String userId, final BmobCallBack callBack){
        BmobQuery<UserLogin> query = new BmobQuery<UserLogin>();
       query.addWhereEqualTo("objectId",userId);
        query.setLimit(1);
        query.findObjects(context, new FindListener<UserLogin>() {
            @Override
            public void onSuccess(List<UserLogin> list) {
                callBack.RequestPhotoSuccessful(list.get(0).getPhotoPath());
                SharedPreferencesUtils.setParam(context, SharedPreferencesUtils.USER_PHOTO_PATH,list.get(0).getPhotoPath());
            }

            @Override
            public void onError(int i, String s) {
                callBack.RequestFailed(i,s);
                SharedPreferencesUtils.setParam(context, SharedPreferencesUtils.USER_PHOTO_PATH,"null");
            }
        });


        /*query.getObject(context, userId, new GetCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    YLog.d("Bmob login error : "+jsonObject.toString());
                    callBack.RequestPhotoSuccessful(jsonObject.getString("photoPath"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                YLog.d("Bmob pgoto data : "+s);
                callBack.RequestFailed(i,s);
            }
        });*/
    }


}
