package com.raythinks.utime.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rayker.core.base.BaseActivity;
import com.rayker.core.base.BasePresenter;
import com.raythinks.utime.R;
import com.raythinks.utime.callable.IDialogOnItemClickCallable;
import com.raythinks.utime.callable.IDialogOnSelectCallable;
import com.raythinks.utime.callable.IMeterialClickCallable;
import com.raythinks.utime.configs.ExtraConfigs;
import com.raythinks.utime.model.SelectedModel;
import com.raythinks.utime.mvp.contract.NewSceneModeContract;
import com.raythinks.utime.mvp.presenter.NewSceneModePresenterImpl;
import com.raythinks.utime.utils.CommomUtils;
import com.raythinks.utime.utils.DialogUtils;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.ThemeManager;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.widget.Slider;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2016/12/22.
 */

public class NewSceneModeActivity extends BaseActivity<NewSceneModePresenterImpl> implements NewSceneModeContract.View, IMeterialClickCallable {
    private TextView tvTitleBack;
    private TextView tvTitleName;
    private TextView tvTitleRight;
    private TextView tvTitleRight1;
    private ImageView ivTitleRight;
    private RelativeLayout rlName;
    private TextView tvNameTitle;
    private TextView tvSceneName;
    private RelativeLayout rlStarttime;
    private TextView tvStarttimeTitle;
    private TextView tvScenemodelStarttime;
    private RelativeLayout rlEndtime;
    private TextView tvEndtimeTitle;
    private TextView tvScenemodelEndtime;
    private RelativeLayout rlRepeat;
    private TextView tvRepeatTitle;
    private TextView tvRepeatContent;
    private RelativeLayout rlSnooze;
    private TextView tvSnoozeTitle;
    private TextView tvSnoozeContent;
    private RelativeLayout rlTone;
    private TextView tvToneTitle;
    private TextView tvToneContent;
    private RelativeLayout rlVol;
    private TextView tvVolTitle;
    private Slider sdVolContent;
    private RelativeLayout rlMode;
    private TextView tvMode;
    private TextView tvModeName;
    private TextView tvModeDes;
    private LinearLayout llWhiteset;
    private int modeType = 1;

    @Override
    public NewSceneModePresenterImpl getmPresenter() {
        return new NewSceneModePresenterImpl(this, this);
    }

    @Override
    public void initView() {
        modeType = getIntent().getIntExtra(ExtraConfigs.EXTRA_CREATE_MODE_TYPE, 0);
        tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
        tvTitleName = (TextView) findViewById(R.id.tv_title_name);
        tvTitleRight = (TextView) findViewById(R.id.tv_title_right);
        tvTitleRight1 = (TextView) findViewById(R.id.tv_title_right1);
        ivTitleRight = (ImageView) findViewById(R.id.iv_title_right);
        rlName = (RelativeLayout) findViewById(R.id.rl_name);
        tvNameTitle = (TextView) findViewById(R.id.tv_scenename_title);
        tvSceneName = (TextView) findViewById(R.id.tv_scene_name);
        rlStarttime = (RelativeLayout) findViewById(R.id.rl_starttime);
        tvStarttimeTitle = (TextView) findViewById(R.id.tv_starttime_title);
        tvScenemodelStarttime = (TextView) findViewById(R.id.tv_scenemodel_starttime);
        rlEndtime = (RelativeLayout) findViewById(R.id.rl_endtime);
        tvEndtimeTitle = (TextView) findViewById(R.id.tv_endtime_title);
        tvScenemodelEndtime = (TextView) findViewById(R.id.tv_scenemodel_endtime);
        rlRepeat = (RelativeLayout) findViewById(R.id.rl_repeat);
        tvRepeatTitle = (TextView) findViewById(R.id.tv_repeat_title);
        tvRepeatContent = (TextView) findViewById(R.id.tv_repeat_content);
        rlSnooze = (RelativeLayout) findViewById(R.id.rl_snooze);
        tvSnoozeTitle = (TextView) findViewById(R.id.tv_snooze_title);
        tvSnoozeContent = (TextView) findViewById(R.id.tv_snooze_content);
        rlTone = (RelativeLayout) findViewById(R.id.rl_tone);
        tvToneTitle = (TextView) findViewById(R.id.tv_tone_title);
        tvToneContent = (TextView) findViewById(R.id.tv_tone_content);
        rlVol = (RelativeLayout) findViewById(R.id.rl_vol);
        tvVolTitle = (TextView) findViewById(R.id.tv_vol_title);
        sdVolContent = (Slider) findViewById(R.id.sd_vol_content);
        rlMode = (RelativeLayout) findViewById(R.id.rl_mode);
        tvMode = (TextView) findViewById(R.id.tv_mode);
        tvModeName = (TextView) findViewById(R.id.tv_mode_name);
        tvModeDes = (TextView) findViewById(R.id.tv_mode_des);
        llWhiteset = (LinearLayout) findViewById(R.id.ll_whiteset);
        tvTitleBack.setVisibility(View.VISIBLE);
        tvTitleName.setText(R.string.str_title_newscenemode);
        tvTitleRight.setText(R.string.str_next);
        tvTitleRight.setVisibility(View.VISIBLE);
        if (modeType == 0) {
            tvNameTitle.setText(getResources().getString(R.string.str_scenemode_name));
            tvSceneName.setText(getResources().getString(R.string.str_input_scene_name));
            llWhiteset.setVisibility(View.VISIBLE);
        } else {
            tvNameTitle.setText(getResources().getString(R.string.str_prohibitmode_name));
            tvSceneName.setText(getResources().getString(R.string.str_input_prohibit_name));
            llWhiteset.setVisibility(View.GONE);
        }
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        CommomUtils.makeMeterial(rlEndtime, this);
        CommomUtils.makeMeterial(rlMode, this);
        CommomUtils.makeMeterial(rlName, this);
        CommomUtils.makeMeterial(rlRepeat, this);
        CommomUtils.makeMeterial(rlSnooze, this);
        CommomUtils.makeMeterial(rlStarttime, this);
        CommomUtils.makeMeterial(rlTone, this);
    }

    @Override
    public void onRigClick(View V) {
        super.onRigClick(V);
        Intent starter = new Intent(this, SelectedSoftActivity.class);
        startActivity(starter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ExtraConfigs.REQUEST_CODE_EDITSCENE_NAME && resultCode == ExtraConfigs.RESULT_CODE_EDITSCENE_NAME && data != null) {
            String nameStr = data.getStringExtra(ExtraConfigs.EXTRA_EDIT_SECENE_NAME);
            tvSceneName.setText(nameStr);
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_new_scenemode;
    }

    @Override
    public void onMetrialClick(View view) {
        switch (view.getId()) {
            case R.id.rl_snooze://
                final List<SelectedModel> list = new ArrayList<>();
                list.add(new SelectedModel(getResources().getString(R.string.str_snooze_time_0)));
                String[] snoozeArray = getResources().getStringArray(R.array.arr_str_repeat_time);
                for (int i = 0; i < snoozeArray.length; i++) {
                    list.add(new SelectedModel(snoozeArray[i] + getResources().getString(R.string.str_unit_mins)));
                }
                DialogUtils.showDialogList(this, getResources().getString(R.string.str_newscenemode_snooze), list, false, new IDialogOnItemClickCallable() {
                    @Override
                    public void onItemDialog(int position) {
                        tvSnoozeContent.setText(list.get(position).getName());
                    }
                });
                break;
            case R.id.rl_name:
                Intent editSceneIntent = new Intent(this, EditSceneActivity.class);
                startActivityForResult(editSceneIntent, ExtraConfigs.REQUEST_CODE_EDITSCENE_NAME);
                break;
            case R.id.rl_mode:
                break;
            case R.id.rl_repeat:
                final List<SelectedModel> weekList = new ArrayList<>();
                String[] weekArray = getResources().getStringArray(R.array.arr_str_repeat_week);
                for (int i = 0; i < weekArray.length; i++) {
                    weekList.add(new SelectedModel(weekArray[i]));
                }
                DialogUtils.showDialogMultiList(this, getResources().getString(R.string.str_newscenemode_repeat), weekList, false, new IDialogOnSelectCallable() {
                    @Override
                    public void onSelect(List<SelectedModel> list) {
                    }
                });
                break;
            case R.id.rl_starttime:
                setTime(true);
                break;
            case R.id.rl_endtime:
                setTime(false);
                break;
        }
    }

    private void setTime(final boolean isStartTime) {
        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        TimePickerDialog.Builder builder = new TimePickerDialog.Builder(isLightTheme ? R.style.Material_App_Dialog_TimePicker_Light : R.style.Material_App_Dialog_TimePicker, 24, 00) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
                Toast.makeText(NewSceneModeActivity.this, "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(NewSceneModeActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK")
                .negativeAction("CANCEL");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }
}
