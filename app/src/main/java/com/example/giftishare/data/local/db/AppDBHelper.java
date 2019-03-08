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

public class AppDBHelper implements DBHelper {

    private static volatile AppDBHelper INSTANCE;

    private CouponsDao mCouponsDao;

    private AppExecutors mAppExecutors;

    private AppDBHelper(@NonNull AppExecutors appExecutors, @NonNull CouponsDao couponsDao) {
        mAppExecutors = appExecutors;
        mCouponsDao = couponsDao;
    }

    public static AppDBHelper getInstance(@NonNull AppExecutors appExecutors,
                                          @NonNull CouponsDao couponsDao) {
        if (INSTANCE == null) {
            synchronized (AppDBHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppDBHelper(appExecutors, couponsDao);
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
    public LiveData<List<Coupon>> getCoupons(Boolean isSale) {
        return mCouponsDao.getCoupons(isSale);
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
