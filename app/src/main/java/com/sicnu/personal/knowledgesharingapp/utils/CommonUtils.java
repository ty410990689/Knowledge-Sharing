package com.sicnu.personal.knowledgesharingapp.utils;

import android.content.Context;
import android.os.Environment;

import com.sicnu.personal.knowledgesharingapp.constant.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class CommonUtils {
    public static String  cutUrlGetImageName(String imageUrl) {
        if(imageUrl.equals("flashpicture")){
            return "flash_picture"+imageUrl.substring(imageUrl.lastIndexOf("."));
        }else{
            return imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.length());
        }

    }
    public static String getFileMD5(String filePath) {
        File file = new File(filePath);
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
    public static String getDownLoadSavePath(Context mContext){
        return   (String) SharedPreferencesUtils.getParam(mContext, SharedPreferencesUtils.DOWNLOAD_PATH, Constant.DOWNLOAD_DEFALUT_PATH);
    }
    public static final String getDownLoadLocalPath(Context context,String fileName){
        return Environment.getExternalStorageDirectory()+getDownLoadSavePath(context)+fileName;
    }
}
