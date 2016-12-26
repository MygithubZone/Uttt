package com.raythinks.utime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 功能：选中内容<br>
 * 作者：赵海<br>
 * 时间：2016/12/25.
 */

public class SelectedModel implements Parcelable {
    String name;
    String des;

    public SelectedModel(String name, String des) {
        this.name = name;
        this.des = des;
    }

    public SelectedModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.des);
    }

    public SelectedModel() {
    }

    protected SelectedModel(Parcel in) {
        this.name = in.readString();
        this.des = in.readString();
    }

    public static final Parcelable.Creator<SelectedModel> CREATOR = new Parcelable.Creator<SelectedModel>() {
        public SelectedModel createFromParcel(Parcel source) {
            return new SelectedModel(source);
        }

        public SelectedModel[] newArray(int size) {
            return new SelectedModel[size];
        }
    };
}
