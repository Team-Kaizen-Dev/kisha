package com.kaizen.team.kishaapp.user.data;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User implements Parcelable {
    private long id;
    private String fullName;
    private String username;
    private String contactNumber;
    private double lat;
    private double lng;
    private String address;
    private String password;

    public User() {
    }

    protected User(Parcel in) {
        id = in.readLong();
        fullName = in.readString();
        username = in.readString();
        contactNumber = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        address = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(fullName);
        parcel.writeString(username);
        parcel.writeString(contactNumber);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeString(address);
        parcel.writeString(password);
    }
}
