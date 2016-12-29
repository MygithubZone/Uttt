package com.raythinks.utime.mirror.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.raythinks.utime.mirror.db.DBHelper;
import com.raythinks.utime.mirror.db.DayDBManager;
import com.raythinks.utime.mirror.utils.AppInfoProviderUtils;
import com.raythinks.utime.mirror.utils.CommonUtils;
import com.raythinks.utime.mirror.utils.LogUtil;
import com.raythinks.utime.mirror.utils.PreferenceUtils;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/28 0028<br>.
 * 版本：1.2.0
 */

public class DateChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "DateChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.DATE_CHANGED".equals(intent.getAction())) {//日期改变则操作
            CommonUtils.clearStatsData(context.getApplicationContext());
            DayDBManager.getInstance(context.getApplicationContext()).insertTrafficApp(CommonUtils.getNetype(context.getApplicationContext()));
        }
    }


}
