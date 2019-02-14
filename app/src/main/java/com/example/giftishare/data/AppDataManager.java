package com.example.giftishare.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.giftishare.data.local.db.AppDbHelper;
import com.example.giftishare.data.local.db.DbHelper;
import com.example.giftishare.data.local.file.AppKeystoreGenerationHelper;
import com.example.giftishare.data.local.file.KeystoreGenerationHelper;
import com.example.giftishare.data.local.prefs.AppPreferencesHelper;
import com.example.giftishare.data.local.prefs.PreferencesHelper;
import com.example.giftishare.data.model.Coupon;

import java.util.List;

/**
 * Created by KS-KIM on 19/02/08.
 */

public class AppDataManager implements DataManager {

    private volatile static DataManager INSTANCE = null;

    private final DbHelper mDbHelper;

    private final KeystoreGenerationHelper mKeystoreGenerationHelper;

    private final PreferencesHelper mPreferencesHelper;

    private AppDataManager(@NonNull AppDbHelper appDbHelper,
                           @NonNull AppKeystoreGenerationHelper appKeystoreGenerationHelper,
                           @NonNull AppPreferencesHelper appPreferencesHelper) {
        mDbHelper = appDbHelper;
        mKeystoreGenerationHelper = appKeystoreGenerationHelper;
        mPreferencesHelper = appPreferencesHelper;
    }

    public static DataManager getInstance(@NonNull AppDbHelper appDbHelper,
                                          @NonNull AppKeystoreGenerationHelper appKeystoreGenerationHelper,
                                          @NonNull AppPreferencesHelper appPreferencesHelper) {
        if (INSTANCE == null) {
            synchronized (AppDataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDataManager(appDbHelper, appKeystoreGenerationHelper, appPreferencesHelper);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public LiveData<List<Coupon>> getAllCoupons() {
        return mDbHelper.getAllCoupons();
    }


    @Override
    public void saveCoupon(@NonNull Coupon coupon) {
        mDbHelper.saveCoupon(coupon);
    }

    @Override
    public void deleteAllCoupons() {
        mDbHelper.deleteAllCoupons();
    }

    @Override
    public void deleteCoupon(@NonNull Coupon coupon) {
        mDbHelper.deleteCoupon(coupon);
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
