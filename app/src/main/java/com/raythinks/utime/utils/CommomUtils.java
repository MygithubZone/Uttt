package com.raythinks.utime.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.raythinks.utime.R;
import com.raythinks.utime.callable.IMeterialClickCallable;

import java.util.List;

/**
 * 功能：<br>
 * 作者：RaykerTeam
 * 时间： 2016/11/7 0007<br>.
 * 版本：1.0.0
 */

public class CommomUtils {
    public static void makeMeterial(final View meterialView, final IMeterialClickCallable onClickListener) {
//         app:mrl_rippleOverlay="true"              // if true, ripple is drawn in foreground; false - background
//        app:mrl_rippleColor="#ff0000"             // color of ripple
//        app:mrl_rippleAlpha="0.1"                 // alpha of ripple
//        app:mrl_rippleDimension="10dp"            // radius of hover and starting ripple
//        app:mrl_rippleHover="true"                // if true, a hover effect is drawn when view is touched
//        app:mrl_rippleRoundedCorners="10dp"       // radius of corners of ripples. Note: it uses software rendering pipeline for API 17 and below
//        app:mrl_rippleInAdapter="true"            // if true, MaterialRippleLayout will optimize for use in AdapterViews
//        app:mrl_rippleDuration="350"              // duration of ripple animation
//        app:mrl_rippleFadeDuration="75"           // duration of fade out effect on ripple
//        app:mrl_rippleDelayClick="true"           // if true, delays calls to OnClickListeners until ripple effect ends
//        app:mrl_rippleBackground="#FFFFFF"        // background under ripple drawable; used with rippleOverlay="false"
//        app:mrl_ripplePersistent="true"           // if true, ripple background color persists after animation, until setRadius(0) is called
        MaterialRippleLayout materialRippleLayout = MaterialRippleLayout.on(meterialView)
//                .rippleColor(meterialView.getResources().getColor(R.color.mrl_rippleColor))
                .rippleAlpha(0.2f)
                .rippleBackground(meterialView.getResources().getColor(R.color.transparent))
                .rippleDelayClick(false)
                .rippleDuration(400)
                .rippleDiameterDp(20)
                .rippleRoundedCorners(20)
                .rippleHover(true)
                .rippleDuration(75)
//                .ripplePersistent(false)
                .create();
        if (onClickListener != null) {
            materialRippleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onMetrialClick(meterialView);
                }
            });
        }
    }

    public static Drawable getDrawable(@Nullable Context context, @Nullable int id) {
        Drawable draw = context.getResources().getDrawable(id);
        draw.setBounds(0, 0, draw.getMinimumWidth(), draw.getMinimumHeight());
        return draw;
    }
public static  void showToast(Context context,String toastStr){
    Toast.makeText(context, toastStr, Toast.LENGTH_SHORT).show();
}
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    /**
     * 状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        return getInternalDimensionSize(activity.getResources(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    /**
     * 获取系统Android自带的 dimen
     *
     * @param res
     * @param key
     * @return
     */
    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取渠道
     *
     * @param ctx
     * @return
     */
    public static int getAppChannelId(Context ctx) {
        switch (getAppMetaData(ctx, "UMENG_CHANNEL")) {
            case "anzi"://安智
                return 1002;
            case "yingyonghui"://应用汇
                return 1003;
            case "xiaomi"://小米
                return 1004;
            case "sougou"://搜狗
                return 1005;
            case "baidu"://百度
                return 1006;
            case "wandoujia"://豌豆荚
                return 1007;
            case "store91"://91市场
                return 1008;
            case "liantongwo"://联通沃
                return 1009;
            case "mumayi"://木蚂蚁
                return 1010;
            case "android"://Android市场
                return 1011;
            case "yingyongbao"://应拥宝
                return 1012;
            case "shougoustore"://搜狗手机助手
                return 1013;
            case "huawei"://华为市场
                return 1014;
            case "feifansoftware"://非凡软件
                return 1015;
            case "android3G"://3G安卓
                return 1016;
        }
        return 1001;//pinzhishangcheng品质商城默认渠道
    }

    /**
     * //获取渠道号
     *
     * @param ctx
     * @return
     */
    public static String getAppMetaData(Context ctx) {
        return getAppMetaData(ctx, "UMENG_CHANNEL");
    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * list是否为空判断
     *
     * @param list
     * @return
     */
    public static boolean isEmptyList(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * dip转px
     */
    public static int dipToPx(Context context, float dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * px转dip
     */
    public static int pxToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 半角转换为全角
     *
     * @param str
     * @return
     */

    public static String toDBC(String str) {

        char[] c = str.toCharArray();

        for (int i = 0; i < c.length; i++) {

            if (c[i] == 12288) {

                c[i] = (char) 32;

                continue;

            }

            if (c[i] > 65280 && c[i] < 65375)

                c[i] = (char) (c[i] - 65248);

        }

        return new String(c);

    }
}
