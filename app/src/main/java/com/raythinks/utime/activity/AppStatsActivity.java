package com.raythinks.utime.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rayker.core.base.BaseActivity;
import com.raythinks.utime.R;
import com.raythinks.utime.adapter.AppStatsAdapter;
import com.raythinks.utime.mvp.contract.AppStatsContract;
import com.raythinks.utime.mvp.presenter.AppStatsPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

/**
 * 功能：App統計<br>
 * 作者：赵海<br>
 * 时间：2016/12/26.
 */

public class AppStatsActivity extends BaseActivity<AppStatsPresenterImpl> implements AppStatsContract.View, ViewPager.OnPageChangeListener, View.OnClickListener {
    private TextView tvTitleBack;
    private TextView tvTitleName;
    private TextView tvSelectTime;
    private TextView tvAppstatsDay;
    private TextView tvAppstatsWeek;
    private TextView tvAppstatsMon;
    private ImageView imgRight;
    private ViewPager vpAppstats;
    private int currentPo = 0;
    List<TextView> tvList = new ArrayList<TextView>();

    @Override
    public AppStatsPresenterImpl getmPresenter() {
        return new AppStatsPresenterImpl(this, this);
    }

    @Override
    public void initView() {

        tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
        tvTitleName = (TextView) findViewById(R.id.tv_title_name);
        tvSelectTime = (TextView) findViewById(R.id.tv_select_time);
        tvAppstatsDay = (TextView) findViewById(R.id.tv_appstats_day);
        tvAppstatsWeek = (TextView) findViewById(R.id.tv_appstats_week);
        tvAppstatsMon = (TextView) findViewById(R.id.tv_appstats_mon);
        imgRight = (ImageView) findViewById(R.id.img_right);
        vpAppstats = (ViewPager) findViewById(R.id.vp_appstats);
        vpAppstats.setAdapter(new AppStatsAdapter(getSupportFragmentManager()));
        vpAppstats.addOnPageChangeListener(this);
        tvTitleBack.setVisibility(View.VISIBLE);
        tvTitleBack.setText("App");
        tvTitleName.setText("今天");
        tvList.add(tvAppstatsDay);
        tvList.add(tvAppstatsWeek);
        tvList.add(tvAppstatsMon);
        tvAppstatsDay.setOnClickListener(this);
        tvAppstatsWeek.setOnClickListener(this);
        tvAppstatsMon.setOnClickListener(this);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
       tvAppstatsDay.setSelected(true);
    }

    @Override
    public void onRigClick(View V) {
        super.onRigClick(V);

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_appstats;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPo = position;
        for (int i = 0; i < tvList.size(); i++) {
            if (i == position) {
                tvList.get(i).setSelected(true);
            } else {
                tvList.get(i).setSelected(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_appstats_day:
                vpAppstats.setCurrentItem(0);
                break;
            case R.id.tv_appstats_week:
                vpAppstats.setCurrentItem(1);
                break;
            case R.id.tv_appstats_mon:
                vpAppstats.setCurrentItem(2);
                break;
        }
    }
}
