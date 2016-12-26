package com.raythinks.utime.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rayker.core.base.BaseActivity;
import com.raythinks.utime.R;
import com.raythinks.utime.configs.ExtraConfigs;
import com.raythinks.utime.mvp.contract.EditSceneContract;
import com.raythinks.utime.mvp.presenter.EditScenePresenterImpl;
import com.raythinks.utime.utils.CommomUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间：2016/12/25.
 */

public class EditSceneActivity extends BaseActivity<EditScenePresenterImpl> implements EditSceneContract.View {
    private TextView tvTitleBack;
    private TextView tvTitleName;
    private TextView tvTitleRight;
    private TextView tvTitleRight1;
    private ImageView ivTitleRight;
    private EditText etSceneName;
    private TagFlowLayout tflSearchFlowlayout;
    private List<String> hotSceneNameList = new ArrayList<>();

    @Override
    public EditScenePresenterImpl getmPresenter() {
        return new EditScenePresenterImpl(this, this);
    }

    @Override
    public void initView() {
        tvTitleBack = (TextView) findViewById(R.id.tv_title_back);
        tvTitleName = (TextView) findViewById(R.id.tv_title_name);
        tvTitleRight = (TextView) findViewById(R.id.tv_title_right);
        tvTitleRight1 = (TextView) findViewById(R.id.tv_title_right1);
        ivTitleRight = (ImageView) findViewById(R.id.iv_title_right);
        etSceneName = (EditText) findViewById(R.id.et_scene_name);
        tflSearchFlowlayout = (TagFlowLayout) findViewById(R.id.tfl_search_flowlayout);
        tvTitleName.setText(R.string.str_title_editscene);
        tvTitleBack.setVisibility(View.VISIBLE);
        tvTitleRight1.setVisibility(View.VISIBLE);
        tvTitleRight1.setText(R.string.str_sure);
        hotSceneNameList.add("Works");
        hotSceneNameList.add("Study");
        hotSceneNameList.add("Games");
        hotSceneNameList.add("Night Rest");
        hotSceneNameList.add("Watch Tv");
        tflSearchFlowlayout.setAdapter(new TagAdapter<String>(hotSceneNameList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eidt_hotscene,
                        tflSearchFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        tflSearchFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                etSceneName.setText(hotSceneNameList.get(position));
                return true;
            }
        });
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onRigClick(View V) {
        super.onRigClick(V);
        String nameStr = etSceneName.getText().toString().trim();
        if (!TextUtils.isEmpty(nameStr)) {
            Intent intent = getIntent();
            intent.putExtra(ExtraConfigs.EXTRA_EDIT_SECENE_NAME, nameStr);
            setResult(ExtraConfigs.RESULT_CODE_EDITSCENE_NAME, intent);
            finish();
            return;
        } else {
            CommomUtils.showToast(this, "Please Input Scene Name");
        }

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_editscene;
    }
}
