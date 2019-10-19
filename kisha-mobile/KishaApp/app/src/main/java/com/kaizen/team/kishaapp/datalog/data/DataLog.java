package com.kaizen.team.kishaapp.datalog.data;


import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jesli Albert Bautista on 10/20/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */

@Getter
@Setter
public class DataLog implements Parcelable {


    private long id;
    private double lat;
    private double lng;
    private String message;
    private int typeOfDisaster;
    private long userId;
    private long timeLogged;


    private long dateCreated;
    private String fullName;

    public DataLog(){}

    protected DataLog(Parcel in) {
        id = in.readLong();
        lat = in.readDouble();
        lng = in.readDouble();
        message = in.readString();
        typeOfDisaster = in.readInt();
        userId = in.readLong();
        dateCreated = in.readLong();
        fullName = in.readString();
        timeLogged = in.readLong();
    }

    public static final Creator<DataLog> CREATOR = new Creator<DataLog>() {
        @Override
        public DataLog createFromParcel(Parcel in) {
            return new DataLog(in);
        }

        @Override
        public DataLog[] newArray(int size) {
            return new DataLog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeString(message);
        parcel.writeInt(typeOfDisaster);
        parcel.writeLong(userId);
        parcel.writeLong(dateCreated);
        parcel.writeString(fullName);
        parcel.writeLong(timeLogged);
    }
}
