package com.kaizen.team.kishaapp.authentication;


import android.content.Context;
import android.content.SharedPreferences;

import com.kaizen.team.kishaapp.core.KishaApp;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class AuthPreferences {
    private static final String PREFERENCE = "com.kaizen.team.kishaapp.authentication.PREFERENCE";

    private static final String PREF_AUTH_KEY = "com.kaizen.team.kishaapp.authentication.AUTH_KEY";
    private static final String PREF_TOKEN_AGE = "com.kaizen.team.kishaapp.authentication.TOKEN_AGE";

    private static final String USER_PREFERENCE = "com.kaizen.team.kishaapp.authentication.SHAREDPREFERENCE";
    private static final String PREF_LOGIN_USER_NAME = "com.kaizen.team.kishaapp.authentication.PREF_LOGIN_USER_NAME";
    private static final String PREF_LOGIN_PASSWORD = "com.kaizen.team.kishaapp.authentication.PREF_LOGIN_PASSWORD";
    private static final String PREF_ACCOUNT_ID = "com.kaizen.team.kishaapp.authentication.PREF_ACCOUNT_ID";

    public synchronized static SharedPreferences getPrefs() {
        return KishaApp.getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
    }

    public static String getAuthKey() {
        return getPrefs().getString(PREF_AUTH_KEY, "");
    }

    public static void setAuthKey(String value) {
        getPrefs().edit().putString(PREF_AUTH_KEY, value).apply();
    }

    public static long getTokenAge() {
        return getPrefs().getLong(PREF_TOKEN_AGE, 0);
    }

    public static void setTokenAge(long value) {
        getPrefs().edit().putLong(PREF_TOKEN_AGE, value).apply();
    }

    public static void clear() {
        setAuthKey(null);
        setTokenAge(0);
    }



    public static void setLoginUserName(String userName) {
        getPrefs().edit().putString(PREF_LOGIN_USER_NAME, userName).apply();
    }

    public static String getLoginUserName() {
        return getPrefs().getString(PREF_LOGIN_USER_NAME, "");
    }

    public static void setLoginPassword(String password) {
        getPrefs().edit().putString(PREF_LOGIN_PASSWORD, password).apply();
    }

    public static String getLoginPassword() {
        return getPrefs().getString(PREF_LOGIN_PASSWORD, "");
    }


    public static void setAccountId(long accountId) {
        getPrefs().edit().putLong(PREF_ACCOUNT_ID, accountId).apply();
    }

    public static long getAccountId() {
        return getPrefs().getLong(PREF_ACCOUNT_ID, 0);
    }

    public static void clearLoggedInUser() {
        setLoginPassword(null);
        setLoginUserName(null);
        setAccountId(0);
    }
}
