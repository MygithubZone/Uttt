package com.raythinks.utime.mirror.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/28 0028<br>.
 * 版本：1.2.0
 */

public class SceneUtils {
    public static boolean stopSceneApp(Context context, String nextPackageName) {
        if (TextUtils.equals(nextPackageName, "com.jfbank.qualitymarket")) {
            Intent MyIntent = new Intent(Intent.ACTION_MAIN);
            MyIntent.addCategory(Intent.CATEGORY_HOME);
            MyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(MyIntent);
            return true;
        } else {
            return false;
        }
    }
}
