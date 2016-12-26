package com.rayker.core.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.rayker.core.manager.ActivityManager;
import com.rayker.core.utils.AndroidVersionUtil;


/**
 * 功能：RaykerActivit基础类<br>
 * 作者：RaykerTeam
 * 时间： 2016/11/3 0003<br>.
 * 版本：1.0.0
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    public static String TAG = null;
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        initView();
        mPresenter = getmPresenter();
        mPresenter.onStart();
        TAG = getClass().getName();
    }

    public abstract P getmPresenter();

    public abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
    }

    public void onBack(View V) {
        finish();
    }

    public void onRigClick(View V) {
    }

    /**
     * setContentView(id layout)
     *
     * @return
     */
    public abstract int setLayoutId();
}
