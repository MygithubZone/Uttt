package com.raythinks.utime.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.raythinks.utime.fragment.AppStatsFragment;
import com.raythinks.utime.fragment.TrafficStatsFragment;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2016/12/26.
 */

public class TrafficStatsAdapter extends FragmentPagerAdapter {
    public TrafficStatsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TrafficStatsFragment.newInstance(position);
            case 1:
                return TrafficStatsFragment.newInstance(position);
            case 2:
                return TrafficStatsFragment.newInstance(position);
            default:
                return null;
        }
    }
}
