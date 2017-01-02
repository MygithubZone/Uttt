package com.raythinks.utime.mirror.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.raythinks.utime.R;
import com.raythinks.utime.callable.IDialogOnClickCallable;
import com.raythinks.utime.mirror.common.CommUtils;
import com.raythinks.utime.mirror.db.DBHelper;
import com.raythinks.utime.mirror.db.DayDBManager;
import com.raythinks.utime.utils.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CommonUtils {
    private static final String TAG = "CommonUtils";

    public static void startMirror(final Context context) {
        if (Build.VERSION.SDK_INT > 20) {
            if (CommUtils.isNoOption(context)) {
                if (CommUtils.isNoSwitch(context)) {

                } else {
                    DialogUtils.showNoTitleDialog(context, "Set Permission before being Used? ", context.getResources().getString(R.string.str_dialog_sure), context.getResources().getString(R.string.str_dialog_cancel), new IDialogOnClickCallable() {
                        @Override
                        public void onDialog(int type) {
                            Intent intent = new Intent(
                                    Settings.ACTION_USAGE_ACCESS_SETTINGS);
                            context.startActivity(intent);
                        }
                    });
                }
            } else {
//                    ("该手机不具备查看应用权限，是否继续使用该软件？");
            }

        }
    }

    /**
     * 获取本月第一天
     *
     * @return
     */

    public static String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取当天日期
     *
     * @return
     */
    public static String getNowTime() {
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    /**
     * 获取当天日期
     *
     * @return
     */
    public static String getYesterdayData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
        return yesterday;
    }

    /**
     * 本周第一天
     *
     * @return
     */
    public static String getMondayOFWeek() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static String getPreviousWeekMonday() {
        Calendar cal = Calendar.getInstance();
        // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        int n = -1;
        if (getIsFristWeek() == 1) {
            n = -2;
        } else {
            n = -1;
        }
        String monday;
        cal.add(Calendar.DATE, n * 7 + 1);
        // 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return monday;
    }

    public static String getCurrentWeekMonday() {
        Calendar cal = Calendar.getInstance();
        // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        String monday;
        if (getIsFristWeek() == 1) {
            cal.add(Calendar.DATE, -6);
        } else {
            cal.add(Calendar.DATE, 1);
        }
        // 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return monday;
    }

    public static int getIsFristWeek() {
        Calendar cal = Calendar.getInstance();
        int date = cal.get(Calendar.DAY_OF_WEEK);
        Log.e("WeekDay", "" + date);
        return date;
    }

    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * 获取设备号
     *
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 清除缓存数据
     *
     * @param context
     */
    public static void clearStatsData(Context context) {
        if (!TextUtils.equals(
                PreferenceUtils.getPrefString(context, "dayTime", ""),
                CommonUtils.getNowTime())) {
            DayDBManager.getInstance(context)
                    .newAllAppStats(DBHelper.DAY_ALLAPP_STATS);
            PreferenceUtils.setPrefString(context, "dayTime",
                    CommonUtils.getNowTime());
            DayDBManager.getInstance(context).insertTrafficApp(CommonUtils.getNetype(context));
            LogUtil.e(TAG, "updateUseTime 清空日数据");
        }
        if (!TextUtils.equals(
                PreferenceUtils.getPrefString(context, "weekTime", ""),
                CommonUtils.getCurrentWeekMonday())
                && CommonUtils.getIsFristWeek() != 1) {
            DayDBManager.getInstance(context)
                    .newAllAppStats(DBHelper.WEEK_ALLAPP_STATS);
            PreferenceUtils.setPrefString(context, "weekTime",
                    CommonUtils.getCurrentWeekMonday());
            LogUtil.e(TAG, "updateUseTime 清空周数据");
        }
        if (!TextUtils.equals(
                PreferenceUtils.getPrefString(context, "monTime", ""),
                CommonUtils.getFirstDayOfMonth())) {
            DayDBManager.getInstance(context)
                    .newAllAppStats(DBHelper.MONTH_ALLAPP_STATS);
            PreferenceUtils.setPrefString(context, "monTime",
                    CommonUtils.getFirstDayOfMonth());
            LogUtil.e(TAG, "updateUseTime 清空月数据");
        }
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @return
     */
    public static int getNetype(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isAvailable()) {
            return netInfo.getType();
        } else {
            return -1;
        }
    }

    /**
     * 输入秒数，将其转化为 *小时*分*秒的时长表示形式 其中如果时间大于1小时，那么只以 *小时*分的形式展示
     * 如果时间低于1小时但是大于1分钟，那么以 *分*秒的形式展示 如果使用时间低于1分钟 以*秒的形式展示
     *
     * @param time
     * @return
     */
    public static String getFormatTime(Context context,long time) {
        long hour = 0;
        long minute = 0;
        StringBuilder result = new StringBuilder();

        if (time > 3600) {
            hour = time / 3600;
            time = time % 3600;
            result.append(hour + context.getString(R.string.str_unit_hour));

            if (time > 60) {
                minute = time / 60;
                time = time % 60;
                result.append(minute + context.getString(R.string.str_unit_min));
                result.append(time + context.getString(R.string.str_count_sec));
            } else {
                result.append(time + "秒");
            }
        } else {
            if (time > 60) {
                minute = time / 60;
                time = time % 60;
                result.append(minute + "分");

                if (time > 0) {
                    result.append(time + "秒");
                }
            } else {
                result.append(time + "秒");
            }
        }
        return result.toString();
    }

    /**
     * 将秒数转换为分钟数，不加单位
     *
     * @param time
     * @return
     */
    public static int getFormatMinute(int time) {
        int minute = 0;
        if (time > 60) {
            minute = minute / 60;
        }
        return minute + 1; // 不足一分钟按一分钟计算
    }

    /**
     * @param @param  value
     * @param @return
     * @return float
     * @throws
     * @comment 绝对值计算
     * @date 2015-12-28 下午12:57:59
     */
    public static float abs(float f) {
        if (f > 0) {
            return f;
        } else {
            return -f;
        }
    }

}
