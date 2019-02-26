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
import com.example.giftishare.data.remote.ethereum.AppSmartContractHelper;
import com.example.giftishare.data.remote.ethereum.SmartContractHelper;
import com.example.giftishare.data.remote.firebase.AppFirebaseDbHelper;
import com.example.giftishare.data.remote.firebase.FirebaseDbHelper;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

import java8.util.concurrent.CompletableFuture;

/**
 * Created by KS-KIM on 19/02/08.
 */

public class AppDataManager implements DataManager {

    private volatile static DataManager INSTANCE = null;

    private final DbHelper mDbHelper;

    private final FirebaseDbHelper mFirebaseDbHelper;

    private final PreferencesHelper mPreferencesHelper;

    private final KeystoreGenerationHelper mKeystoreGenerationHelper;

    private final SmartContractHelper mSmartContractHelper;

    private AppDataManager(@NonNull AppDbHelper appDbHelper,
                           @NonNull AppFirebaseDbHelper appFirebaseDbHelper,
                           @NonNull AppPreferencesHelper appPreferencesHelper,
                           @NonNull AppKeystoreGenerationHelper appKeystoreGenerationHelper,
                           @NonNull AppSmartContractHelper appSmartContractHelper) {
        mDbHelper = appDbHelper;
        mFirebaseDbHelper = appFirebaseDbHelper;
        mKeystoreGenerationHelper = appKeystoreGenerationHelper;
        mPreferencesHelper = appPreferencesHelper;
        mSmartContractHelper = appSmartContractHelper;
    }

    public static DataManager getInstance(@NonNull AppDbHelper appDbHelper,
                                          @NonNull AppFirebaseDbHelper appFirebaseDbHelper,
                                          @NonNull AppPreferencesHelper appPreferencesHelper,
                                          @NonNull AppKeystoreGenerationHelper appKeystoreGenerationHelper,
                                          @NonNull AppSmartContractHelper appSmartContractHelper) {
        if (INSTANCE == null) {
            synchronized (AppDataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDataManager(appDbHelper, appFirebaseDbHelper,
                            appPreferencesHelper, appKeystoreGenerationHelper, appSmartContractHelper);
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
    public LiveData<List<Coupon>> getCoupons(Boolean isSale) {
        return mDbHelper.getCoupons(isSale);
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
    public void saveSaleCoupon(@NonNull Coupon coupon) {
        mFirebaseDbHelper.saveSaleCoupon(coupon);
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

    public CompletableFuture<TransactionReceipt> buyCoupon(@NonNull String uuid, @NonNull String price) {
        return mSmartContractHelper.buyCoupon(uuid, price);
    }

    public CompletableFuture<TransactionReceipt> resumeSaleCoupon(@NonNull String uuid) {
        return mSmartContractHelper.resumeSaleCoupon(uuid);
    }

    public CompletableFuture<TransactionReceipt> useCoupon(@NonNull String uuid) {
        return mSmartContractHelper.useCoupon(uuid);
    }

    public CompletableFuture<TransactionReceipt> addCoupon(@NonNull Coupon coupon) {
        return mSmartContractHelper.addCoupon(coupon);
    }

    public CompletableFuture<TransactionReceipt> stopSaleCoupon(String uuid) {
        return mSmartContractHelper.stopSaleCoupon(uuid);
    }

    public String loadWalletAddress() {
        return mSmartContractHelper.loadWalletAddress();
    }
}
