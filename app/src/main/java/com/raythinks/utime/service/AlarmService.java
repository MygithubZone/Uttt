package com.raythinks.utime.service;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/26 0026<br>.
 * 版本：1.2.0
 */

public class AlarmService extends Service {
    AlarmManager am;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
