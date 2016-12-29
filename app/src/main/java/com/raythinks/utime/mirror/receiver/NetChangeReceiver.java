package com.raythinks.utime.mirror.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.raythinks.utime.mirror.db.DayDBManager;
import com.raythinks.utime.mirror.utils.CommonUtils;

/**
 * 功能：网络变化广播接收器<br>
 * 作者：赵海<br>
 * 时间： 2016/12/27 0027<br>.
 * 版本：1.2.0
 */

public class NetChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            DayDBManager.getInstance(context).insertTrafficApp(CommonUtils.getNetype(context));
        }
    }
}
