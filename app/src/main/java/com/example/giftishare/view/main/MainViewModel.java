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
import com.example.giftishare.utils.NotificationUtils;

import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    public final MutableLiveData<String> mWalletAddress = new MutableLiveData<>();

    public final MutableLiveData<String> mWalletBalance = new MutableLiveData<>();

    private final DataManager mDataManager;

    private final MutableLiveData<Event<CouponsCategoryType>> mOpenOnSaleCouponsEvent = new MutableLiveData<>();

    private final MutableLiveData<Event<String>> mShowTransactionEvent = new MutableLiveData<>();

    private final Context mContext;

    public MainViewModel(Application context, DataManager dataManager) {
        super(context);
        mContext = context.getApplicationContext();
        mDataManager = dataManager;
    }

    public LiveData<String> getWalletBalance() {
        return mWalletBalance;
    }

    public LiveData<Event<String>> getShowTransactionEvent() {
        return mShowTransactionEvent;
    }

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
            NotificationUtils.sendNotification(getApplication().getApplicationContext(),
                    1,
                    NotificationUtils.Channel.NOTICE,
                    "이더 전송 성공",
                    "결과를 확인하시려면 눌러주세요",
                    "https://blockscout.com/eth/ropsten/tx/" + TransactionReceipt.getTransactionHash());

            getBalance();
        }).exceptionally(TransactionReceipt -> {
            Log.i(TAG, "send failed. Detail Message: " + TransactionReceipt.getMessage());
            NotificationUtils.sendNotification(getApplication().getApplicationContext(),
                    1,
                    NotificationUtils.Channel.NOTICE,
                    "이더 전송 결과",
                    "이더 전송에 실패했습니다.",
                    "네트워크 상태와 이더리움 지갑 잔액을 확인하세요.",
                    null);
            return null;
        });
    }

    public void getBalance() {
        mWalletBalance.postValue("업데이트 중입니다...");
        mDataManager.getBalance().thenAccept(balance -> {
            Log.i(TAG, "wallet balance loaded: " + balance.getBalance());
            mWalletBalance.postValue(NumberFormat.getNumberInstance(Locale.US).format(balance.getBalance()) + " WEI");
        }).exceptionally(transactionReceipt -> {
            Log.d(TAG, "failed to load balance. network not available" + transactionReceipt.getMessage());
            mWalletBalance.postValue("잔액을 불러올 수 없습니다. 네트워크 상태를 확인하세요.");
            return null;
        });
    }

    public void getTransactionList() {
        mShowTransactionEvent.postValue(new Event("https://blockscout.com/eth/ropsten/address/" + mWalletAddress.getValue()));
    }
}
