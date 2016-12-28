package com.raythinks.utime.mirror.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.text.TextUtils;

import com.raythinks.utime.mirror.model.AppUseStaticsModel;
import com.raythinks.utime.mirror.model.TrafficDbModel;
import com.raythinks.utime.mirror.utils.AppInfoProviderUtils;
import com.raythinks.utime.mirror.utils.CalendarUtil;
import com.raythinks.utime.mirror.utils.CommonUtils;
import com.raythinks.utime.mirror.utils.IconUtils;
import com.raythinks.utime.mirror.utils.LogUtil;
import com.raythinks.utime.mirror.utils.TrafficUtils;
import com.raythinks.utime.utils.CommomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.icon_frame;
import static android.R.id.list;

public class DayDBManager {
    private static final String TAG = "DayDBManager";
    private DBHelper helper;
    private SQLiteDatabase db;
    Context mContext;
    public static String[] statsTable = {DBHelper.DAY_ALLAPP_STATS,
            DBHelper.WEEK_ALLAPP_STATS, DBHelper.MONTH_ALLAPP_STATS};//
    public Map<String, String> backupsMap = new HashMap<>();
    private static DayDBManager dayDBManager;

    public synchronized static DayDBManager getInstance(Context context) {
        if (dayDBManager == null) {
            dayDBManager = new DayDBManager(context);
        }
        return dayDBManager;
    }

    public DayDBManager(Context context) {
        this.mContext = context;
        helper = DBHelper.getInstance(context);
        // 执行了下面这句话，数据库才算创建
        db = helper.getWritableDatabase();
        backupsMap.put(statsTable[0], DBHelper.DAY_ALLAPP_STATS_RECORD);
        backupsMap.put(statsTable[1], DBHelper.WEEK_ALLAPP_STATS_RECORD);
        backupsMap.put(statsTable[2], DBHelper.MONTH_ALLAPP_STATS_RECORD);
    }

    /**
     * 新增或者更新统计时长和频次信息
     *
     * @param appUseStatics
     */
    public void updateTrafficTable(List<AppUseStaticsModel> appUseStatics) {
        try {
            db.beginTransaction();
            // 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
            // 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
            // + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
            // +
            // "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
            // + "weight INTEGER)";
            for (int i = 0; i < statsTable.length; i++) {
                ContentValues cv = new ContentValues();
                for (int j = 0; j < appUseStatics.size(); j++) {
                    cv.put("wifi", appUseStatics.get(j).getUseFreq());
                    cv.put("mobile", appUseStatics.get(j).getUseTime());
                    String[] whereArgs = {String.valueOf(appUseStatics.get(j)
                            .getPkgName())};
                    db.update(statsTable[j], cv, "pkgName=?", whereArgs);
                    LogUtil.i(TAG, "更新数据数据库的新应用 ： "
                            + appUseStatics.get(j).getAppName());
                }

            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    /**
     * 新增或者更新统计时长和频次信息
     *
     * @param appUseStatics
     */
    public void updateTable(List<AppUseStaticsModel> appUseStatics) {
        try {
            db.beginTransaction();
            // 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
            // 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
            // + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
            // +
            // "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
            // + "weight INTEGER)";
            insertTrafficApp(mContext, db, appUseStatics, CommonUtils.getNetype(mContext));
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    public void initAddStatsApp(AppUseStaticsModel appUseStatics) {
        db.beginTransaction();
        try {
            // 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
            // 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
            // + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
            // +
            // "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
            // + "weight INTEGER)";
            for (int i = 0; i < statsTable.length; i++) {
                db.execSQL(
                        "INSERT INTO " + statsTable[i]
                                + " VALUES(?, ?, ?, ?, ?,?,?,?,?,?)",
                        new Object[]{appUseStatics.getAppName(),
                                appUseStatics.getPkgName(),
                                appUseStatics.isSysApp(),
                                0,
                                0,
                                0,
                                0, IconUtils.getIconData(appUseStatics.getIcon()),
                                appUseStatics.getAtime(), System.currentTimeMillis()
                        });
                LogUtil.i(TAG, "新增数据数据库的新应用 ： " + appUseStatics.getAppName());
            }
            insertTraffcApp(mContext, db, appUseStatics, CommonUtils.getNetype(mContext));
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    public List<AppUseStaticsModel> newAllAppStats(final String table) {
        synchronized (db) {
            List<AppUseStaticsModel> backupsList = findStatsAll(table);
            try {
                db.beginTransaction();
                // 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
                // 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
                // + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
                // +
                // "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
                // + "weight INTEGER)";

                deleteAll(table);
                List<AppUseStaticsModel> listStats = AppInfoProviderUtils.getAllAppsStats(mContext);
                insertStatsApp(db, listStats, table);
//                for (int i = 0; i < backupsList.size(); i++) {
//                    AppUseStaticsModel mm = findTrafficApp(backupsList.get(i).getPkgName(), backupsList.get(i).getAtime());
//                    backupsList.get(i).setWifiRx(mm.getWifiRx());
//                    backupsList.get(i).setWifiTx(mm.getWifiTx());
//                    backupsList.get(i).setMobileTx(mm.getMobileTx());
//                    backupsList.get(i).setMobileRx(mm.getMobileRx());
//                }
                saveBackups(backupsList, backupsMap.get(table));
                LogUtil.i(TAG, "数据库创建后插入所有应用信息应用newAllAppStats");
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            return backupsList;
        }

    }

    public static void insertStatsApp(final SQLiteDatabase db, List<AppUseStaticsModel> listStats, String table) {
        for (int j = 0; j < listStats.size(); j++) {
            db.execSQL(
                    "INSERT INTO " + table
                            + " VALUES(?, ?, ?, ?, ?,?,?,?,?,?)",
                    new Object[]{listStats.get(j).getAppName(),
                            listStats.get(j).getPkgName(),
                            listStats.get(j).isSysApp(),
                            0,
                            0, 0, 0, IconUtils.getIconData(listStats.get(j).getIcon()),
                            listStats.get(j).getAtime(), System.currentTimeMillis()
                    });
        }
    }

    public void saveBackups(List<AppUseStaticsModel> listStats, String table) {
        for (int j = 0; j < listStats.size(); j++) {
            db.execSQL(
                    "INSERT INTO " + table
                            + " VALUES(?, ?, ?, ?, ?,?,?,?,?,?)",
                    new Object[]{listStats.get(j).getAppName(),
                            listStats.get(j).getPkgName(),
                            listStats.get(j).isSysApp(),
                            0,
                            0, 0, 0, IconUtils.getIconData(listStats.get(j).getIcon()),
                            listStats.get(j).getAtime(), System.currentTimeMillis()
                    });
        }
    }

    public static void insertTrafficApp(Context mContext, final SQLiteDatabase db, List<AppUseStaticsModel> listStats, int netype) {

        if (netype == ConnectivityManager.TYPE_WIFI || netype == ConnectivityManager.TYPE_MOBILE) {//wifi或者mobile记录
            db.beginTransaction();
            for (int i = 0; i < listStats.size(); i++) {
                AppUseStaticsModel appUseStaticsModel = listStats.get(i);
                insertTraffcApp(mContext, db, appUseStaticsModel, netype);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    /**
     * 网络变化时插入数据
     *
     * @param netype
     */
    public void insertTrafficApp(int netype) {
        if (netype == ConnectivityManager.TYPE_WIFI || netype == ConnectivityManager.TYPE_MOBILE) {//wifi或者mobile记录
            List<AppUseStaticsModel> listStats = AppInfoProviderUtils.getAllAppsStats(mContext);
            db.beginTransaction();
            for (int i = 0; i < listStats.size(); i++) {
                AppUseStaticsModel appUseStaticsModel = listStats.get(i);
                insertTraffcApp(mContext, db, appUseStaticsModel, netype);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    /**
     * 网络变化时插入数据
     *
     * @param netype
     */
    private static void insertTraffcApp(Context mContext, SQLiteDatabase db, AppUseStaticsModel appUseStaticsModel, int netype) {
        if (netype == ConnectivityManager.TYPE_WIFI || netype == ConnectivityManager.TYPE_MOBILE) {//wifi或者mobile记录
            PackageManager pm = mContext.getPackageManager();
            TrafficDbModel listTraffics = new TrafficDbModel(appUseStaticsModel.getPkgName(), appUseStaticsModel.getIsSysApp(), TrafficStats.getUidRxBytes(appUseStaticsModel.getUid()), TrafficStats.getUidTxBytes(appUseStaticsModel.getUid()), netype, CommonUtils.getNowTime());
            db.execSQL(
                    "INSERT INTO " + DBHelper.ALL_APP_TRAFFIC_NETCHANGE
                            + " VALUES(?, ?, ?, ?, ?,?)",
                    new Object[]{listTraffics.getPkgName(),
                            listTraffics.getIsSysApp(),
                            listTraffics.getRx(),
                            listTraffics.getTx(),
                            listTraffics.getNettype(), listTraffics.getaTime()
                    });
        }

    }

    /**
     * 将所有数据从数据库中删除
     *
     * @return
     */
    public int deleteAll(String table) {
        return db.delete(table, null, null);
    }


    /**
     * 根据包名进行查询
     *
     * @param pkgName
     * @return info 返回值非空，可以由调用者判断
     */
    public AppUseStaticsModel queryStatsByPkgName(String pkgName, String table) {
        String sql = "SELECT * FROM " + table + " where pkgName= '" + pkgName
                + "'";

        AppUseStaticsModel info = new AppUseStaticsModel();
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToNext()) {
            info.setPkgName(c.getString(c.getColumnIndex("pkgName")));
            info.setAppName(c.getString(c.getColumnIndex("appName")));
            info.setUseFreq(c.getInt(c.getColumnIndex("useFreq")));
            info.setUseTime(c.getInt(c.getColumnIndex("useTime")));
            info.setIcon(IconUtils.getDrawableFromBitmap(IconUtils
                    .getBitmapFromBytes(c.getBlob(c.getColumnIndex("appIcon")))));
            info.setSysApp(c.getInt(c.getColumnIndex("isSysApp")));
            info.setUpdateTime(c.getInt(c.getColumnIndex("updateTime")));
            info.setAtime(c.getString(c.getColumnIndex("aTime")));
        } else {
            info.setAppName("empty");
            info.setPkgName("empty");
        }
        c.close();
        return info;
    }

    /**
     * 查询所有应用信息
     *
     * @param table
     * @return
     */
    public List<AppUseStaticsModel> findStatsAll(String table) {
        ArrayList<AppUseStaticsModel> infos = new ArrayList<AppUseStaticsModel>();
        String sql = "SELECT * FROM " + table;
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            AppUseStaticsModel info = new AppUseStaticsModel();
            info.setAppName(c.getString(c.getColumnIndex("appName")));
            info.setPkgName(c.getString(c.getColumnIndex("pkgName")));
            info.setSysApp(c.getInt(c.getColumnIndex("isSysApp")));
            info.setUseFreq(c.getInt(c.getColumnIndex("useFreq")));
            info.setUseTime(c.getInt(c.getColumnIndex("useTime")));
            // info.setIcon(IconUtils.getDrawableFromBitmap(IconUtils
            // .getBitmapFromBytes(c.getBlob(c.getColumnIndex("appIcon")))));
            info.setUpdateTime(c.getInt(c.getColumnIndex("updateTime")));
            info.setAtime(c.getString(c.getColumnIndex("aTime")));
            if (info.getUseFreq() > 0 && info.getUseTime() > 0) {
                infos.add(info);
            }
        }
        c.close();
        Collections.sort(infos);
        return infos;
    }

    public AppUseStaticsModel findTrafficApp(String table, String pkName, String today) {
        String sql = "SELECT * FROM " + DBHelper.ALL_APP_TRAFFIC_NETCHANGE + " where aTime=" + today + " and pkgName=" + pkName;
        Cursor c = db.rawQuery(sql, null);
        AppUseStaticsModel appFromPkgName = new AppUseStaticsModel();
        int i = 0;
        TrafficDbModel preTra = null;
        while (c.moveToNext()) {
            TrafficDbModel info = new TrafficDbModel();
            info.setPkgName(c.getString(c.getColumnIndex("pkgName")));
            info.set_id(c.getString(c.getColumnIndex("_id")));
            info.setIsSysApp(c.getInt(c.getColumnIndex("isSysApp")));
            info.setTx(c.getLong(c.getColumnIndex("tx")));
            info.setNettype(c.getInt(c.getColumnIndex("nettype")));
            info.setRx(c.getLong(c.getColumnIndex("rx")));
            info.setaTime(c.getString(c.getColumnIndex("aTime")));
            if (preTra != null) {
                if (preTra.getNettype() == ConnectivityManager.TYPE_WIFI) {
                    appFromPkgName.setWifiRx((appFromPkgName.getWifiRx() + info.getRx() - preTra.getRx()));
                    appFromPkgName.setWifiTx((appFromPkgName.getWifiTx() + info.getTx() - preTra.getTx()));
                } else {
                    appFromPkgName.setMobileRx((appFromPkgName.getMobileRx() + info.getRx() - preTra.getRx()));
                    appFromPkgName.setMobileTx((appFromPkgName.getMobileTx() + info.getTx() - preTra.getTx()));

                }
            }
            preTra = info;
        }
        c.close();
        if (TextUtils.equals(CommonUtils.getNowTime(), today)) {
            if (preTra.getNettype() == ConnectivityManager.TYPE_WIFI) {
                appFromPkgName.setWifiRx((appFromPkgName.getWifiRx() + TrafficStats.getUidRxBytes(appFromPkgName.getUid()) - preTra.getRx()));
                appFromPkgName.setWifiTx((appFromPkgName.getWifiTx() + TrafficStats.getUidTxBytes(appFromPkgName.getUid()) - preTra.getTx()));
            } else {
                appFromPkgName.setMobileRx((appFromPkgName.getMobileRx() + TrafficStats.getUidRxBytes(appFromPkgName.getUid()) - preTra.getRx()));
                appFromPkgName.setMobileTx((appFromPkgName.getMobileTx() + TrafficStats.getUidTxBytes(appFromPkgName.getUid()) - preTra.getTx()));
            }
        }
        return appFromPkgName;
    }

    public List<AppUseStaticsModel> findTrafficTadayAll(String today) {
        String sql = "SELECT * FROM " + DBHelper.ALL_APP_TRAFFIC_NETCHANGE + " where aTime=" + today;
        Cursor c = db.rawQuery(sql, null);
        List<AppUseStaticsModel> list = new ArrayList<>();
        List<TrafficDbModel> trafficList = new ArrayList<>();
        while (c.moveToNext()) {
            TrafficDbModel info = new TrafficDbModel();
            info.setPkgName(c.getString(c.getColumnIndex("pkgName")));
            info.set_id(c.getString(c.getColumnIndex("_id")));
            info.setIsSysApp(c.getInt(c.getColumnIndex("isSysApp")));
            info.setTx(c.getLong(c.getColumnIndex("tx")));
            info.setNettype(c.getInt(c.getColumnIndex("nettype")));
            info.setRx(c.getLong(c.getColumnIndex("rx")));
            info.setaTime(c.getString(c.getColumnIndex("aTime")));
            trafficList.add(info);
        }
        c.close();
        int size = trafficList.size();
        for (int i = 0; i < size; i++) {
            AppUseStaticsModel appFromPkgName = AppInfoProviderUtils.getAppFromPkgName(mContext, trafficList.get(i).getPkgName());
            int index = list.indexOf(appFromPkgName);
            if (index == -1) {
                list.add(appFromPkgName);
            } else {
                appFromPkgName = list.get(index);
            }
            if (i < size - 1) {
                if (trafficList.get(i).getNettype() == ConnectivityManager.TYPE_WIFI) {
                    appFromPkgName.setWifiRx((appFromPkgName.getWifiRx() + trafficList.get(i + 1).getRx() - trafficList.get(i).getRx()));
                    appFromPkgName.setWifiTx((appFromPkgName.getWifiTx() + trafficList.get(i + 1).getTx() - trafficList.get(i).getTx()));
                } else {
                    appFromPkgName.setMobileRx((appFromPkgName.getMobileRx() + trafficList.get(i + 1).getRx() - trafficList.get(i).getRx()));
                    appFromPkgName.setMobileTx((appFromPkgName.getMobileTx() + trafficList.get(i + 1).getTx() - trafficList.get(i).getTx()));

                }
            } else {
                if (trafficList.get(i).getNettype() == ConnectivityManager.TYPE_WIFI) {
                    appFromPkgName.setWifiRx((appFromPkgName.getWifiRx() + TrafficStats.getUidRxBytes(appFromPkgName.getUid()) - trafficList.get(i).getRx()));
                    appFromPkgName.setWifiTx((appFromPkgName.getWifiTx() + TrafficStats.getUidTxBytes(appFromPkgName.getUid()) - trafficList.get(i).getTx()));
                } else {
                    appFromPkgName.setMobileRx((appFromPkgName.getMobileRx() + TrafficStats.getUidRxBytes(appFromPkgName.getUid()) - trafficList.get(i).getRx()));
                    appFromPkgName.setMobileTx((appFromPkgName.getMobileTx() + TrafficStats.getUidTxBytes(appFromPkgName.getUid()) - trafficList.get(i).getTx()));
                }
            }
        }

        return list;
    }


    /**
     * @Description: 关闭数据库
     */

    public void closeDB() {
//         db.close();
        helper.close();
    }
}
