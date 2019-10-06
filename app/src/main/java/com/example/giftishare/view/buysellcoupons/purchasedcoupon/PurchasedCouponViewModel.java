package com.example.giftishare.view.buysellcoupons.purchasedcoupon;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.model.Coupon;

public class PurchasedCouponViewModel extends AndroidViewModel {

    private final MutableLiveData<Coupon> mCoupon = new MutableLiveData<>();

    private final DataManager mDataManager;

    public PurchasedCouponViewModel(Application context, DataManager dataManager) {
        super(context);
        mDataManager = dataManager;
    }

    public void start(Coupon coupon) {
        mCoupon.setValue(coupon);
    }

    public LiveData<Coupon> getCoupon() {
        return mCoupon;
    }
}
