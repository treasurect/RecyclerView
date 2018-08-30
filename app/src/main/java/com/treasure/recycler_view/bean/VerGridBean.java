package com.treasure.recycler_view.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by treasure on 2017/12/6.
 */

public class VerGridBean implements Parcelable{
    private int width;
    private int height;
    private int id;
    private int hotNum;
    private int type;//0:add   1:use

    public VerGridBean() {
    }

    public VerGridBean(int width, int id, int hotNum, int type) {
        this.width = width;
        this.id = id;
        this.hotNum = hotNum;
        this.type = type;
    }

    public VerGridBean(int width, int height, int id, int hotNum, int type) {
        this.width = width;
        this.height = height;
        this.id = id;
        this.hotNum = hotNum;
        this.type = type;
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

    public int getHotNum() {
        return hotNum;
    }

    public void setHotNum(int hotNum) {
        this.hotNum = hotNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.id);
        dest.writeInt(this.hotNum);
        dest.writeInt(this.type);
    }

    protected VerGridBean(Parcel in) {
        this.id = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.hotNum = in.readInt();
        this.type = in.readInt();
    }

    public static final Creator<VerGridBean> CREATOR = new Creator<VerGridBean>() {
        @Override
        public VerGridBean createFromParcel(Parcel source) {
            return new VerGridBean(source);
        }

        @Override
        public VerGridBean[] newArray(int size) {
            return new VerGridBean[size];
        }
    };
}
