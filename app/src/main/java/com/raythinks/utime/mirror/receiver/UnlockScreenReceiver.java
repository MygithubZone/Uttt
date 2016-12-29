package com.raythinks.utime.mirror.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.raythinks.utime.mirror.db.DayDBManager;
import com.raythinks.utime.mirror.service.MirrorService;
import com.raythinks.utime.mirror.utils.CommonUtils;
import com.raythinks.utime.mirror.utils.LogUtil;

/**
 * @author zp
 * @file UnlockScreenReceiver.java
 * @package com.zp.quickaccess.receiver
 * @comment 监听屏幕点亮事件，并在监听到屏幕亮起之后，判断服务和浮窗是否被优化
 * 如果被优化则开启
 * @date 2015-12-24 下午4:08:01
 */
public class UnlockScreenReceiver extends BroadcastReceiver {

    private static final String TAG = "UnlockScreenReceiver";
    private Context context;

    /**
     * 接收到广播，然后直接开启应用统计服务
     * 而浮窗服务是根据用户设置来选择开启的
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        startWatchdogService();
        LogUtil.i(TAG, "解锁屏幕，开启WatchdogService");
    }

    /**
     * @param
     * @return void
     * @throws
     * @comment 开启应用进程使用情况统计服务
     * @date 2015-12-24 下午5:26:16
     */
    public void startWatchdogService() {
        DayDBManager.getInstance(context).insertTrafficApp(CommonUtils.getNetype(context));
        Intent mWatchdogService = new Intent(this.context, MirrorService.class);
        this.context.startService(mWatchdogService);
        LogUtil.i(TAG, "startWatchdogService in UnlockScreenReceiver");
    }

}
