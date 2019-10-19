package com.kaizen.team.kishaapp.authentication.data;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User extends BaseDTO implements Parcelable {
    private MetaData metadata;

    protected User(Parcel in) {
        super(in);
        metadata = in.readParcelable(MetaData.class.getClassLoader());
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
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(metadata, flags);
    }
}
