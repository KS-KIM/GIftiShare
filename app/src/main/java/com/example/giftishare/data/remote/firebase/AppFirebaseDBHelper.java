package com.example.giftishare.data.remote.firebase;

import android.util.Log;

import com.example.giftishare.data.model.Coupon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AppFirebaseDBHelper implements FirebaseDBHelper {

    public static final String TAG = AppFirebaseDBHelper.class.getSimpleName();

    public final static String FIREBASE_CATEGORY_COUPONS = "coupons";

    private static volatile AppFirebaseDBHelper INSTANCE;

    private final DatabaseReference mDatabase;

    private AppFirebaseDBHelper() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static AppFirebaseDBHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (AppFirebaseDBHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppFirebaseDBHelper();
                }
            }
        }
        return INSTANCE;
    }

    // POST /coupons/:categoryName/:couponId
    @Override
    public void saveSaleCoupon(Coupon coupon) {
        // String key = coupon.getId();
        String category = coupon.getCategory();
        String key = mDatabase.child(FIREBASE_CATEGORY_COUPONS).child(category).push().getKey();
        Map<String, Object> couponValues = coupon.toMap();
        mDatabase.child(FIREBASE_CATEGORY_COUPONS)
                .child(category)
                .child(key)
                .updateChildren(couponValues);
    }


    @Override
    public void getSaleCoupons(String category, ValueEventListener listener) {
        mDatabase.child(FIREBASE_CATEGORY_COUPONS).child(category).addListenerForSingleValueEvent(listener);
    }

    @Override
    public void deleteCoupon(String category, String id) {
        mDatabase.child(FIREBASE_CATEGORY_COUPONS)
                .child(category)
                .orderByChild("id")
                .equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            child.getRef().removeValue();
                        }
                        Log.d(TAG, "Successfully removed data: " + dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: ", databaseError.toException());
                    }
                });
    }
}
