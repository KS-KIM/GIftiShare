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
import com.example.giftishare.data.remote.firebase.AppFirebaseDbHelper;
import com.example.giftishare.data.remote.firebase.FirebaseDbHelper;
import com.example.giftishare.data.remote.firebase.FirebaseQueryLiveData;

import java.util.List;

/**
 * Created by KS-KIM on 19/02/08.
 */

public class AppDataManager implements DataManager {

    private volatile static DataManager INSTANCE = null;

    private final DbHelper mDbHelper;

    private final FirebaseDbHelper mFirebaseDbHelper;

    private final KeystoreGenerationHelper mKeystoreGenerationHelper;

    private final PreferencesHelper mPreferencesHelper;

    private AppDataManager(@NonNull AppDbHelper appDbHelper,
                           @NonNull AppFirebaseDbHelper appFirebaseDbHelper,
                           @NonNull AppKeystoreGenerationHelper appKeystoreGenerationHelper,
                           @NonNull AppPreferencesHelper appPreferencesHelper) {
        mDbHelper = appDbHelper;
        mFirebaseDbHelper = appFirebaseDbHelper;
        mKeystoreGenerationHelper = appKeystoreGenerationHelper;
        mPreferencesHelper = appPreferencesHelper;
    }

    public static DataManager getInstance(@NonNull AppDbHelper appDbHelper,
                                          @NonNull AppFirebaseDbHelper appFirebaseDbHelper,
                                          @NonNull AppKeystoreGenerationHelper appKeystoreGenerationHelper,
                                          @NonNull AppPreferencesHelper appPreferencesHelper) {
        if (INSTANCE == null) {
            synchronized (AppDataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDataManager(appDbHelper, appFirebaseDbHelper, appKeystoreGenerationHelper, appPreferencesHelper);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public LiveData<List<Coupon>> getAllPurchasedCoupons() {
        return mDbHelper.getAllPurchasedCoupons();
    }


    @Override
    public void savePurchasedCoupon(@NonNull Coupon coupon) {
        mDbHelper.savePurchasedCoupon(coupon);
    }

    @Override
    public void deleteAllPurchasedCoupons() {
        mDbHelper.deleteAllPurchasedCoupons();
    }

    @Override
    public void deletePurchasedCoupon(@NonNull Coupon coupon) {
        mDbHelper.deletePurchasedCoupon(coupon);
    }

    @Override
    public void saveCoupon(Coupon coupon) {
        mFirebaseDbHelper.saveCoupon(coupon);
    }

    @Override
    public FirebaseQueryLiveData getCoupons(String category) {
        return mFirebaseDbHelper.getCoupons(category);
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

    @Override
    public void setWalletPath(@NonNull String walletPath) {
        mPreferencesHelper.setWalletPath(walletPath);
    }
}
