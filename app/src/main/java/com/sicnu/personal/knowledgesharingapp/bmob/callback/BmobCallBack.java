package com.sicnu.personal.knowledgesharingapp.bmob.callback;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public interface BmobCallBack {
    void RequestSuccessful(String userName,String passWord);
    void RequestFailed(int code,String reason);
    void RequestPhotoSuccessful(String photoUrl);
    void RequestPhotoFailed(int code,String reason);
}
