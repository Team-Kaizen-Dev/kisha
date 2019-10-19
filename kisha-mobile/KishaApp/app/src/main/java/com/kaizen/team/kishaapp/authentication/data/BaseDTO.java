package com.kaizen.team.kishaapp.authentication.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jan Paolo Regalado on 6/10/19.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */

@Getter
@Setter
public class BaseDTO implements Parcelable {

    @NonNull
    private String id;
    private long dateCreated;
    private long dateModified;
    private String modifiedBy;

    public BaseDTO() {
    }

    protected BaseDTO(Parcel in) {
        id = in.readString();
        dateCreated = in.readLong();
        dateModified = in.readLong();
        modifiedBy = in.readString();
    }

    public static final Creator<BaseDTO> CREATOR = new Creator<BaseDTO>() {
        @Override
        public BaseDTO createFromParcel(Parcel in) {
            return new BaseDTO(in);
        }

        @Override
        public BaseDTO[] newArray(int size) {
            return new BaseDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(dateCreated);
        dest.writeLong(dateModified);
        dest.writeString(modifiedBy);
    }
}
