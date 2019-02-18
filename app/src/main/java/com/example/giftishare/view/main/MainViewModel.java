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

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private final DataManager mDataManager;

    private final MutableLiveData<Event<CouponsCategoryType>> mOpenOnSaleCouponsEvent = new MutableLiveData<>();

    private final Context mContext;

    public MainViewModel(Application context, DataManager dataManager) {
        super(context);
        mContext = context.getApplicationContext();
        mDataManager = dataManager;
    }

    public LiveData<Event<CouponsCategoryType>> getOpenOnSaleCouponsEvent() {
        return mOpenOnSaleCouponsEvent;
    }

    public void openOnSaleCoupons(CouponsCategoryType couponsCategoryType) {
        Log.d(TAG, "open onSale coupons function called with " + couponsCategoryType.toEng());
        mOpenOnSaleCouponsEvent.setValue(new Event<>(couponsCategoryType));
    }
}
