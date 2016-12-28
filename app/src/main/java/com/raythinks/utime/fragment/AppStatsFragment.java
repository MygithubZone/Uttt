package com.raythinks.utime.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.rayker.core.base.BaseFragment;
import com.raythinks.utime.R;
import com.raythinks.utime.adapter.AppStatsDataAdapter;
import com.raythinks.utime.configs.ExtraConfigs;
import com.raythinks.utime.mirror.db.DBHelper;
import com.raythinks.utime.mirror.db.DayDBManager;
import com.raythinks.utime.mirror.model.AppUseStaticsModel;
import com.raythinks.utime.mvp.contract.AppStatsFragsContract;
import com.raythinks.utime.mvp.presenter.AppStatsFragsPresenterImpl;

import java.util.List;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2016/12/26.
 */

public class AppStatsFragment extends BaseFragment<AppStatsFragsPresenterImpl> implements AppStatsFragsContract.View {
    private TwinklingRefreshLayout ttrflRefresh;
    private ListView llAppstats;
    AppStatsDataAdapter adapter;
    private Animation animation;
    private LayoutAnimationController controller;
    int timeType = 0;

    @Override
    public AppStatsFragsPresenterImpl getmPresenter() {
        return new AppStatsFragsPresenterImpl(mActivity, this);
    }

    @Override
    public void initView() {
        timeType = getArguments().getInt(ExtraConfigs.EXTRA_APPSTATS_TIMETYPE, 0);
        ttrflRefresh = (TwinklingRefreshLayout) getView().findViewById(R.id.ttrfl_refresh);
        llAppstats = (ListView) getView().findViewById(R.id.ll_appstats);
        adapter = new AppStatsDataAdapter(mActivity);
        llAppstats.setAdapter(adapter);
        if (timeType == 0) {
            updateList(DayDBManager.getInstance(getActivity().getApplicationContext()).findStatsAll(DBHelper.DAY_ALLAPP_STATS));
        } else if (timeType == 1) {
            updateList(DayDBManager.getInstance(getActivity().getApplicationContext()).findStatsAll(DBHelper.WEEK_ALLAPP_STATS));
        } else {
            updateList(DayDBManager.getInstance(getActivity().getApplicationContext()).findStatsAll(DBHelper.MONTH_ALLAPP_STATS));
        }
    }

    public void updateList(List<AppUseStaticsModel> list) {
        animation = new AlphaAnimation(0, 1); // AlphaAnimation 控制渐变透明的动画效果
        animation.setDuration(500);
        // animation = new ScaleAnimation(5,0,2,0); //RotateAnimation
        // 控制画面角度变化的动画效果
        // animation.setDuration(300);
        // animation = new TranslateAnimation(-250f, 0f, 0f, 0f);
        // animation.setDuration(250);
        // 1f为延时
        controller = new LayoutAnimationController(animation, 1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        llAppstats.setLayoutAnimation(controller);
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            {
                total = total + list.get(i).getUseTime();
            }
            adapter.setData(list, total);
            llAppstats.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                }
            });
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.frag_appstats;
    }

    public static AppStatsFragment newInstance(int position) {
        AppStatsFragment fragment = new AppStatsFragment();
        Bundle bundleParams = new Bundle();
        bundleParams.putInt(ExtraConfigs.EXTRA_APPSTATS_TIMETYPE, position);
        fragment.setArguments(bundleParams);
        return fragment;
    }
}
