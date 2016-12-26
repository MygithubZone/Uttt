package com.rayker.core.manager;

import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

/**
 * Created by lyy on 2015/11/4.
 * APP生命周期管理类管理
 */
public class ActivityManager {
    private static final String TAG = "ActivityManager";
    private static final Object LOCK = new Object();
    private volatile static ActivityManager mManager = null;
    private Stack<AppCompatActivity> mActivityStack = new Stack<>();

    private static class ActivityManagerUtilsInstance {
        private static ActivityManager INSTANCE = new ActivityManager();
    }

    /* 获取实例 */
    public static ActivityManager getInstance() {
        return ActivityManagerUtilsInstance.INSTANCE;
    }

    /**
     * 获取Activity栈
     *
     * @return
     */
    public Stack<AppCompatActivity> getActivityStack() {
        return mActivityStack;
    }

    /**
     * 堆栈大小
     */
    public int getActivitySize() {
        return mActivityStack.size();
    }

    /**
     * 获取指定的Activity
     */
    public AppCompatActivity getActivity(int location) {
        return mActivityStack.get(location);
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(AppCompatActivity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public AppCompatActivity getCurrentActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        finishActivity(mActivityStack.lastElement());
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(AppCompatActivity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(AppCompatActivity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (AppCompatActivity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (mActivityStack.get(i) != null && mActivityStack.size() > 0) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }


}
