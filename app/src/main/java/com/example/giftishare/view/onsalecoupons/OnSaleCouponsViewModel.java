package com.example.giftishare.view.onsalecoupons;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.util.Log;

import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.model.Coupon;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.giftishare.data.remote.firebase.AppFirebaseDbHelper.FIREBASE_CATEGORY_COUPONS;

public class OnSaleCouponsViewModel extends AndroidViewModel {

    private static final String TAG = OnSaleCouponsViewModel.class.getSimpleName();

    private final MutableLiveData<List<Coupon>> mCoupons = new MutableLiveData<>();

    private final DataManager mDataManager;

    private DatabaseReference mDatabaseReference;

    private final Context mContext;

    public LiveData<List<Coupon>> getCoupons() {
        return mCoupons;
    }

    public final LiveData<Boolean> empty = Transformations.map(mCoupons, input -> input.isEmpty());

    public OnSaleCouponsViewModel(Application context, DataManager dataManager) {
        super(context);
        mContext = context.getApplicationContext();
        mDataManager = dataManager;
    }

    public void start(String category) {
        FirebaseApp.initializeApp(mContext);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase
                .getReference().child(FIREBASE_CATEGORY_COUPONS).child(category);
        loadCoupons();
    }

    public void loadCoupons() {
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Coupon> coupons = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Coupon coupon = ds.getValue(Coupon.class);
                    Log.d(TAG, coupon.getBarcode());
                    coupons.add(coupon);
                }
                Log.d(TAG, "Coupon list Created. Number of coupons: " + coupons.size());
                mCoupons.setValue(coupons);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mCoupons.setValue(new ArrayList<>());
            }
        });
    }
}
