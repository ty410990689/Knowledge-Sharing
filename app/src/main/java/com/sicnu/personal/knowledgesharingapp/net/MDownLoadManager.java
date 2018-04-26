package com.sicnu.personal.knowledgesharingapp.net;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.sicnu.personal.knowledgesharingapp.R;
import com.sicnu.personal.knowledgesharingapp.constant.Constant;
import com.sicnu.personal.knowledgesharingapp.utils.CommonUtils;
import com.sicnu.personal.knowledgesharingapp.utils.SharedPreferencesUtils;
import com.sicnu.personal.knowledgesharingapp.utils.YLog;

import java.io.File;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class MDownLoadManager {
    public static MDownLoadManager mManager;
    private static  DownloadManager downLoadManager ;
    private static Context mContext;
    public static final int DOWNLOAD_ADDRESS=0;
    public static final int DOWNLOAD_BYTES=1;
    public static final int DOWNLOAD_TOTAL_BYTES=2;
    public static final int DOWNLOAD_FILE_NAME=3;
    public static final int DOWNLOAD_FILE_SAVE_PATH=4;
    public static MDownLoadManager getInstance(Context context){
        if(mManager==null && downLoadManager==null){
            synchronized (MDownLoadManager.class){
                mManager = new MDownLoadManager(context);
            }
        }
        return mManager;
    }

    private MDownLoadManager(Context context){
        if(downLoadManager==null){
            downLoadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            mContext = context;
        }
    }
    public long addDownLoadTask(String url,String fileName){
        if(downLoadManager!=null) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            String savePath = CommonUtils.getDownLoadSavePath(mContext);
            File file  = new File(savePath);
            if(!file.exists()){
                file.mkdir();
            }
            if(new File(CommonUtils.getDownLoadLocalPath(mContext,fileName)).exists()){
                Toast.makeText(mContext, mContext.getString(R.string.file_is_exited), Toast.LENGTH_SHORT).show();
                return  -2;
            }
            request.setDestinationInExternalPublicDir(savePath, fileName);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            long id = downLoadManager.enqueue(request);
            return id;
        }else{
            return -1;
        }
    }
    public void removeDownLoadTask(long id){
        if(downLoadManager!=null){
            downLoadManager.remove(id);
        }
    }
    public Object queryDownLaodMessage(long id,int key){
        DownloadManager.Query query = new DownloadManager.Query();
        Cursor cursor = downLoadManager.query(query.setFilterById(id));
        if(cursor!=null && cursor.moveToFirst()){
            switch (key){
                case DOWNLOAD_ADDRESS:
                    return cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                case DOWNLOAD_BYTES:
                    return cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

                case DOWNLOAD_TOTAL_BYTES:
                    return cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                case DOWNLOAD_FILE_NAME:
                    return cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));

                case DOWNLOAD_FILE_SAVE_PATH:
                    return  cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                default:
                    return "Error_NoKey";
            }
        }else{
            return "Error";
        }
    }
}
