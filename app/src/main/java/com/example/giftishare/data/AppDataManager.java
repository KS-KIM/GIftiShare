package com.example.giftishare.data;

import android.support.annotation.NonNull;

import com.example.giftishare.data.local.file.AppKeystoreGenerationHelper;
import com.example.giftishare.data.local.file.KeystoreGenerationHelper;
import com.example.giftishare.data.local.prefs.AppPreferencesHelper;
import com.example.giftishare.data.local.prefs.PreferencesHelper;

/**
 * Created by KS-KIM on 19/02/08.
 */

public class AppDataManager implements DataManager {

    private volatile static DataManager INSTANCE = null;

    private final KeystoreGenerationHelper mKeystoreGenerationHelper;

    private final PreferencesHelper mPreferencesHelper;

    private AppDataManager(@NonNull AppKeystoreGenerationHelper appKeystoreGenerationHelper,
                           @NonNull AppPreferencesHelper appPreferencesHelper) {
        mKeystoreGenerationHelper = appKeystoreGenerationHelper;
        mPreferencesHelper = appPreferencesHelper;
    }

    public static DataManager getInstance(@NonNull AppKeystoreGenerationHelper appKeystoreGenerationHelper,
                                          @NonNull AppPreferencesHelper appPreferencesHelper) {
        if (INSTANCE == null) {
            synchronized (AppDataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDataManager(appKeystoreGenerationHelper, appPreferencesHelper);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public String createWallet(@NonNull String password) {
        return mKeystoreGenerationHelper.createWallet(password);
    }

    @Override
    public String getUserName() {
        return mPreferencesHelper.getUserName();
    }

    @Override
    public void setUserName(@NonNull String userName) {
        mPreferencesHelper.setUserName(userName);
    }

    @Override
    public String getWalletPassword() {
        return mPreferencesHelper.getWalletPassword();
    }

    @Override
    public void setWalletPassword(@NonNull String password) {
        mPreferencesHelper.setWalletPassword(password);
    }

    @Override
    public String getWalletPath() {
        return mPreferencesHelper.getWalletPath();
    }

    public void setWalletPath(@NonNull String walletPath) {
        mPreferencesHelper.setWalletPath(walletPath);
    }
}
