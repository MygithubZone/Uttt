package com.rayker.core.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：权限控制<br>
 * 作者：RaykerTeam
 * 时间： 2016/11/3 0003<br>.
 * 版本：1.0.0
 */

public class PermissionUtils {
    /**
     * 无需开启权限API
     */
    public interface OnPermissionListener {
        void noNeedPermission();
    }

    /**
     * 请求权限返回结果判断
     */
    public interface OnPermissionResultListener {
        void onResult(boolean iGranted);
    }

    private static final String TAG = "PermissionUtils";

    /**
     * 处理权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param onPermissionListener 处理结果回调
     */
    public static void handlePermissionCallback(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, OnPermissionResultListener onPermissionListener) {
        boolean iGrantedPermission = true;
        for (int state : grantResults) {
            if (state == PackageManager.PERMISSION_GRANTED) {
            } else {
                iGrantedPermission = false;
                break;
            }
        }
        if (onPermissionListener != null) {
            onPermissionListener.onResult(iGrantedPermission);
        }
    }

    /**
     * 权限请求
     *
     * @param obj                  activity或者
     * @param permissionArray
     * @param requestCode
     * @param onPermissionListener
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestPermission(Object obj, String[] permissionArray, int requestCode, OnPermissionListener onPermissionListener) {
        Activity activity = null;
        Fragment fragment = null;
        if (obj instanceof Activity) {
            activity = (Activity) obj;
        } else if (obj instanceof Fragment) {
            fragment = (Fragment) obj;
            activity = fragment.getActivity();
        } else {
            LogUtils.e(TAG, "obj 只能是 Activity 或者 fragment 及其子类");
            return;
        }
        List<String> permission = new ArrayList<>();
        for (int i = 0; i < permissionArray.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permissionArray[i]) != PackageManager.PERMISSION_GRANTED) {
                // 请求权限
                permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }

        if (ContextCompat.checkSelfPermission(fragment.getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permission.add(Manifest.permission.CAMERA);
        }
        if (permission.size() > 0) {
            // 请求权限
            if (fragment != null) {
                fragment.requestPermissions(permission.toArray(new String[permission.size()]), requestCode);
            } else {
                activity.requestPermissions(permission.toArray(new String[permission.size()]), requestCode);
            }
        } else {
            if (onPermissionListener != null) {
                onPermissionListener.noNeedPermission();
            }
        }
    }
}
