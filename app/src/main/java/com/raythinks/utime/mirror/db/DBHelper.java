package com.raythinks.utime.mirror.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.raythinks.utime.mirror.model.AppUseStaticsModel;
import com.raythinks.utime.mirror.utils.AppInfoProviderUtils;
import com.raythinks.utime.mirror.utils.CommonUtils;
import com.raythinks.utime.mirror.utils.LogUtil;

import java.util.List;

/**
 * @author zhangpeng
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "timer.db"; // 数据库名字
    private static final int DATABASE_VERSION = 1; // 数据库版本号

    public static String DAY_ALLAPP_STATS = "day_allapp_stats"; // 日所有应用信息表
    public static String WEEK_ALLAPP_STATS = "week_allapp_stats"; // 周所有应用信息表
    public static String MONTH_ALLAPP_STATS = "month_allapp_stats"; // 月所有应用信息表

    public static String ALL_APP_TRAFFIC_NETCHANGE = "allapp_traffic_netchange"; // 所有应用网络改变流量记录

    public static String DAY_ALLAPP_STATS_RECORD = "day_allapp_stats_record"; // 日所有应用信息记录表
    public static String WEEK_ALLAPP_STATS_RECORD = "week_allapp_stats_record"; // 周所有应用信息记录表
    public static String MONTH_ALLAPP_STATS_RECORD = "month_allapp_stats_record"; // 月所有应用信息记录表
    public static String[] statsTable = {DBHelper.DAY_ALLAPP_STATS,
            DBHelper.WEEK_ALLAPP_STATS, DBHelper.MONTH_ALLAPP_STATS};//

    // 包含包名，应用名，使用频次，使用时长，以及计算得到的优先级
    // 其中对于是否是系统应用采用的是布尔类型进行判断，而布尔类型是不存在的，所以使用INTEGER，0代表false，1代表true
    private final static String DATY_ALL_APP_INFO_SQL = "CREATE TABLE IF NOT EXISTS "
            + DAY_ALLAPP_STATS
            + "(appName VARCHAR ,pkgName VARCHAR PRIMARY KEY,"
            + "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
            + "aTime VARCHAR,updateTime INTEGER)";
    private final static String WEEK_ALL_APP_INFO_SQL = "CREATE TABLE IF NOT EXISTS "
            + WEEK_ALLAPP_STATS
            + "(appName VARCHAR,pkgName VARCHAR PRIMARY KEY,"
            + "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER,appIcon BLOB,"
            + "aTime VARCHAR,updateTime INTEGER)";
    private final static String MONTH_ALL_APP_INFO_SQL = "CREATE TABLE IF NOT EXISTS "
            + MONTH_ALLAPP_STATS
            + "(appName VARCHAR,pkgName VARCHAR  PRIMARY KEY,"
            + "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
            + "aTime VARCHAR,updateTime INTEGER)";
    //    // 所有应用流量记录
    private final static String ALL_APP_TRAFFIC_NETCHANGE_SQL = "CREATE TABLE IF NOT EXISTS "
            + ALL_APP_TRAFFIC_NETCHANGE
            + "(_ID INTEGER PRIMARY KEY AUTOINCREMENT ,pkgName VARCHAR,"
            + "isSysApp INTEGER,rx INTEGER, tx INTEGER, nettype INTEGER,"
            + "aTime VARCHAR,weekTime VARCHAR,monthTime VARCHAR)";
    private final static String DATY_ALL_APP_INFO_RECORD_SQL = "CREATE TABLE IF NOT EXISTS "
            + DAY_ALLAPP_STATS_RECORD
            + "(appName VARCHAR ,pkgName VARCHAR PRIMARY KEY,"
            + "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER,wifirx INTEGER,wifitx INTEGER,mobilex INTEGER,mobiletx INTEGER, appIcon BLOB,issys INTEGER DEFAULT 0,"
            + "aTime VARCHAR)";
    private final static String MONTH_ALL_APP_INFO_RECORD_SQL = "CREATE TABLE IF NOT EXISTS "
            + MONTH_ALLAPP_STATS_RECORD
            + "(appName VARCHAR ,pkgName VARCHAR PRIMARY KEY,"
            + "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER,wifirx INTEGER,wifitx INTEGER,  mobilex INTEGER,mobiletx INTEGER, appIcon BLOB,issys INTEGER DEFAULT 0,"
            + "aTime VARCHAR)";
    private final static String WEEK_ALL_APP_INFO_RECORD_SQL = "CREATE TABLE IF NOT EXISTS "
            + WEEK_ALLAPP_STATS_RECORD
            + "(appName VARCHAR ,pkgName VARCHAR PRIMARY KEY,"
            + "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER,wifirx INTEGER,wifitx INTEGER,  mobilex INTEGER,mobiletx INTEGER, appIcon BLOB,issys INTEGER DEFAULT 0,"
            + "aTime VARCHAR)";
    private static DBHelper mInstance = null;
    Context mContext;

    private DBHelper(Context context) {
        // CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    /**
     * 单例模式 获取数据库helper实例
     */
    public static synchronized DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
        return mInstance;
    }

    /**
     * 数据库第一次被创建时onCreate会被调用 创建系统所需要使用的3张表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        db.execSQL(DATY_ALL_APP_INFO_SQL);
        db.execSQL(WEEK_ALL_APP_INFO_SQL);
        db.execSQL(MONTH_ALL_APP_INFO_SQL);
        db.execSQL(ALL_APP_TRAFFIC_NETCHANGE_SQL);
        db.execSQL(DATY_ALL_APP_INFO_RECORD_SQL);
        db.execSQL(MONTH_ALL_APP_INFO_RECORD_SQL);
        db.execSQL(WEEK_ALL_APP_INFO_RECORD_SQL);
        List<AppUseStaticsModel> listStats = AppInfoProviderUtils.getAllAppsStats(mContext);
        for (int i = 0; i < statsTable.length; i++) {
            DayDBManager.insertStatsApp(db, listStats, statsTable[i]);
            LogUtil.i(TAG, "数据库创建后插入所有应用信息应用 ： " + listStats.get(i).getAppName());
        }
        DayDBManager.insertTrafficApp(mContext, db, listStats, CommonUtils.getNetype(mContext));
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * 数据库表结构发生变化时，onUpgrade会被调用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
