package com.raythinks.utime.mirror.common;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class AppManager {
	/**
	 * 卸载软件
	 * @param act
	 * @param pkgName
	 */
public void uninstallApp(Activity act,String pkgName){
	String uriStr = "package:" + pkgName;
	Uri deleteUri = Uri.parse(uriStr);
	Intent deleteIntent = new Intent();
	deleteIntent.setData(deleteUri);
	deleteIntent.setAction(Intent.ACTION_DELETE);
	act.startActivityForResult(deleteIntent, 0);
}
/**
 * 打开应用
 * @param act
 * @param pkgName
 */
public void startApp(Activity act,String pkgName){
	// 开启应用的逻辑：根据包名获取具有启动属性的activity；然后开启
				try {
					PackageInfo pkgInfo = act.getPackageManager().getPackageInfo(
							pkgName,
							PackageManager.GET_UNINSTALLED_PACKAGES
									| PackageManager.GET_ACTIVITIES);
					ActivityInfo[] activityInfos = pkgInfo.activities;
					// 对于有些系统应用是没有启动属性的，所以添加判断避免崩溃
					if (activityInfos.length > 0) {
						ActivityInfo startActivity = activityInfos[0];
						Intent intent = new Intent();
						intent.setClassName(pkgName, startActivity.name);
						act.startActivity(intent);
					} else {
						Toast.makeText(act, "应用程序无法启动", 0).show();
					}

				} catch (Exception e) {
					Toast.makeText(act, "应用程序无法找到或不允许被启动", 0).show();
					e.printStackTrace();
				}
}
public void onResultAct(Activity act,int requestCode, int resultCode, Intent data){
	// 返回始终是RESULT_CANCELED
			// 根据卸载提示框中用户的实际点击进行判断是否真的执行卸载操作了
			// 如果不进行判断，那么当用户点击卸载然后取消，由于没进行判断，应用也会被从列表中移除
			if (resultCode == act.RESULT_CANCELED) {
				Toast.makeText(act, "取消卸载", 0).show();
			} else if(resultCode == act.RESULT_OK){
				// 卸载之后就将数据从数组中删除，同时将其从数据库中删除
//				AppUseStaticsModel removeObject = MirrApplication.mDBManager
//						.queryStatsByPkgName(clicked_item_pkgname);
//				MirrApplication.mDBManager.deleteByAppName(removeObject.getName());
				// 从数据库中获取更新后的应用信息
//				 List<AppUseStaticsModel> 	infos = MirrApplication.dayDBManager.findStatsAll();
				Toast.makeText(act, "卸载成功", 0).show();
			}else{
				Toast.makeText(act, "无法卸载", 0).show();
			}
}
}
