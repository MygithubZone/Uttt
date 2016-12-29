package com.raythinks.utime.mirror.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.raythinks.utime.mirror.db.DayDBManager;
import com.raythinks.utime.mirror.service.MirrorService;
import com.raythinks.utime.mirror.utils.CommonUtils;

public class StartServiceReceiver extends BroadcastReceiver {
    // 重写onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {
        // 后边的XXX.class就是要启动的服务
        CommonUtils.clearStatsData(context);
        DayDBManager.getInstance(context).insertTrafficApp(CommonUtils.getNetype(context));
        Intent service = new Intent(context, MirrorService.class);
        context.startService(service);
    }

}