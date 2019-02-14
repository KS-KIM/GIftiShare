package com.example.giftishare.data.remote.firebase;

import android.arch.lifecycle.LiveData;

import com.example.giftishare.data.model.Coupon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseQueryLiveData extends LiveData<List<Coupon>> {

    private final DatabaseReference mDatabaseReference;

    public FirebaseQueryLiveData(DatabaseReference databaseReference) {
        mDatabaseReference = databaseReference;
    }

    @Override
    protected void onActive() {
        mDatabaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Coupon> coupons = new ArrayList<>();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Coupon coupon = ds.getValue(Coupon.class);
                    coupons.add(coupon);
                }
                setValue(coupons);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // setValue(new ArrayList<>());
            }
        });
    }
}
