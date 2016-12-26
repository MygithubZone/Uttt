package com.raythinks.utime.mirror.enty;

/*
 * 从PackageManager获取的应用程序的信息
 */
public class AppInfo extends BaseInfo{

//	private Drawable icon;
	private String appName;
	private String pkgName;
	private int isSysApp; // 判断是否是系统应用;1代表是系统应用 0 代表是第三方应用
//	public Drawable getIcon() {
//		return icon;
//	}

//	public void setIcon(Drawable icon) {
//		this.icon = icon;
//	}

	public String getName() {
		return appName;
	}

	public void setName(String name) {
		this.appName = name;
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
