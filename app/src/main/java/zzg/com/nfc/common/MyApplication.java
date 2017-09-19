package zzg.com.nfc.common;

import android.app.Application;

import zzg.com.nfc.net.response.LoginResponse;

/**
 * @author zhongzhigang
 * @Description:
 * @date 2017/9/13
 */
public class MyApplication extends Application {
    private static MyApplication mBaseApplication;

    public static LoginResponse getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(LoginResponse userInfo) {
        MyApplication.userInfo = userInfo;
    }

    public static LoginResponse userInfo;
    public static MyApplication getInstance() {
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
    }

}