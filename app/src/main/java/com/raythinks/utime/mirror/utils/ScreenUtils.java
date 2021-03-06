package com.raythinks.utime.mirror.utils;

import android.content.Context;
import android.view.WindowManager;

public class ScreenUtils {
	private Context context;
	private static WindowManager mWindowManager;
	
	public ScreenUtils(Context c){
		this.context = c;
		mWindowManager = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
	}
	/**
	 * 获取屏幕宽度
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenWidth() {
		return mWindowManager.getDefaultDisplay().getWidth();
	}

	/**
	 * 获取屏幕高度
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight() {
		return mWindowManager.getDefaultDisplay().getHeight();
	}

}
