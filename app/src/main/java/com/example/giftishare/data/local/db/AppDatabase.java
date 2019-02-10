package com.example.giftishare.data.local.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.giftishare.data.local.db.dao.CouponsDao;

/**
 * Created by KS-KIM on 19/02/06.
 */

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CouponsDao couponDao();

    private static final Object sLock = new Object();

    public static AppDatabase getInstance(@NonNull Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "Coupons.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
