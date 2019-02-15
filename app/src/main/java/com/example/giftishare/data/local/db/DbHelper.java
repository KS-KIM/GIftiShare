package com.example.giftishare.data.local.db;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.giftishare.data.model.Coupon;

import java.util.List;

/**
 * Created by KS-KIM on 19/02/06.
 */

public interface DbHelper {

    LiveData<List<Coupon>> getAllPurchasedCoupons();

    void savePurchasedCoupon(@NonNull Coupon coupon);

    void deleteAllPurchasedCoupons();

    void deletePurchasedCoupon(@NonNull Coupon coupon);
}
