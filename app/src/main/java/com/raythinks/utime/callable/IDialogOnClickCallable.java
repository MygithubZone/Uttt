package com.raythinks.utime.callable;

/**
 * 文 件 名：IDialogOnClickCallable
 * 功    能：对话框按钮回调
 * 作    者：赵海
 * 时    间：2016/7/27
 **/
public interface IDialogOnClickCallable {
    int DIALOG_POSITIVE = 1;
    int DIALOG_NEGATIVE = -1;
    int DIALOG_CANCEL = 0;

    public void onDialog(int type);

}
