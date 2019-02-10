package com.example.giftishare.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by KS-KIM on 19/02/06.
 */

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_FILE_NAME = "USER_PREF";

    private static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";

    private static final String PREF_KEY_WALLET_PASSWORD = "PREF_KEY_WALLET_PASSWORD";

    private static final String PREF_KEY_WALLET_PATH = "PREF_KEY_WALLET_PATH";

    private final SharedPreferences mPrefs;

    public AppPreferencesHelper(@NonNull Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getUserName() {
        return mPrefs.getString(PREF_KEY_USER_NAME, null);
    }

    @Override
    public void setUserName(@NonNull String userName) {
        mPrefs.edit().putString(PREF_KEY_USER_NAME, userName).apply();
    }

    @Override
    public String getWalletPassword() {
        return mPrefs.getString(PREF_KEY_WALLET_PASSWORD, null);
    }

    @Override
    public void setWalletPassword(@NonNull String password) {
        mPrefs.edit().putString(PREF_KEY_WALLET_PASSWORD, password).apply();
    }

    @Override
    public String getWalletPath() {
        return mPrefs.getString(PREF_KEY_WALLET_PATH, null);
    }

    @Override
    public void setWalletPath(@NonNull String walletPath) {
        mPrefs.edit().putString(PREF_KEY_WALLET_PATH, walletPath).apply();
    }
}
