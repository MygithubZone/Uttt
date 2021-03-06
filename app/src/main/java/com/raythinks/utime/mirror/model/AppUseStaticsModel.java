package com.raythinks.utime.mirror.model;


import android.text.TextUtils;

/*
 * 应用程序的信息：基本信息加上使用信息
 * 集成基本信息类APPInfo
 */
public class AppUseStaticsModel extends AppInfoModel implements Comparable<AppUseStaticsModel> {
    private int useFreq = 0; // 使用频率，以次数为单位
    private int useTime; // 使用时长，以秒为单位
    private int updateTime; // 权重，用于排序
    private long wifiRx = 0; // wifi流量
    private long mobileTx = 0; // 移动流量
    private long mobileRx = 0; // 移动流量
    private long wifiTx = 0; // wifi流量
    private String atime; // 权重，用于排序

    public long getWifiTx() {
        return wifiTx;
    }

    public void setWifiTx(long wifiTx) {
        this.wifiTx = wifiTx;
    }

    public long getWifiRx() {
        return wifiRx;
    }

    public void setWifiRx(long wifiRx) {
        this.wifiRx = wifiRx;
    }

    public long getMobileTx() {
        return mobileTx;
    }

    public void setMobileTx(long mobileTx) {
        this.mobileTx = mobileTx;
    }

    public long getMobileRx() {
        return mobileRx;
    }

    public void setMobileRx(long mobileRx) {
        this.mobileRx = mobileRx;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public int getUseFreq() {
        return useFreq;
    }

    public void setUseFreq(int useFreq) {
        this.useFreq = useFreq;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }


    /**
     * 实现其按权重和是否是系统应用进行排序
     * <p>
     * 依次按权重值，使用频率，使用时间，是否是第三方应用进行排序
     * <p>
     * a negative integer if this instance is less than another; a positive
     * integer if this instance is greater than another; 0 if this instance has
     * the same order as another.
     * <p>
     * Collection.sort实现的是递增排序，此处需要的是递减排序，所以在compareTo实现时使用相反的逻辑
     * <p>
     * 后来优化了一下，直接计算权值，按权值进行比较 权值计算系统应用初始化为0，三方应用初始化为1，保证三方应用总是排在系统应用前面 权值 =
     * 使用时间长度 + 使用次数
     * <p>
     * 比较函数复杂总是会导致意想不到的错误顺序,其次还会犯一些比较器冲突的错误
     * 比如没有对相等情形进行判断的时候就会抛出如下异常：
     * Comparison method violates its general contract
     */
    @Override
    public int compareTo(AppUseStaticsModel another) {
        if (this.updateTime > another.updateTime)
            return -1;
        else if (this.updateTime < another.updateTime)
            return 1;
        else
            return 0;
    }

    public String getAtime() {
        return atime;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }

    @Override
    public boolean equals(Object obj) {
        return TextUtils.equals(getPkgName(), ((AppUseStaticsModel) obj).getPkgName());
    }
}
