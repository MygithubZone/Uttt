package com.raythinks.utime.mirror.common;

import java.util.List;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;

import com.raythinks.utime.mirror.service.MirrorService;

public class CommUtils {

    /**
     * 判断当前设备中有没有“有权查看使用权限的应用”这个选项
     *
     * @param context
     * @return
     */
    public static boolean isNoOption(Context context) {
        PackageManager packageManager = context.getApplicationContext()
                .getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 判断调用该设备中“有权查看使用权限的应用”这个选项的APP有没有打开
     *
     * @param context
     * @return
     */
    public static boolean isNoSwitch(Context context) {
        long ts = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getApplicationContext()
                .getSystemService("usagestats");
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, 0, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return false;
        }
        return true;
    }


    /**
     * 开启软件监控服务
     *
     * @param act
     */
    public void startMirrorService(Activity act) {
        Intent watchdogService = new Intent(act, MirrorService.class);
        act.startService(watchdogService);
    }
}
