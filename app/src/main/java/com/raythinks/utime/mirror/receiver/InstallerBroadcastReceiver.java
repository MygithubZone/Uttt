package com.raythinks.utime.mirror.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.raythinks.utime.mirror.db.DayDBManager;
import com.raythinks.utime.mirror.utils.AppInfoProviderUtils;
import com.raythinks.utime.mirror.utils.CommonUtils;
import com.raythinks.utime.mirror.utils.LogUtil;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/28 0028<br>.
 * 版本：1.2.0
 */
public class InstallerBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "InstallerBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 安装软件
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            String pkgName = getPkgNameWithoutPrifx(packageName);
            LogUtil.i(TAG, "新安装软件信息添加到数据库 : " + pkgName);
            DayDBManager.getInstance(context).insertTrafficApp(pkgName, CommonUtils.getNetype(context));
        }
        // 卸载软件
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            //TODO
        }
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
