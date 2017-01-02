package com.raythinks.utime.mirror.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.raythinks.utime.mirror.model.AppUseStaticsModel;

/**
 * 获取系统中应用的使用信息
 * <p>
 * 获取原理是使用packageManager扫描所有安装在系统中的应用然后获取信息
 * 是一个比较耗时的工作，因此如果要调用这个provider获取应用信息的时候需要在子线程中执行
 */
public class AppInfoProviderUtils {
    private static final String TAG = "AppInfoProvider";

    /**
     * 获取已经安装的所有应用的信息，并返回信息列表
     */
    public synchronized static List<AppUseStaticsModel> getAllAppsStats(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> pkgInfo = pm
                .getInstalledPackages(0);
        List<AppUseStaticsModel> result = new ArrayList<AppUseStaticsModel>();
        for (PackageInfo info : pkgInfo) {
            result.add(getAppFromPkgName(info, pm));
            LogUtil.i(TAG, "getAllAppsStats: " + info.packageName);
        }
        return result;
    }

    /**
     * 获取已经安装的所有应用的信息，并返回信息map
     *
     * @param context
     * @return
     */
    public synchronized static Map<String, AppUseStaticsModel> getAppALL(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> pkgInfo = pm
                .getInstalledPackages(0);
        Map<String, AppUseStaticsModel> result = new HashMap<String, AppUseStaticsModel>();
        for (PackageInfo info : pkgInfo) {
            result.put(info.packageName, getAppFromPkgName(info, pm));
            LogUtil.i(TAG, "getAllAppsStats: " + info.packageName);
        }
        return result;
    }

    /**
     * @param @param  info
     * @param @return
     * @return AppUseStaticsModel
     * @throws
     * @comment 根据PackageInfo获取该包对应的应用的信息，并将应用信息以AppUseStatics bean的形式返回
     * 默认统计数据都为零
     * @date 2015-12-30 上午10:35:33
     */
    public synchronized static AppUseStaticsModel getAppFromPkgName(PackageInfo info, PackageManager pm) {

        AppUseStaticsModel myApp = new AppUseStaticsModel();
        // 获取包名
        String pkgName = info.packageName;
        // 根据包名获取应用信息
        ApplicationInfo appInfo = info.applicationInfo;
        Drawable icon = appInfo.loadIcon(pm);
        String appName = appInfo.loadLabel(pm).toString();
        myApp.setIcon(icon);
        myApp.setPkgName(pkgName);
        myApp.setAppName(appName);
        myApp.setUid(appInfo.uid);
        if (filterApp(appInfo)) { // 如果是第三方应用
            myApp.setSysApp(0);
        } else { // 如果是系统应用
            myApp.setSysApp(1);
        }
        LogUtil.i(TAG, "getAllAppsStats: " + myApp.getPkgName());
        return myApp;
    }

    public synchronized  static AppUseStaticsModel getAppFromPkgName(Context context, String pkgName) {
        synchronized(context){
        PackageManager pm = context.getPackageManager();
        AppUseStaticsModel myApp = new AppUseStaticsModel();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(pkgName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            // 根据包名获取应用信息
            ApplicationInfo appInfo = info.applicationInfo;
            Drawable icon = appInfo.loadIcon(pm);
            String appName = appInfo.loadLabel(pm).toString();
            myApp.setPkgName(pkgName);
            myApp.setAppName(appName);
            myApp.setUid(appInfo.uid);
            myApp.setIcon(icon);
            if (filterApp(appInfo)) { // 如果是第三方应用
                myApp.setSysApp(0);
            } else { // 如果是系统应用
                myApp.setSysApp(1);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtil.i(TAG, "getAppFromPkgName: " + myApp.getPkgName());
        return myApp;
        }
    }

    /*
     * 判断是否是第三方应用。如果是第三方应用，那么返回true否则返回false
     */
    public synchronized static boolean filterApp(ApplicationInfo info) {
        // 如果系统应用更新之后也被视为是三方应用
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
}
