package zzg.com.nfc.common;

import android.app.Application;

/**
 * @author zhongzhigang
 * @Description:
 * @date 2017/9/13
 */
public class MyApplication extends Application {
    private static MyApplication mBaseApplication;

    public static MyApplication getInstance() {
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
    }

}