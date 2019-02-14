package com.example.giftishare.data.remote.firebase;

import com.example.giftishare.data.model.Coupon;

public interface FirebaseDbHelper {

    void saveCoupon(Coupon coupon);

    FirebaseQueryLiveData getCoupons(String category);

}
