package com.raythinks.utime.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rayker.core.base.BaseFragment;
import com.raythinks.utime.R;
import com.raythinks.utime.activity.NewSceneModeActivity;
import com.raythinks.utime.activity.WhiteListActivity;
import com.raythinks.utime.adapter.SceneModeAdapter;
import com.raythinks.utime.callable.IDialogOnItemClickCallable;
import com.raythinks.utime.configs.ExtraConfigs;
import com.raythinks.utime.model.SelectedModel;
import com.raythinks.utime.mvp.contract.SceneModeContract;
import com.raythinks.utime.mvp.presenter.SceneModePresenterImpl;
import com.raythinks.utime.utils.DialogUtils;
import com.raythinks.utime.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/15 0015<br>.
 * 版本：1.2.0
 */

public class SceneModeFragment extends BaseFragment implements SceneModeContract.View, View.OnClickListener {
    private TextView tvTitleBack;
    private TextView tvTitleName;
    private TextView tvTitleRight;
    private TextView tvTitleRight1;
    private ImageView ivTitleRight;
    private LinearLayout llSneceWhitelist;
    private TextView tvWhitelistName;
    private TextView tvWhitelistDes;
    private NoScrollListView lvSceneList;
    private TextView tvEmptyScene;
    private NoScrollListView lvProhibitList;
    private TextView tvEmptyProhibit;
    private FloatingActionButton fabSceneAdd;
    SceneModeAdapter sceneAdapter;
    SceneModeAdapter prohibitAdapter;

    @Override
    public SceneModePresenterImpl getmPresenter() {
        return new SceneModePresenterImpl(mActivity, this);
    }

    @Override
    public void initView() {


        tvTitleBack = (TextView) getView().findViewById(R.id.tv_title_back);
        tvTitleName = (TextView) getView().findViewById(R.id.tv_title_name);
        tvTitleRight = (TextView) getView().findViewById(R.id.tv_title_right);
        tvTitleRight1 = (TextView) getView().findViewById(R.id.tv_title_right1);
        ivTitleRight = (ImageView) getView().findViewById(R.id.iv_title_right);
        llSneceWhitelist = (LinearLayout) getView().findViewById(R.id.ll_snece_whitelist);
        tvWhitelistName = (TextView) getView().findViewById(R.id.tv_whitelist_name);
        tvWhitelistDes = (TextView) getView().findViewById(R.id.tv_whitelist_des);
        lvSceneList = (NoScrollListView) getView().findViewById(R.id.lv_scene_list);
        tvEmptyScene = (TextView) getView().findViewById(R.id.tv_empty_scene);
        lvProhibitList = (NoScrollListView) getView().findViewById(R.id.lv_prohibit_list);
        tvEmptyProhibit = (TextView) getView().findViewById(R.id.tv_empty_prohibit);
        fabSceneAdd = (FloatingActionButton) getView().findViewById(R.id.fab_scene_add);
        tvTitleName.setText(R.string.tb_scenemode);
        fabSceneAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<SelectedModel> listModel = new ArrayList<>();
                String[] modelApp = getResources().getStringArray(R.array.arr_str_mode_name);
                String[] modelAppDes = getResources().getStringArray(R.array.arr_str_mode_des);
                for (int i = 0; i < modelApp.length; i++) {
                    listModel.add(new SelectedModel(modelApp[i], modelAppDes[i]));
                }
                DialogUtils.showDialogList(mActivity, getResources().getString(R.string.str_dialog_title_createmode), listModel, true, new IDialogOnItemClickCallable() {
                    @Override
                    public void onItemDialog(int position) {
                        Intent starter = new Intent(mActivity, NewSceneModeActivity.class);
                        starter.putExtra(ExtraConfigs.EXTRA_CREATE_MODE_TYPE, position);
                        startActivity(starter);
                    }
                });
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        sceneAdapter = new SceneModeAdapter(mActivity);
        prohibitAdapter = new SceneModeAdapter(mActivity);
        lvSceneList.setAdapter(sceneAdapter);
        lvSceneList.setEmptyView(tvEmptyScene);
        lvProhibitList.setAdapter(prohibitAdapter);
        lvProhibitList.setEmptyView(tvEmptyProhibit);
        llSneceWhitelist.setOnClickListener(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.frag_scene;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_snece_whitelist:
                Intent starter = new Intent(mActivity, WhiteListActivity.class);
                startActivity(starter);
                break;
        }
    }
}
