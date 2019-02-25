package com.example.giftishare.view.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.giftishare.Event;
import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.model.CouponsCategoryType;

import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    public final MutableLiveData<String> mWalletAddress = new MutableLiveData<>();

    public final MutableLiveData<String> mWalletBalance = new MutableLiveData<>();

    private final DataManager mDataManager;

    private final MutableLiveData<Event<CouponsCategoryType>> mOpenOnSaleCouponsEvent = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> mSendEtherCompleteEvent = new MutableLiveData<>();

    private final Context mContext;

    public MainViewModel(Application context, DataManager dataManager) {
        super(context);
        mContext = context.getApplicationContext();
        mDataManager = dataManager;
    }

    public LiveData<String> getWalletBalance() {
        return mWalletBalance;
    }

    // @TODO Notification으로 변경 예정
    public LiveData<Event<String>> getSendEtherCompleteEvent() {
        return mSendEtherCompleteEvent;
    }

    /**
     * @apiNote 지갑 잔액 테스트용. 추후 지갑 관리 액티비티에서 호출
     */
    public void start() {
        String walletAddress = mDataManager.getCredentials().getAddress();
        mWalletAddress.setValue(walletAddress);
        mWalletBalance.setValue("잔액 조회중...");
        getBalance();
    }

    public LiveData<Event<CouponsCategoryType>> getOpenOnSaleCouponsEvent() {
        return mOpenOnSaleCouponsEvent;
    }

    public void openOnSaleCoupons(CouponsCategoryType couponsCategoryType) {
        Log.d(TAG, "open onSale coupons function called with " + couponsCategoryType.toEng());
        mOpenOnSaleCouponsEvent.setValue(new Event<>(couponsCategoryType));
    }

    public void sendEther(String toAddress, String weiValue) {
        BigDecimal balance = new BigDecimal(weiValue);
        mDataManager.sendEther(toAddress, balance).thenAccept(TransactionReceipt -> {
            Log.i(TAG, "send complete. blockHash: " + TransactionReceipt.getBlockHash());
            mSendEtherCompleteEvent.postValue(new Event<>("송금을 완료했습니다."));
            getBalance();
        }).exceptionally(TransactionReceipt -> {
            Log.i(TAG, "send failed. Detail Message: " + TransactionReceipt.getMessage());
            mSendEtherCompleteEvent.postValue(new Event<>("송금에 실패했습니다. 다시 시도해주세요."));
            return null;
        });
    }

    public void getBalance() {
        mDataManager.getBalance().thenAccept(balance -> {
            String walletBalance = balance.getBalance().toString();
            Log.i(TAG, "wallet balance loaded: " + walletBalance);
            mWalletBalance.postValue(walletBalance + " wei");
        }).exceptionally(transactionReceipt -> {
            Log.d(TAG, "failed to load balance. network not available" + transactionReceipt.getMessage());
            mWalletBalance.postValue("잔액을 불러올 수 없습니다. 네트워크 상태를 확인하세요.");
            return null;
        });
    }
}
