package com.sicnu.personal.knowledgesharingapp.constant;

import com.sicnu.personal.knowledgesharingapp.R;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class Constant {
    public static final String CUSTOM_SERVER_BASE_URL_GUIDE = "http://192.168.1.1/";
    public static final String BASE_URL_GANK = "http://gank.io/";
    public static final String PRETTY_PICTUR_BASE_URL = "http://192.168.1.110/";
    public static final String NET_STATES = "netStatus";
    public static final String WIFI_IS_CONNECT = "wifiConnect";
    public static final String NET_IS_CONNECT = "netConnect";
    public static final String NET_IS_DISCONNECT = "netDisConnect";

    public static final int ANDROID_FRAGMENT = 1;
    public static final int IOS_FRAGMENT = 0;
    public static final int WEB_FRAGMENT = 2;

    //WebUrl跳转key
    public static final String INTENT_WEB_URL = "IntentWebUrl";
    public static final String INTENT_WEB_TYPE = "IntentWebType";
    public static final int INTENT_WEB_VIDEO_TYPE = 1;
    public static final int INTENT_WEB_KNOWLEDGE_TYPE = 2;
    //Adapter加载更多的常量flag
    public static final int RC_NORMAL_TYPE = 1;
    public static final int RC_LOAD_TYPE = 2;

    public static final int GANK_KNOWLEDGE_COUNT = 10;

    public static final String NET_IS_ERROR = "Failed to connect to";
    public static final String NO_MORE_DATA_TO_LOAD = "HTTP 404 Not Found";
    public static final String DOWNLOAD_SP_KEY = "download";
    public static final String DOWNLOAD_DEFALUT_PATH = "/KnowLedgeShareApp/PrettyPictures/";
    public static final Integer[] RELAX_MEDIA_BG = new Integer[]{R.mipmap.relaxmedia_bg0, R.mipmap.relaxmedia_bg1,
             R.mipmap.relaxmedia_bg2, R.mipmap.relaxmedia_bg3, R.mipmap.relaxmedia_bg4, R.mipmap.relaxmedia_bg5, R.mipmap.relaxmedia_bg6,
            R.mipmap.relaxmedia_bg7, R.mipmap.relaxmedia_bg8, R.mipmap.relaxmedia_bg9, R.mipmap.relaxmedia_bg10, R.mipmap.relaxmedia_bg11,
             R.mipmap.relaxmedia_bg12, R.mipmap.relaxmedia_bg13, R.mipmap.relaxmedia_bg14, R.mipmap.relaxmedia_bg15,
            R.mipmap.relaxmedia_bg16};

    //Login And Register
    public static final String USERNAME= "userName";
    public static final String PASSWORD="passWord";
    public static final int REGISTER_CODE=1;
    public static final int LOGIN_CODE=2;

    public static final int QUERY_ERROR=1;
    public static final int SHOW_ERROR_PAGE =2;
}
