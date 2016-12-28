package com.raythinks.utime.mirror.model;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/28 0028<br>.
 * 版本：1.2.0
 */

public class TrafficDbModel {
    private String _id;
    private String pkgName;
    private int isSysApp; // 判断是否是系统应用;1代表是系统应用 0 代表是第三方应用
    private long rx;
    private long tx;
    private int nettype;
    private String aTime;
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public TrafficDbModel() {

    }

    public TrafficDbModel(String pkgName, int isSysApp, long rx, long tx, int nettype, String aTime) {
        this.pkgName = pkgName;
        this.isSysApp = isSysApp;
        this.rx = rx;
        this.tx = tx;
        this.nettype = nettype;
        this.aTime = aTime;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public int getIsSysApp() {
        return isSysApp;
    }

    public void setIsSysApp(int isSysApp) {
        this.isSysApp = isSysApp;
    }

    public long getRx() {
        return rx;
    }

    public void setRx(long rx) {
        this.rx = rx;
    }

    public long getTx() {
        return tx;
    }

    public void setTx(long tx) {
        this.tx = tx;
    }

    public int getNettype() {
        return nettype;
    }

    public void setNettype(int nettype) {
        this.nettype = nettype;
    }

    public String getaTime() {
        return aTime;
    }

    public void setaTime(String aTime) {
        this.aTime = aTime;
    }
}
