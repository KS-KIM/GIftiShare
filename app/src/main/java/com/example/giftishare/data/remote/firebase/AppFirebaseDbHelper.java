package com.example.giftishare.data.remote.firebase;

import com.example.giftishare.data.model.Coupon;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class AppFirebaseDbHelper implements FirebaseDbHelper {

    public final static String FIREBASE_CATEGORY_COUPONS = "coupons";

    private static volatile AppFirebaseDbHelper INSTANCE;

    private final DatabaseReference mDatabase;

    private AppFirebaseDbHelper() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static AppFirebaseDbHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (AppFirebaseDbHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppFirebaseDbHelper();
                }
            }
        }
        return INSTANCE;
    }

    // POST /coupons/:categoryName/:couponId
    @Override
    public void saveCoupon(Coupon coupon) {
        // String key = coupon.getId();
        String category = coupon.getCategory();
        String key = mDatabase.child(FIREBASE_CATEGORY_COUPONS).child(category).push().getKey();
        Map<String, Object> couponValues = coupon.toMap();
        mDatabase.child(FIREBASE_CATEGORY_COUPONS)
                .child(category)
                .child(key)
                .updateChildren(couponValues);
    }

    /*
    @Override
    public FirebaseQueryLiveData getCoupons(String category) {
        // @TODO enum 타입으로 카테고리 분류 가능 (1: 카페, 2: 편의점 ...)
        FirebaseQueryLiveData coupons = new FirebaseQueryLiveData(mDatabase.child(category));
        return coupons;
    }
    */
}
