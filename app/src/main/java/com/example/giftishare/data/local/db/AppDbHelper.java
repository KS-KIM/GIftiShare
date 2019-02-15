package com.example.giftishare.data.local.db;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.giftishare.data.local.db.dao.CouponsDao;
import com.example.giftishare.data.model.Coupon;

import java.util.List;

/**
 * Created by KS-KIM on 19/02/06.
 */

public class AppDbHelper implements DbHelper {

    private static volatile AppDbHelper INSTANCE;

    private CouponsDao mCouponsDao;

    private AppDbHelper(@NonNull CouponsDao couponsDao) {
        mCouponsDao = couponsDao;
    }

    public static AppDbHelper getInstance(@NonNull CouponsDao couponsDao) {
        if (INSTANCE == null) {
            synchronized (AppDbHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDbHelper(couponsDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Coupon>> getAllPurchasedCoupons() {
        return mCouponsDao.loadAll();
    }

    @Override
    public void savePurchasedCoupon(@NonNull Coupon coupon) {
        mCouponsDao.insert(coupon);
    }

    @Override
    public void deleteAllPurchasedCoupons() {
        mCouponsDao.deleteAllCoupons();
    }

    @Override
    public void deletePurchasedCoupon(@NonNull Coupon coupon) {
        mCouponsDao.delete(coupon);
    }
}
