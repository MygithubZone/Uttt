package com.raythinks.utime.mirror.model;

import android.graphics.drawable.Drawable;

/*
 * 从PackageManager获取的应用程序的信息
 */
public class AppInfoModel extends BaseInfoModel {

    private Drawable icon;
    private String appName;
    private String pkgName;
    private int isSysApp; // 判断是否是系统应用;1代表是系统应用 0 代表是第三方应用
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getIsSysApp() {
        return isSysApp;
    }


    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }


    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public int isSysApp() {
        return isSysApp;
    }

    public void setSysApp(int isSysApp) {
        this.isSysApp = isSysApp;
    }

}
