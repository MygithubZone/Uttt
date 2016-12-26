package com.raythinks.utime.callable;

/**
 * 文 件 名：IDialogOnEditCallbale
 * 功    能：diaolog
 * 作    者：赵海
 * 时    间：2016/8/12
 **/
public interface IDialogOnEditCallbale {
    int DIALOG_POSITIVE = 1;
    int DIALOG_NEGATIVE = -1;
    int DIALOG_CANCEL = 0;

    void onDialog(int type, int position, int num);
}
