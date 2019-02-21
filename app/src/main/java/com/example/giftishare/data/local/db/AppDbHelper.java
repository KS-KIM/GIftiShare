package com.example.giftishare.data.local.db;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.giftishare.data.local.db.dao.CouponsDao;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.utils.AppExecutors;

import java.util.List;

/**
 * Created by KS-KIM on 19/02/06.
 */

public class AppDbHelper implements DbHelper {

    private static volatile AppDbHelper INSTANCE;

    private CouponsDao mCouponsDao;

    private AppExecutors mAppExecutors;

    private AppDbHelper(@NonNull AppExecutors appExecutors, @NonNull CouponsDao couponsDao) {
        mAppExecutors = appExecutors;
        mCouponsDao = couponsDao;
    }

    public static AppDbHelper getInstance(@NonNull AppExecutors appExecutors,
                                          @NonNull CouponsDao couponsDao) {
        if (INSTANCE == null) {
            synchronized (AppDbHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDbHelper(appExecutors, couponsDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Coupon>> getAllCoupons() {
        return mCouponsDao.loadAll();
    }

    @Override
    public void saveCoupon(@NonNull Coupon coupon) {
        Runnable runnable = () -> mCouponsDao.insert(coupon);
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllCoupons() {
        Runnable runnable = () -> mCouponsDao.deleteAllCoupons();
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteCoupon(@NonNull Coupon coupon) {
        Runnable runnable = () -> mCouponsDao.delete(coupon);
        mAppExecutors.diskIO().execute(runnable);
    }
}
