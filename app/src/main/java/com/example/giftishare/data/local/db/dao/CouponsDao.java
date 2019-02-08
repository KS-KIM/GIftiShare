package com.example.giftishare.data.local.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.giftishare.data.model.Coupon;

import java.util.List;

/**
 * Created by KS-KIM on 19/02/06.
 */

@Dao
public interface CouponsDao {

    @Insert
    void insert(Coupon coupon);

    @Query("SELECT * FROM coupons")
    LiveData<List<Coupon>> loadAll();

    @Delete
    void delete(Coupon coupon);


}
