package com.example.giftishare.view.buycoupon;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.model.Coupon;

public class BuyCouponViewModel extends AndroidViewModel {

    public final MutableLiveData<Coupon> mCoupon = new MutableLiveData<>();

    private final DataManager mDataManager;

    public BuyCouponViewModel(Application context, DataManager dataManager) {
        super(context);
        mDataManager = dataManager;
    }

    public void start(Coupon coupon) {
        mCoupon.setValue(coupon);
    }
}
