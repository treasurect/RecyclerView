package com.treasure.recycler_view.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by treasure on 2017/12/6.
 */

public class VerDelReportBean implements Parcelable{
    private int width;
    private int id;

    public VerDelReportBean(int width, int id) {
        this.width = width;
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.width);
        dest.writeInt(this.id);
    }

    protected VerDelReportBean(Parcel in) {
        this.id = in.readInt();
        this.width = in.readInt();
    }

    public static final Parcelable.Creator<VerDelReportBean> CREATOR = new Parcelable.Creator<VerDelReportBean>() {
        @Override
        public VerDelReportBean createFromParcel(Parcel source) {
            return new VerDelReportBean(source);
        }

        @Override
        public VerDelReportBean[] newArray(int size) {
            return new VerDelReportBean[size];
        }
    };
}
