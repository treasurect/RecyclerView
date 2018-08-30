package com.treasure.recycler_view.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by treasure on 2018/8/30.
 */

public class VerticalBean implements Parcelable{
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VerticalBean(String id, String title) {
        this.id = id;
        this.title = title;
    }

    protected VerticalBean(Parcel in) {
        id = in.readString();
        title = in.readString();
    }

    public static final Creator<VerticalBean> CREATOR = new Creator<VerticalBean>() {
        @Override
        public VerticalBean createFromParcel(Parcel in) {
            return new VerticalBean(in);
        }

        @Override
        public VerticalBean[] newArray(int size) {
            return new VerticalBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
    }
}
