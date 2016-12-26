package com.raythinks.utime.mirror.receiver;

import com.raythinks.utime.mirror.common.MirrApplication;
import com.raythinks.utime.mirror.enty.AppUseStatics;
import com.raythinks.utime.mirror.utils.AppInfoProviderUtils;
import com.raythinks.utime.mirror.utils.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 
 * @file InstallerBroadcastReceiver.java
 * @package com.zp.quickaccess.receiver
 * @comment 监听其他应用的安装和卸载事件，当应用安装完成或者卸载完成的时候触发，同时更新数据库
 * 
 * @author zp
 * @date 2015-12-30 上午10:43:43
 */
public class InstallerBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = "InstallerBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// 安装软件
		if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
			String packageName = intent.getDataString();
			String pkgName = getPkgNameWithoutPrifx(packageName);
			AppUseStatics temp = new AppUseStatics();
			PackageManager pm = MirrApplication.mAppContext.getPackageManager();
			AppInfoProviderUtils provider = new AppInfoProviderUtils(
					MirrApplication.mAppContext);

			try {
				PackageInfo pi = pm.getPackageInfo(pkgName,
						PackageManager.GET_UNINSTALLED_PACKAGES);
				AppUseStatics aus = provider.getAppFromPkgName(pi, pm);
				MirrApplication.dayDBManager.add(aus);
				LogUtil.i(TAG, "新安装软件信息添加到数据库 : " + aus.getPkgName());
				// LogUtil.i(TAG, "安装软件信息 : Name --> " + aus.getName());
				// LogUtil.i(TAG, "安装软件信息 : PkgName --> " + aus.getPkgName());
				// LogUtil.i(TAG, "安装软件信息 : UseFreq --> " + aus.getUseFreq());
				// LogUtil.i(TAG, "安装软件信息 : UseTime --> " + aus.getUseTime());
				// LogUtil.i(TAG, "安装软件信息 : Weight --> " + aus.getWeight());

			} catch (NameNotFoundException e) {
				LogUtil.i(TAG, "NameNotFoundException : " + e.toString());
				e.printStackTrace();
			}

		}

		// 卸载软件
		if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
			// String packageName = intent.getDataString();
			// String pkgName = getPkgNameWithoutPrifx(packageName);
			// int affectRow =
			// MirrApplication.dayDBManager.deleteByPkgName(pkgName);
			// LogUtil.i(TAG, "卸载软件包名 : " + pkgName);
			// LogUtil.i(TAG, "卸载个数 : " + affectRow);
		}

		// 监听安装或者卸载事件之后可以开启一下WatchdogService服务
		// 开启服务
	}

	private String getPkgNameWithoutPrifx(String pkgNameWithPrifx) {
		if (pkgNameWithPrifx.startsWith("package:", 0)) {
			LogUtil.i(TAG, "with prefix: --> " + pkgNameWithPrifx);
			LogUtil.i(TAG, "erase prefix: --> " + pkgNameWithPrifx.substring(8));
			return pkgNameWithPrifx.substring(8);
		}
		return pkgNameWithPrifx;
	}

}
