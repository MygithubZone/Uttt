package com.raythinks.utime.mirror.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import com.coolerfall.daemon.Daemon;
import com.raythinks.utime.R;
import com.raythinks.utime.mirror.common.MirrApplication;
import com.raythinks.utime.mirror.db.DBHelper;
import com.raythinks.utime.mirror.db.DayDBManager;
import com.raythinks.utime.mirror.model.AppUseStaticsModel;
import com.raythinks.utime.mirror.utils.AppInfoProviderUtils;
import com.raythinks.utime.mirror.utils.CommonUtils;
import com.raythinks.utime.mirror.utils.LogUtil;
import com.raythinks.utime.mirror.utils.PreferenceUtils;
import com.raythinks.utime.mirror.utils.SceneUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 监视当前开启的应用的任务栈并检查其最顶层的activity
 * <p>
 * 用于应用使用信息的统计，实了现监视屏幕开启且不是桌面的情形下的栈顶应用 然后根据获取的应用信息查询数据库得到应用使用的统计数据
 * 并将每次的应用使用信息更新到数据库
 * <p>
 * 之前的监视方法： 对于activity栈顶变化可以划分为4中情形： 桌面-应用：计时开始 应用-桌面：计时结束，更新数据库
 * 应用-应用：又分为：本应用-本应用 或者 本应用-其他应用 桌面-桌面：此情形不计时
 * 1秒钟的循环，对于进入之后迅速退出的程序而言可能检测不到，不过不必那么精确
 * <p>
 * 由于在进行判断的时候，将本应用也作为桌面加入，因此从本应用到桌面 == 桌面--桌面 从桌面--本应用 == 桌面--桌面 从其他应用--本应用 ==
 * 应用到桌面 会造成一定的统计误差
 * <p>
 * 如果不可以去制造极端测试情况的话，还是比较准确的，次数，时间，开启应用数目 非极端的正常使用都是正确的。除非一秒钟做多次切换无法准确的监测
 * <p>
 * 需要注意一点的是，虽然自己监听了软件安装卸载的广播事件，而且以此为根据更新数据库
 * 但是总是会有意外情况，所以如果将数据插入数据库之前可以先判断一下该应用是不是在数据库中
 * 如果不在，那么将其添加进去。也就是修改updateUseTime()方法，添加一个判断即可
 */
public class MirrorService extends Service {

    private static final String TAG = "MirrorService";
    private ActivityManager mActivityManager;
    private String prePackageName;//之前运行app包名
    private String nextPackageName;//当前运行app包名
    private int sleepLength = 1000; // 线程休眠时长
    private int timeCount = 0; // 时间计数器
    boolean isRun = true;
    ExecutorService singleThread;// 当前线程

    @Override

    public void onCreate() {
        super.onCreate();
        DayDBManager.getInstance(getApplicationContext());
        Daemon.run(this, MirrorService.class, Daemon.INTERVAL_ONE_MINUTE * 2);
    }

    /**
     * 根据包名将当前统计信息写入到数据库
     * 在写入之前先判断数据库中是否有该应用，如果没有那么从系统中获取(该过程可能导致WatchdogService阻塞)
     * 然后将获取的信息赋值给tempAppInfo。 如果有该应用(tempAppInfo的name
     * 不为empty)那么直接使用tempAppInfo为对象插入到数据库
     *
     * @param pkgName 更新应用的包名
     */
    public synchronized void updateUseTime(String pkgName) {
        if (pkgName != null && pkgName.contains("com.android.settings")) {
            return;
        }
        if (pkgName != null && pkgName.contains("com.raythinks.utime")) {
            return;
        }
        AppUseStaticsModel dayAppInfo = DayDBManager.getInstance(getApplicationContext()).queryStatsByPkgName(
                pkgName, DBHelper.DAY_ALLAPP_STATS);
        AppUseStaticsModel weekAppInfo = DayDBManager.getInstance(getApplicationContext())
                .queryStatsByPkgName(pkgName, DBHelper.WEEK_ALLAPP_STATS);
        AppUseStaticsModel monthAppInfo = DayDBManager.getInstance(getApplicationContext())
                .queryStatsByPkgName(pkgName, DBHelper.MONTH_ALLAPP_STATS);
        List<AppUseStaticsModel> lst = new ArrayList<AppUseStaticsModel>();
        lst.add(dayAppInfo);
        lst.add(weekAppInfo);
        lst.add(monthAppInfo);
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo(pkgName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            AppUseStaticsModel appStatics = AppInfoProviderUtils.getAppFromPkgName(info, pm);
            for (int i = 0; i < lst.size(); i++) {
                lst.get(i).setAppName(appStatics.getAppName());
                lst.get(i).setPkgName(pkgName);
                lst.get(i).setSysApp(appStatics.isSysApp());
                lst.get(i).setUseFreq(lst.get(i).getUseFreq() + 1);
                lst.get(i).setUseTime(lst.get(i).getUseTime() + timeCount);

                if (i == 0) {
                    lst.get(i).setAtime(CommonUtils.getNowTime());
                } else if (i == 1) {
                    lst.get(i).setAtime(CommonUtils.getCurrentWeekMonday());
                } else if (i == 2) {
                    lst.get(i).setAtime(CommonUtils.getFirstDayOfMonth());
                }
            }
            DayDBManager.getInstance(getApplicationContext()).updateTable(lst);
        } catch (NameNotFoundException e) {
            LogUtil.e(TAG, "updateUseTime " + pkgName
                    + " NameNotFoundException : " + e.toString());
            e.printStackTrace();
        }

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (timeCount != 0) {
            updateUseTime(prePackageName);
            LogUtil.i(TAG, "WatchdogService onDestroy ： timeCount != 0");
        }
        stopForeground(true);
        LogUtil.i(TAG, "WatchdogService onDestroy");
    }


    // /**
    // * Service设置成START_STICKY，kill 后会被重启（等待5秒左右）
    // * 重传Intent
    // *
    // * 由于后来通过解锁广播开启服务，所以就不必通过START_STICKY来保证服务不被系统关闭了
    // *
    // */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        makeStats();
        return Service.START_STICKY;
    }
    /**
     * 线程做统计
     */
    public void makeStats() {
        LogUtil.i(TAG, "WatchdogService onCreate");
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (timeCount != 0) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    updateUseTime(prePackageName);
                    timeCount = 0;

                }
            }).start();

            LogUtil.i(TAG, "onCreate : timeCount = " + timeCount);
        }
        if (singleThread != null) {
            isRun = false;
            LogUtil.i(TAG, "singleThread : shutdownNow");
        }

        ExecutorService singleThreadExecutor = Executors
                .newSingleThreadExecutor();
        singleThreadExecutor.execute(new Runnable() {

            @Override
            public void run() {
                prePackageName = getPackageName();
                nextPackageName = getPackageName();
                final PowerManager mPowerManager = (PowerManager) getApplicationContext()
                        .getSystemService(Context.POWER_SERVICE);
                boolean isScreenOn = true;
                LogUtil.i(TAG, "preAppInfo : package name : " + prePackageName);
                try {
                    Thread.sleep(sleepLength);
                    isRun = true;
                    while (isRun) {
                        isScreenOn = mPowerManager.isScreenOn();
                        // 只有当屏幕亮起的时候才进行统计
                        if (isScreenOn) {
                            LogUtil.i(TAG, "屏幕亮起，开始统计" + timeCount);

                            // 获取当前正在运行的App的包名
                            if (Build.VERSION.SDK_INT > 20) {//SDK_INT5.0以上
                                String str = getProcessNew();
                                if (!TextUtils.isEmpty(str)) {
                                    nextPackageName = str;
                                }

                                Log.e(TAG, "SDK_INT5.0以上" + nextPackageName);
                            } else {//SDK_INT5.0及以下
                                String str = getActivePackagesCompat();
                                if (!TextUtils.isEmpty(str)) {
                                    nextPackageName = str;
                                }
                                Log.e(TAG, "SDK_INT5.0以下" + nextPackageName);
                            }
                            if (MirrApplication.isHome(nextPackageName)) {//当前运行的app是否为桌面应用  ：是
                                // 守护FloatViewService
                                if (MirrApplication.isHome(prePackageName)) {// 之前运行的app是否为桌面应用  ：是
                                    // 1 1 ：nextPackageName、prePackageName均为是桌面，则不统计
                                    LogUtil.i(TAG, "桌面，不统计： " + nextPackageName
                                            + "--" + prePackageName);
                                } else {//退出应用，回到桌面:// prePackageName不是桌面，从非桌面到桌面，计时结束
                                    writeToStorage();
                                    LogUtil.i(TAG, "从非桌面到桌面，计时结束: "
                                            + nextPackageName + "--"
                                            + prePackageName + timeCount);
                                }
                            } else {//当前运行的app是否为桌面应用  ：否
                                if (!SceneUtils.stopSceneApp(MirrorService.this, nextPackageName)) {//非禁止打开该应用，则统计
                                    if (MirrApplication.isHome(prePackageName)) {//之前是桌面
                                        // prePackageName是桌面，从桌面到非桌面，计时开始
                                        timeCount = timeCount + 1;
                                        prePackageName = nextPackageName;
                                        LogUtil.i(TAG, "，从桌面到非桌面，计时开始 "
                                                + nextPackageName + "--"
                                                + prePackageName);

                                    } else {//之前非桌面
                                        if (prePackageName.equals(nextPackageName)) {//同款软件
                                            timeCount = timeCount + 1;
                                            LogUtil.i(TAG, "从非桌面到非桌面,同款"
                                                    + timeCount + prePackageName
                                                    + ":" + nextPackageName);
                                        } else {//新的软件
                                            // 如果nextPackageName和prePackageName不同，那么说明从prePackageName跳转到了nextPackageName
                                            // 也就是prePackageName的计时结束，nextPackageName的计时开始
                                            timeCount = timeCount + 1;
                                            writeToStorage();
                                            LogUtil.i(TAG, "从非桌面到非桌面,异款"
                                                    + prePackageName + ":"
                                                    + nextPackageName);
                                        }
                                    }
                                }
                            }
                            // end if(isScreenOn)
                        } else { // 屏幕熄灭
                            /*
                             * 屏幕熄灭的时候如果统计并没有结束，那么将当前统计结果写入，然后将统计变量初始化
							 *
							 * 避免统计时间丢失：比如当前正在运行音乐软件，然后直接锁屏，如果不在else中将之前统计数据写入
							 * 那么可能会造成之前数据的丢失
							 *
							 * 写入统计结果的同时，将服务自身关闭，减少不必要的运行。当用户下次解锁屏幕的时候再次开启此服务
							 */
                            if (timeCount != 0) {
                                writeToStorage();
                                LogUtil.i(TAG, "屏幕熄灭timeCount != 0");
                            } else {
                                LogUtil.i(TAG, "屏幕熄灭timeCount == 0");
                            }
                            LogUtil.i(TAG, "屏幕熄灭，结束统计，服务停止运行" + timeCount);
                            break; // 结束服务之后跳出循环
                        }
                        Thread.sleep(sleepLength);
                    } // end while
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            /**
             * 记录app实用时长和频次
             */
            private void writeToStorage() {
                updateUseTime(prePackageName);
                timeCount = 0;
                prePackageName = nextPackageName;
            }

            ;
        });
        singleThread = singleThreadExecutor;
    }

    /**
     * 获取当前运行的应用包名 >20
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    String getProcessNew() {
        String topPackageName = "";
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(getResources().getString(R.string.app_usagestats));
        long time = System.currentTimeMillis();
        // We get usage stats for the last 10 seconds
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
        // Sort the stats by the last time used
        if (stats != null) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : stats) {
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                topPackageName = mySortedMap.get(mySortedMap.lastKey())
                        .getPackageName();
                Log.e("TopPackage Name", topPackageName);

                Log.e("MirrorActivity", mySortedMap.get(mySortedMap.lastKey()).toString());
            }
        }
        return topPackageName;
    }

    /**
     * 获取当前运行的应用包名
     *
     * @return
     */
    String getActivePackagesCompat() {
        final List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager
                .getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();
        Log.e("MirrorActivity", taskInfo.get(0).topActivity.getClassName() + "");
        return activePackages[0];
    }
}
