package com.kaizen.team.kishaapp.user;


import android.content.Context;
import android.content.SharedPreferences;

import com.kaizen.team.kishaapp.core.KishaApp;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class UserPreferences {
    private static final String PREFERENCE = "com.kaizen.team.kishaapp.authentication.PREFERENCE";


    private static final String PREF_ACCOUNT_ID = "com.kaizen.team.kishaapp.authentication.PREF_ACCOUNT_ID";
    private static UserPreferences INSTANCE;
    private SharedPreferences sharedPreferences;

    public static UserPreferences getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserPreferences(KishaApp.getContext().
                    getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE));
        }
        return INSTANCE;
    }

    private UserPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    public void setAccountId(long accountId) {
        sharedPreferences.edit().putLong(PREF_ACCOUNT_ID, accountId).apply();
    }

    public long getAccountId() {
        return sharedPreferences.getLong(PREF_ACCOUNT_ID, 0);
    }

    public void clearLoggedInUser() {
        setAccountId(0);
    }
}
