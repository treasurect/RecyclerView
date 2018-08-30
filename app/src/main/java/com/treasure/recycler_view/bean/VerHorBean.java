package com.treasure.recycler_view.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by treasure on 2017/12/9.
 */

public class VerHorBean implements Parcelable{
    private String label;
    private List<VerGridBean> listBeen;

    public VerHorBean(String label, List<VerGridBean> listBeen) {
        this.label = label;
        this.listBeen = listBeen;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<VerGridBean> getListBeen() {
        return listBeen;
    }

    public void setListBeen(List<VerGridBean> listBeen) {
        this.listBeen = listBeen;
    }

    protected VerHorBean(Parcel in) {
        label = in.readString();
        listBeen = in.createTypedArrayList(VerGridBean.CREATOR);
    }

    public static final Creator<VerHorBean> CREATOR = new Creator<VerHorBean>() {
        @Override
        public VerHorBean createFromParcel(Parcel in) {
            return new VerHorBean(in);
        }

        @Override
        public VerHorBean[] newArray(int size) {
            return new VerHorBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(label);
        parcel.writeTypedList(listBeen);
    }
}
