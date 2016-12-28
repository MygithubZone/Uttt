package com.raythinks.utime.mirror.utils;

import android.net.TrafficStats;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/27 0027<br>.
 * 版本：1.2.0
 */

public class TrafficUtils {
    public static String formatMB(long bytes) {
        float fBytes = (float) bytes;
        String suffix = "B";
        int i = 0;
        while (!"G".equals(suffix) && fBytes >= 1024) {
            i++;
            fBytes /= 1024;
            switch (i) {
                case 1:
                    suffix = "K";
                    break;
                case 2:
                    suffix = "M";
                    break;
                case 3:
                    suffix = "G";
                    break;
                default:
                    break;
            }
        }
        String value = String.format("%.2f", fBytes);
        return value + " " + suffix;
    }

    public static long reduceTraffic(long traffic1, long traffic2) {

        return traffic1 - traffic2;
    }

}
