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
import com.google.firebase.database.ValueEventListener;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
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

    /* Room database */
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

    /* Firebase */
    @Override
    public void saveSaleCoupon(@NonNull Coupon coupon) {
        mFirebaseDbHelper.saveSaleCoupon(coupon);
    }

    @Override
    public void getSaleCoupons(String category, ValueEventListener listener) {
        mFirebaseDbHelper.getSaleCoupons(category, listener);
    }

    @Override
    public void deleteCoupon(String category, String id) {
        mFirebaseDbHelper.deleteCoupon(category, id);
    }

    public void deleteSaleCoupon(Coupon coupon) {
        deleteCoupon(coupon.getCategory(), coupon.getId());
    }

    /* ethereum wallet */
    @Override
    public String createWallet(@NonNull String password) {
        return mKeystoreGenerationHelper.createWallet(password);
    }

    /* Preferences */
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

    /* ethereum transaction */
    @Override
    public CompletableFuture<TransactionReceipt> buyCoupon(@NonNull String uuid, @NonNull String price) {
        return mSmartContractHelper.buyCoupon(uuid, price);
    }

    @Override
    public CompletableFuture<TransactionReceipt> resumeSaleCoupon(@NonNull String uuid) {
        return mSmartContractHelper.resumeSaleCoupon(uuid);
    }

    @Override
    public CompletableFuture<TransactionReceipt> useCoupon(@NonNull String uuid) {
        return mSmartContractHelper.useCoupon(uuid);
    }

    @Override
    public CompletableFuture<TransactionReceipt> addCoupon(@NonNull Coupon coupon) {
        return mSmartContractHelper.addCoupon(coupon);
    }

    @Override
    public CompletableFuture<TransactionReceipt> stopSaleCoupon(String uuid) {
        return mSmartContractHelper.stopSaleCoupon(uuid);
    }

    @Override
    public void loadCredentialsAndSmartContract(String password, String source) {
        mSmartContractHelper.loadCredentialsAndSmartContract(password, source);
    }

    @Override
    public Credentials getCredentials() {
        return mSmartContractHelper.getCredentials();
    }

    @Override
    public CompletableFuture<EthGetBalance> getBalance() {
        return mSmartContractHelper.getBalance();
    }

    @Override
    public CompletableFuture<TransactionReceipt> sendEther(String toAddress, BigDecimal balance) {
        return mSmartContractHelper.sendEther(toAddress, balance);
    }
}
