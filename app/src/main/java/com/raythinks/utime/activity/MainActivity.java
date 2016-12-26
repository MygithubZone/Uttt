package com.raythinks.utime.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.rayker.core.base.BaseActivity;
import com.rayker.core.base.BaseFragment;
import com.raythinks.utime.R;
import com.raythinks.utime.adapter.MainPagerAdapter;
import com.raythinks.utime.mirror.service.MirrorService;
import com.raythinks.utime.mvp.contract.MainContract;
import com.raythinks.utime.mvp.presenter.MainPresenterImpl;
import com.raythinks.utime.utils.CommomUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPresenterImpl>
        implements MainContract.View, NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {
    private TabLayout mTlMainTablayout;
    MainPagerAdapter adapter;//Tab适配器
    List<TabLayout.Tab> tabList = new ArrayList<>();
    Toolbar toolbar;
    private FrameLayout flMainCenter;
    private long keyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public MainPresenterImpl getmPresenter() {
        return new MainPresenterImpl(this, this);
    }

    @Override
    public void initView() {
        flMainCenter = (FrameLayout) findViewById(R.id.fl_main_center);
        mTlMainTablayout = (TabLayout) findViewById(R.id.main_bottom_tab);
        mTlMainTablayout.setTabMode(TabLayout.MODE_FIXED);
        adapter = new MainPagerAdapter(this, this.getSupportFragmentManager());
        mTlMainTablayout.setOnTabSelectedListener(this);
        for (int i = 0; i < adapter.getCount(); i++) {//初始化TabLayout
            TabLayout.Tab tab = mTlMainTablayout.newTab();
            tab.setCustomView(adapter.getTabView(i));
            tabList.add(tab);
            mTlMainTablayout.addTab(tab, i == 0 ? true : false);
        }
        initNavigationView();
        com.raythinks.utime.mirror.utils.CommonUtils.startMirror(this);
        Intent watchdogService = new Intent(this, MirrorService.class);
        startService(watchdogService);
    }

    /**
     */
    private void initNavigationView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }


    /**
     * //选中Tab时
     *
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        BaseFragment fragment = (BaseFragment) adapter.instantiateItem(flMainCenter, position);
        adapter.setPrimaryItem(flMainCenter, position, fragment);
        adapter.finishUpdate(flMainCenter);
        adapter.selectorTab(position);
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        activityManager.killBackgroundProcesses("aaaaaaa");
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - keyTime) > 2000) {
                keyTime = System.currentTimeMillis();
//                showToast("再按一次退出程序");
            } else {
//                sendBroadcast(new Intent(Constants.ACTION_EXIT_APP));
                finish();
            }
        }
        return true;
    }
}