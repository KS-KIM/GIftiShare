package com.example.giftishare.data.remote.firebase;

import com.example.giftishare.data.model.Coupon;
import com.google.firebase.database.ValueEventListener;

public interface FirebaseDBHelper {

    void saveSaleCoupon(Coupon coupon);

    void getSaleCoupons(String category, ValueEventListener listener);

    void deleteCoupon(String category, String id);
}
