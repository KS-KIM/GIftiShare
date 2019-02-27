package com.example.giftishare.view.buycoupons;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.model.Coupon;

import java.util.List;

public class BuyCouponsViewModel extends AndroidViewModel {

    public final LiveData<List<Coupon>> mCoupons;

    private final DataManager mDataManager;

    private final Context mContext;

    public LiveData<List<Coupon>> getCoupons() {
        return mCoupons;
    }

    public BuyCouponsViewModel(@NonNull Application context, DataManager dataManager) {
        super(context);
        mContext = context.getApplicationContext();
        mDataManager = dataManager;
        mCoupons = mDataManager.getCoupons(false);
    }

    public void start(Context context) {

    }
}
