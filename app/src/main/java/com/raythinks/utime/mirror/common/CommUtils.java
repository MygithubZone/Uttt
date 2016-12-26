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

import com.raythinks.utime.mirror.enty.AppUseStatics;
import com.raythinks.utime.mirror.enty.UserInfoEntry;
import com.raythinks.utime.mirror.service.MirrorService;

public class CommUtils {
	private static UserInfoEntry userInfo;
	/**
	 * 判断当前设备中有没有“有权查看使用权限的应用”这个选项
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
	 * @param context
	 * @return
	 */
	public static  boolean isNoSwitch(Context context) {
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
	 * 清除所有应用使用情况的统计信息
	 */
	public void clearData(String table) {
		// 清除所有应用的统计数据，仅仅是将其三个统计值置为0，并不是将数据库置空
		List<AppUseStatics> infos = MirrApplication.dayDBManager.findAll(table);
		for (int i = 0; i < infos.size(); i++) {
			infos.get(i).setUseFreq(0);
			infos.get(i).setUseTime(0);
			infos.get(i).setWeight(0);
		}
		MirrApplication.dayDBManager.deleteAll(table);
//		MirrApplication.dayDBManager.addAll(infos);
		// 数据清除完成，发消息更新UI
	}

//	public List<AppUseStatics> getApp() {
//		List<AppUseStatics> infos = null;
//		boolean isFirst = MirrApplication.getSharedPreferences().getBoolean(
//				"isFirst", true);
//		// 若是第一次使用，那么遍历系统安装的应用信息
//		/*
//		 * 这里存在一个交互问题：在遍历并排序所有应用信息的过程是很漫长的，然而获取之后填充到ListView的时候却是一瞬间
//		 * 给人感觉很不好，可不可以增量获取，分批次更新ListView？？
//		 */
//		if (isFirst) {
//			infos = MirrApplication.mAppInfoProvider.getAllApps();
//			// 按优先级对其递增排序
//			Collections.sort(infos);
//			MirrApplication.dayDBManager.addAll(infos);
//			Editor editor = MirrApplication.mSharedPreferences.edit();
//			editor.putBoolean("isFirst", false);
//			editor.commit();
//		} else {
//			// 否则就直接从数据库中查询信息
//			infos = MirrApplication.dayDBManager.findAll();
//			Collections.sort(infos);
//		}
//		return infos;
//	}

	/**
	 * 开启软件监控服务
	 * 
	 * @param act
	 */
	public void startMirrorService(Activity act) {
		Intent watchdogService = new Intent(act, MirrorService.class);
		act.startService(watchdogService);
	}
	public static UserInfoEntry getUserInfo() {
		return userInfo;
	}
	public static void setUserInfo(UserInfoEntry userInfo) {
		CommUtils.userInfo = userInfo;
	}
}
