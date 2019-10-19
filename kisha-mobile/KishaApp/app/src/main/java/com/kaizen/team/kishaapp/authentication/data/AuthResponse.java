package com.kaizen.team.kishaapp.authentication.data;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jan Paolo Regalado on 6/11/19.
 * jan.regalado@safesat.com.ph
 * Sattelite GPS (GPS Tracking and Asset Management System)
 */

@Getter
@Setter
public class AuthResponse implements Parcelable {

    private String access_token;
    private String token_type;
    private String expires_in;
    private String scope;
    private String refresh_token;

    protected AuthResponse(Parcel in) {
        access_token = in.readString();
        token_type = in.readString();
        expires_in = in.readString();
        scope = in.readString();
        refresh_token = in.readString();
    }

    public static final Creator<AuthResponse> CREATOR = new Creator<AuthResponse>() {
        @Override
        public AuthResponse createFromParcel(Parcel in) {
            return new AuthResponse(in);
        }

        @Override
        public AuthResponse[] newArray(int size) {
            return new AuthResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_token);
        dest.writeString(token_type);
        dest.writeString(expires_in);
        dest.writeString(scope);
        dest.writeString(refresh_token);
    }
}
