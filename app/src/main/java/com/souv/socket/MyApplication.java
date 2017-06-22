package com.souv.socket;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.souv.socket.util.AppContextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;


/**
 * 描述：application
 * 作者：dc on 2017/2/16 10:54
 * 邮箱：597210600@qq.com
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;

    private List<Activity> allActivities;

    public static synchronized MyApplication getInstance() {
        return instance;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtil.init(this);

        instance = this;
        /** LeakCanary **/
//        LeakCanary.install(this);

    }


    /**
     * 添加activity
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new ArrayList<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除activity
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            Log.e(TAG, "移出activity：" + act);
            allActivities.remove(act);
        }
    }

    /**
     * 退出app
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 获取最新一个activity
     * @return
     */
    public String getTopActivity(){
        ActivityManager activityManager =(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        String[] runningAct = runningActivity.split("\\.");
        int size  = runningAct.length;
        String currentRunningAct = runningAct[size -1];
        return currentRunningAct;
    }


}
