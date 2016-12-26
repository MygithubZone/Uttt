package com.raythinks.utime.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.raythinks.utime.R;
import com.raythinks.utime.adapter.DialogStyleOneAdapter;
import com.raythinks.utime.callable.IDialogOnClickCallable;
import com.raythinks.utime.callable.IDialogOnItemClickCallable;
import com.raythinks.utime.callable.IDialogOnSelectCallable;
import com.raythinks.utime.model.SelectedModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * 功能：<br>
 * 作者：赵海<br>
 * 时间： 2016/12/23 0023<br>.
 * 版本：1.2.0
 */

public class DialogUtils {
    /**
     * 对话框
     *
     * @param context
     * @param titleResId
     * @param messageResId
     * @param positiveResId
     * @param negativeResId
     * @param iDialogOnClickCallable
     */
    public static void showDialog(Context context, @StringRes int titleResId,
                                  @StringRes int messageResId, @StringRes int positiveResId, @StringRes int negativeResId, final IDialogOnClickCallable iDialogOnClickCallable) {
        showDialog(context, context.getResources().getString(titleResId), context.getResources().getString(messageResId), context.getResources().getString(positiveResId), context.getResources().getString(negativeResId), iDialogOnClickCallable);
    }

    /**
     * 只有一个确定按钮对话框
     *
     * @param context
     * @param titleRes
     * @param messageRes
     * @param positiveRes
     * @param iDialogOnClickCallable
     */
    public static void showDialog(Context context, String titleRes,
                                  String messageRes, String positiveRes, final IDialogOnClickCallable iDialogOnClickCallable) {
        showDialog(context, titleRes, messageRes, positiveRes, null, iDialogOnClickCallable);

    }

    /**
     * 对话框
     *
     * @param context
     * @param titleRes
     * @param messageRes
     * @param positiveRes
     * @param negativeRes
     * @param iDialogOnClickCallable
     */
    public static void showDialog(Context context, String titleRes,
                                  String messageRes, String positiveRes, String negativeRes,
                                  final IDialogOnClickCallable iDialogOnClickCallable) {
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
        if (TextUtils.isEmpty(negativeRes)) {
            mMaterialDialog.setNegativeButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iDialogOnClickCallable.onDialog(IDialogOnClickCallable.DIALOG_NEGATIVE);
                    mMaterialDialog.dismiss();
                }
            });
        } else {
            mMaterialDialog.setNegativeButton(negativeRes, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iDialogOnClickCallable.onDialog(IDialogOnClickCallable.DIALOG_NEGATIVE);
                    mMaterialDialog.dismiss();
                }
            });
        }
        mMaterialDialog
                .setPositiveButton(positiveRes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iDialogOnClickCallable.onDialog(IDialogOnClickCallable.DIALOG_POSITIVE);
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.setTitle(titleRes);
        mMaterialDialog.setMessage(messageRes);
        mMaterialDialog.show();
        if (TextUtils.isEmpty(negativeRes)) {
            mMaterialDialog.getNegativeButton().setVisibility(View.GONE);
        } else {
            mMaterialDialog.getNegativeButton().setVisibility(View.VISIBLE);
        }

    }

    public static void showNoTitleDialog(Context context,String messageRes, String positiveRes, String negativeRes,
                                  final IDialogOnClickCallable iDialogOnClickCallable) {
        final MaterialDialog mMaterialDialog = new MaterialDialog(context);
            mMaterialDialog.setNegativeButton(negativeRes, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iDialogOnClickCallable.onDialog(IDialogOnClickCallable.DIALOG_NEGATIVE);
                    mMaterialDialog.dismiss();
                }
            });
        mMaterialDialog
                .setPositiveButton(positiveRes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iDialogOnClickCallable.onDialog(IDialogOnClickCallable.DIALOG_POSITIVE);
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.setMessage(messageRes);
        mMaterialDialog.show();
        if (TextUtils.isEmpty(negativeRes)) {
            mMaterialDialog.getNegativeButton().setVisibility(View.GONE);
        } else {
            mMaterialDialog.getNegativeButton().setVisibility(View.VISIBLE);
        }

    }

    public static void showDialogList(Context context, String title, List<SelectedModel> list, boolean isDes, final IDialogOnItemClickCallable onItemClickListener) {
        final DialogStyleOneAdapter arrayAdapter
                = new DialogStyleOneAdapter(list, isDes);
        ListView listView = (ListView) LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
        listView.setAdapter(arrayAdapter);
        final MaterialDialog alert = new MaterialDialog(context).setTitle(
                title).setContentView(listView);
        alert.setCanceledOnTouchOutside(true);
        alert.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                alert.dismiss();
                onItemClickListener.onItemDialog(i);
            }
        });

    }

    public static void showDialogMultiList(Context context, String title, final List<SelectedModel> list, boolean isDes, final IDialogOnSelectCallable onSelectCallable) {
        final TagFlowLayout tagFlowLayout = (TagFlowLayout) LayoutInflater.from(context).inflate(R.layout.item_dialog_multiselected, null);
        tagFlowLayout.setAdapter(new TagAdapter<SelectedModel>(list) {
            @Override
            public View getView(FlowLayout parent, int position, SelectedModel s) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eidt_hotscene,
                        tagFlowLayout, false);
                tv.setText(s.getName());
                return tv;
            }
        });
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

            }
        });
        final MaterialDialog alert = new MaterialDialog(context).setTitle(
                title).setContentView(tagFlowLayout);
        alert.setNegativeButton(context.getString(R.string.str_dialog_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.setPositiveButton(context.getString(R.string.str_dialog_sure), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SelectedModel> selectModel = new ArrayList<>();
                for (int i : tagFlowLayout.getSelectedList()) {
                    selectModel.add(list.get(i));
                }
                onSelectCallable.onSelect(selectModel);
                alert.dismiss();
            }
        });
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
}
