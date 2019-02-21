package com.example.giftishare.view.addcoupon;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.giftishare.Event;
import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.utils.CategoryNameMapperUtils;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddCouponViewModel extends AndroidViewModel {

    public final static String TAG = AddCouponViewModel.class.getSimpleName();

    public final MutableLiveData<String> mCouponName = new MutableLiveData<>();

    public final MutableLiveData<String> mCompanyName = new MutableLiveData<>();

    public final MutableLiveData<String> mPrice = new MutableLiveData<>();

    public final MutableLiveData<String> mBarcode = new MutableLiveData<>();

    public final MutableLiveData<Date> mDeadline = new MutableLiveData<>();

    public final MutableLiveData<String> mCouponCategory = new MutableLiveData<>();

    private final DataManager mDataManager;

    private final MutableLiveData<Event<Object>> mTransactionGasLackEvent = new MutableLiveData<>();

    public AddCouponViewModel(Application context, DataManager dataManager) {
        super(context);
        mDataManager = dataManager;
    }

    public MutableLiveData<Event<Object>> getTransactionGasLackEvent() {
        return mTransactionGasLackEvent;
    }

    public boolean isEmptyField(String field) {
        return field == null || field.replace(" ", "").equals("");
    }

    public void setDeadline(int year, int month, int date) {
        Date deadline = new GregorianCalendar(year, month, date).getTime();
        mDeadline.setValue(deadline);
    }

    private String loadWalletAddress() {
        String walletAddress = null;
        try {
            String password = mDataManager.getWalletPassword();
            String path = mDataManager.getWalletPath();
            Credentials credential = WalletUtils.loadCredentials(password, path);
            walletAddress = credential.getAddress();
        } catch (IOException
                | CipherException e) {
            e.printStackTrace();
        }
        return walletAddress;
    }

    public void createCoupon() {
        String walletAddress = loadWalletAddress();
        if (walletAddress != null) {
            Coupon coupon = new Coupon(
                    mCouponName.getValue(),
                    CategoryNameMapperUtils.toEngName(mCouponCategory.getValue()),
                    mCompanyName.getValue(),
                    mPrice.getValue(),
                    mBarcode.getValue(),
                    mDeadline.getValue().getTime(),
                    walletAddress);
            mDataManager.addCoupon(coupon).thenAccept(transactionReceipt -> {
                Log.d(TAG, "transaction accepted. check at https://blockscout.com/eth/ropsten/tx/" +
                        transactionReceipt.getTransactionHash());
                mDataManager.saveCoupon(coupon);
                mDataManager.saveSaleCoupon(coupon);
            }).exceptionally(transactionReceipt  -> {
                Log.d(TAG, transactionReceipt.getMessage());
                mTransactionGasLackEvent.setValue(new Event<>(new Object()));
                return null;
            });
        }
    }
}