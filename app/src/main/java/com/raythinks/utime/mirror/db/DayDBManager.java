package com.raythinks.utime.mirror.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.raythinks.utime.mirror.common.MirrApplication;
import com.raythinks.utime.mirror.enty.AppUseStatics;
import com.raythinks.utime.mirror.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayDBManager {
	private static final String TAG = "DayDBManager";
	private DBHelper helper;
	private SQLiteDatabase db;
	public static String[] table = { DBHelper.DAY_ALL_APP_INFO,
			DBHelper.WEEK_ALL_APP_INFO, DBHelper.MONTH_ALL_APP_INFO };

	public DayDBManager(Context context) {
		helper = DBHelper.getInstance(context);
		// 执行了下面这句话，数据库才算创建
		db = helper.getWritableDatabase();
	}

	int updateId = 0;

	public void add(List<AppUseStatics> appUseStatics, boolean[] upateArr) {
		db.beginTransaction();
		try {
			// 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
			// 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
			// + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
			// +
			// "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
			// + "weight INTEGER)";
			for (int i = 0; i < table.length; i++) {
				if (!upateArr[i]) {
					ContentValues cv = new ContentValues();
					cv.put("useFreq", appUseStatics.get(i).getUseFreq());
					cv.put("useTime", appUseStatics.get(i).getUseTime());
					cv.put("weight", appUseStatics.get(i).getWeight());
					cv.put("aTime", appUseStatics.get(i).getAtime());
					String[] whereArgs = { String.valueOf(appUseStatics.get(i)
							.getPkgName()) };
					updateId = db.update(table[i], cv, "pkgName=?", whereArgs);
					LogUtil.i(TAG, "更新数据数据库的新应用 ： "
							+ appUseStatics.get(i).getName());
				} else {
					db.execSQL("INSERT INTO " + table[i]
							+ " VALUES(?, ?, ?, ?, ?,?,?,?)",
							new Object[] { appUseStatics.get(i).getName(),
									appUseStatics.get(i).getPkgName(),
									appUseStatics.get(i).isSysApp(),
									appUseStatics.get(i).getUseFreq(),
									appUseStatics.get(i).getUseTime(), null,
									appUseStatics.get(i).getWeight(),
									appUseStatics.get(i).getAtime() });
					LogUtil.i(TAG, "新增数据数据库的新应用 ： "
							+ appUseStatics.get(i).getName());
				}

			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}

	public void initTable(List<AppUseStatics> appUseStatics, String table) {
		db.beginTransaction();
		try {

			// 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
			// 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
			// + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
			// +
			// "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
			// + "weight INTEGER)";
			for (int i = 0; i < appUseStatics.size(); i++) {
				// AppUseStatics dayAppInfo =
				// queryByPkgName(appUseStatics.get(i)
				// .getPkgName(), table);
				// if ("empty".equals(dayAppInfo.getName())) {
				ContentValues cv = new ContentValues();
				cv.put("useFreq", appUseStatics.get(i).getUseFreq());
				cv.put("useTime", appUseStatics.get(i).getUseTime());
				cv.put("weight", appUseStatics.get(i).getWeight());
				cv.put("aTime", appUseStatics.get(i).getAtime());
				String[] whereArgs = { String.valueOf(appUseStatics.get(i)
						.getPkgName()) };
				updateId = db.update(table, cv, "pkgName=?", whereArgs);
				LogUtil.i(TAG, "更新数据数据库的新应用 ： "
						+ appUseStatics.get(i).getName());
				// } else {
				// db.execSQL("INSERT INTO " + table
				// + " VALUES(?, ?, ?, ?, ?,?,?,?)",
				// new Object[] { appUseStatics.get(i).getName(),
				// appUseStatics.get(i).getPkgName(),
				// appUseStatics.get(i).isSysApp(),
				// appUseStatics.get(i).getUseFreq(),
				// appUseStatics.get(i).getUseTime(), null,
				// appUseStatics.get(i).getWeight(),
				// appUseStatics.get(i).getAtime() });
				// LogUtil.i(TAG, "新增数据数据库的新应用 ： "
				// + appUseStatics.get(i).getName());
				// }

			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}

	public void add(AppUseStatics appUseStatics) {
		db.beginTransaction();
		try {
			// 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
			// 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
			// + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
			// +
			// "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
			// + "weight INTEGER)";
			for (int i = 0; i < table.length; i++) {
				db.execSQL(
						"INSERT INTO " + table[i]
								+ " VALUES(?, ?, ?, ?, ?,?,?,?)",
						new Object[] { appUseStatics.getName(),
								appUseStatics.getPkgName(),
								appUseStatics.isSysApp(),
								appUseStatics.getUseFreq(),
								appUseStatics.getUseTime(), null,
								appUseStatics.getWeight(),
								appUseStatics.getAtime() });
				LogUtil.i(TAG, "新增数据数据库的新应用 ： " + appUseStatics.getName());

			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}

	public void add(String table, AppUseStatics appUseStatics) {
		db.beginTransaction();
		try {
			// 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
			// 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
			// + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
			// +
			// "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
			// + "weight INTEGER)";
			db.execSQL(
					"INSERT INTO " + table + " VALUES(?, ?, ?, ?, ?,?,?,?)",
					new Object[] { appUseStatics.getName(),
							appUseStatics.getPkgName(),
							appUseStatics.isSysApp(),
							appUseStatics.getUseFreq(),
							appUseStatics.getUseTime(), null,
							appUseStatics.getWeight(), appUseStatics.getAtime() });
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}

	public void addALL(String table, List<AppUseStatics> appUseStatics) {
		db.beginTransaction();
		try {
			// 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
			// 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
			// + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
			// +
			// "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
			// + "weight INTEGER)";
			MirrApplication.dayDBManager.deleteAll(table);
			for (int j = 0; j < appUseStatics.size(); j++) {
				db.execSQL("INSERT INTO " + table
						+ " VALUES(?, ?, ?, ?, ?,?,?,?)", new Object[] {
						appUseStatics.get(j).getName(),
						appUseStatics.get(j).getPkgName(),
						appUseStatics.get(j).isSysApp(),
						appUseStatics.get(j).getUseFreq(),
						appUseStatics.get(j).getUseTime(), null,
						appUseStatics.get(j).getWeight(),
						appUseStatics.get(j).getAtime() });
			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}

	// public void add(List<AppUseStatics> appUseStatics) {
	// db.beginTransaction();
	// try {
	// // 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
	// // 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
	// // + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
	// // +
	// // "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
	// // + "weight INTEGER)";
	// for (int i = 0; i < table.length; i++) {
	// db.execSQL("INSERT INTO " + table[i]
	// + " VALUES(?, ?, ?, ?, ?,?,?,?)", new Object[] {
	// appUseStatics.get(i).getName(),
	// appUseStatics.get(i).getPkgName(),
	// appUseStatics.get(i).isSysApp(),
	// appUseStatics.get(i).getUseFreq(),
	// appUseStatics.get(i).getUseTime(), null,
	// appUseStatics.get(i).getWeight(),
	// appUseStatics.get(i).getAtime() });
	// }
	// db.setTransactionSuccessful();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// db.endTransaction();
	// }
	//
	// }
	/**
	 * 将列表中所有数据添加到数据库中
	 * 
	 * 实现有一些低效，后续优化
	 * 
	 * @param allAppStatics
	 */
	// public void addAll(List<AppUseStatics> allAppStatics) {
	// add(allAppStatics);
	// }

	/**
	 * 根据应用名将其从数据库中删除
	 * 
	 * @param name
	 * @return
	 */
	public int deleteByAppName(String name) {
		return db.delete(DBHelper.DAY_ALL_APP_INFO, "appName=?",
				new String[] { name });
	}

	/**
	 * 
	 * @comment 根据应用包名将应用删除
	 * @param @param pkgName
	 * @param @return
	 * @return int
	 * @throws
	 * @date 2015-12-30 上午11:12:53
	 */
	public int deleteByPkgName(String pkgName) {
		return db.delete(DBHelper.DAY_ALL_APP_INFO, "pkgName=?",
				new String[] { pkgName });
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
	 * 根据应用名进行查询
	 * 
	 * @param name
	 * @return info
	 */
	public AppUseStatics queryByAppName(String name) {
		String sql = "SELECT * FROM " + DBHelper.DAY_ALL_APP_INFO
				+ " where appName= '" + name + "'";

		AppUseStatics info = new AppUseStatics();
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToNext()) {
			info.setPkgName(c.getString(c.getColumnIndex("pkgName")));
			info.setName(c.getString(c.getColumnIndex("appName")));
			info.setUseFreq(c.getInt(c.getColumnIndex("useFreq")));
			info.setUseTime(c.getInt(c.getColumnIndex("useTime")));
			// info.setIcon(IconUtils.getDrawableFromBitmap(IconUtils
			// .getBitmapFromBytes(c.getBlob(c.getColumnIndex("appIcon")))));
			info.setSysApp(c.getInt(c.getColumnIndex("isSysApp")));
			info.setWeight(c.getInt(c.getColumnIndex("weight")));
			info.setAtime(c.getString(c.getColumnIndex("aTime")));
		} else {
			info.setName("empty");
			info.setPkgName("empty");
		}
		c.close();
		return info;
	}

	/**
	 * 根据包名进行查询
	 * 
	 * @param pkgName
	 * @return info 返回值非空，可以由调用者判断
	 */
	public AppUseStatics queryByPkgName(String pkgName, String table) {
		String sql = "SELECT * FROM " + table + " where pkgName= '" + pkgName
				+ "'";

		AppUseStatics info = new AppUseStatics();
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToNext()) {
			info.setPkgName(c.getString(c.getColumnIndex("pkgName")));
			info.setName(c.getString(c.getColumnIndex("appName")));
			info.setUseFreq(c.getInt(c.getColumnIndex("useFreq")));
			info.setUseTime(c.getInt(c.getColumnIndex("useTime")));
			// info.setIcon(IconUtils.getDrawableFromBitmap(IconUtils
			// .getBitmapFromBytes(c.getBlob(c.getColumnIndex("appIcon")))));
			info.setSysApp(c.getInt(c.getColumnIndex("isSysApp")));
			info.setWeight(c.getInt(c.getColumnIndex("weight")));
			info.setAtime(c.getString(c.getColumnIndex("aTime")));
		} else {
			info.setName("empty");
			info.setPkgName("empty");
		}
		c.close();
		return info;
	}

	/**
	 * 查询所有应用信息
	 */
	public List<AppUseStatics> findAll(String table) {
		ArrayList<AppUseStatics> infos = new ArrayList<AppUseStatics>();
		String sql = "SELECT * FROM " + table;
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			AppUseStatics info = new AppUseStatics();
			info.setName(c.getString(c.getColumnIndex("appName")));
			info.setPkgName(c.getString(c.getColumnIndex("pkgName")));
			info.setSysApp(c.getInt(c.getColumnIndex("isSysApp")));
			info.setUseFreq(c.getInt(c.getColumnIndex("useFreq")));
			info.setUseTime(c.getInt(c.getColumnIndex("useTime")));
			// info.setIcon(IconUtils.getDrawableFromBitmap(IconUtils
			// .getBitmapFromBytes(c.getBlob(c.getColumnIndex("appIcon")))));
			info.setWeight(c.getInt(c.getColumnIndex("weight")));
			info.setAtime(c.getString(c.getColumnIndex("aTime")));
			if (info.getUseFreq() > 0 && info.getUseTime() > 0) {
				infos.add(info);
			}
		}
		c.close();
		Collections.sort(infos);
		return infos;
	}

	/**
	 * 查找排名前count的应用
	 * 
	 * @param count
	 * @return
	 */
	public ArrayList<AppUseStatics> findTopApp(int count) {
		ArrayList<AppUseStatics> infos = new ArrayList<AppUseStatics>();

		// 查询之后按权重排序
		String sql = "SELECT * FROM " + DBHelper.DAY_ALL_APP_INFO
				+ " order by weight desc";
		Cursor c = db.rawQuery(sql, null);

		if (c.getCount() <= 0) {
			return null;
		} else {
			// 取出前count 个应用的信息
			for (int i = 0; i < count; i++) {
				if (c.moveToNext()) {
					AppUseStatics info = new AppUseStatics();
					info.setPkgName(c.getString(c.getColumnIndex("pkgName")));
					info.setName(c.getString(c.getColumnIndex("appName")));
					info.setUseFreq(c.getInt(c.getColumnIndex("useFreq")));
					info.setUseTime(c.getInt(c.getColumnIndex("useTime")));
					// info.setIcon(IconUtils.getDrawableFromBitmap(IconUtils
					// .getBitmapFromBytes(c.getBlob(c
					// .getColumnIndex("appIcon")))));
					info.setSysApp(c.getInt(c.getColumnIndex("isSysApp")));
					info.setWeight(c.getInt(c.getColumnIndex("weight")));
					info.setAtime(c.getString(c.getColumnIndex("aTime")));

					infos.add(info);
				}
			}
		}
		c.close();
		Collections.sort(infos);
		return infos;
	}

	/**
	 * 根据应用名更新应用信息，比如修改权重和使用时间信息等
	 * 
	 */
	public int updateAppInfo(List<AppUseStatics> info) {
		int updateId = 0;
		db.beginTransaction();
		try {
			// 依次插入包名，应用名，是否是系统应用，使用频次，使用时间，权重值
			// 由于这里省略了列的顺序，所以在进行数据插入的时候需要注意和建表顺序一致
			// + "(appName VARCHAR PRIMARY KEY,pkgName VARCHAR,"
			// +
			// "isSysApp INTEGER, useFreq INTEGER, useTime INTEGER, appIcon BLOB,"
			// + "weight INTEGER)";
			for (int i = 0; i < table.length; i++) {
				ContentValues cv = new ContentValues();
				cv.put("useFreq", info.get(i).getUseFreq());
				cv.put("useTime", info.get(i).getUseTime());
				cv.put("weight", info.get(i).getWeight());
				cv.put("aTime", info.get(i).getAtime());
				String[] whereArgs = { String.valueOf(info.get(i).getPkgName()) };
				updateId = db.update(table[i], cv, "pkgName=?", whereArgs);
			}
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return updateId;
	}

	/**
	 * 更新数据库中所有应用信息
	 * 
	 * @return 更新的记录数
	 */
	public int updateAll() {

		return 0;
	}

	/**
	 * @Description: 关闭数据库
	 */
	public void closeDB() {
		// db.close();
		helper.close();
	}
}
