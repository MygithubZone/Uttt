package com.raythinks.utime.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.raythinks.utime.R;
import com.raythinks.utime.fragment.SceneModeFragment;
import com.raythinks.utime.fragment.RankFragment;
import com.raythinks.utime.fragment.StaticsFragment;
import com.raythinks.utime.utils.CommomUtils;
import com.raythinks.utime.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名：MainPagerAdapter
 * 功    能：主界面适配器
 * 作    者：赵海
 * 时    间：2016/7/11
 **/
public class MainPagerAdapter extends FragmentPagerAdapter {
    Context context;
    List<TextView> tabItemList = new ArrayList<>();//菜单栏View
    int[] titleText = {R.string.tb_stats, R.string.tb_rank, R.string.tb_scenemode};//菜单栏Text
    int[] titleIcon = {R.drawable.tb_db, R.drawable.tb_jx, R.drawable.tb_mine};//菜单栏ICON

    public BadgeView getBageNumView() {
        return bageNumView;
    }


    private BadgeView bageNumView;
//    MainPresenter mainPresenter;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
//        this.mainPresenter = mainPresenter;
    }

    public List<TextView> getTabItemList() {
        return tabItemList;
    }

    /**
     * 获取当前TabView
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        View tabView = LayoutInflater.from(context).inflate(R.layout.tab_main_layout, null);
        TextView tvTabItem = (TextView) tabView.findViewById(R.id.tv_tab_item);
        tvTabItem.setText(titleText[position]);
        tvTabItem.setCompoundDrawables(null, CommomUtils.getDrawable(context, titleIcon[position]), null, null);
        tvTabItem.setCompoundDrawablePadding((int) context.getResources().getDimension(R.dimen.d5));
        if (position == 3) {
            bageNumView = new BadgeView(context);
            bageNumView.setBadgeCount(12);
            bageNumView.setVisibility(View.INVISIBLE);
            bageNumView.setBackground(8, context.getResources().getColor(R.color.c_cf1321));
            bageNumView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
            bageNumView.setBadgeMargin(2);
            bageNumView.setTextColor(context.getResources().getColor(R.color.white));
            bageNumView.setTargetView(tvTabItem);
            bageNumView.setTextSize(10);
        }
        tabItemList.add(tvTabItem);
        return tabView;
    }

    public void selectorTab(int position) {
        for (int i = 0; i < tabItemList.size(); i++) {
            if (position == i) {
                tabItemList.get(position).setSelected(true);
            } else {
                tabItemList.get(i).setSelected(false);
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StaticsFragment();
            case 1:
                return new RankFragment();
            case 2:
                return new SceneModeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
