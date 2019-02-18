package com.example.giftishare.view.addcoupon;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

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

    public final MutableLiveData<String> mCouponName = new MutableLiveData<>();

    public final MutableLiveData<String> mCompanyName = new MutableLiveData<>();

    public final MutableLiveData<String> mPrice = new MutableLiveData<>();

    public final MutableLiveData<String> mBarcode = new MutableLiveData<>();

    public final MutableLiveData<Date> mDeadline = new MutableLiveData<>();

    public final MutableLiveData<String> mCouponCategory = new MutableLiveData<>();

    private final DataManager mDataManager;

    public AddCouponViewModel(Application context, DataManager dataManager) {
        super(context);
        mDataManager = dataManager;
    }

    public boolean isEmptyField(String field) {
        if (field == null) {
            return true;
        } else {
            return field.replace(" ", "").equals("");
        }
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
            mDataManager.saveCoupon(coupon);
        }
    }
}