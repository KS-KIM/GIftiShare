package com.example.giftishare.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by KS-KIM on 19/02/04.
 */

@Entity(tableName = "coupons")
public final class Coupon {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public String mId;

    @ColumnInfo(name = "name")
    public String mName;

    @ColumnInfo(name = "category")
    public String mCategory;

    @ColumnInfo(name = "company")
    public String mCompany;

    @ColumnInfo(name = "price")
    public Integer mPrice;

    // Date is stored as a timestamp
    @ColumnInfo(name = "deadline")
    public Long mDeadline;

    @ColumnInfo(name = "owner")
    public String mOwner;

    // RoomDatabase에서는 오직 하나의 생성자만 허용하므로 나머지는 @Ignore를 통해 처리를 해주어야 함
    @Ignore
    public Coupon(@NonNull String id, @NonNull String name, @NonNull String category,
                  @NonNull String company, @NonNull Integer price, @NonNull Long deadline,
                  @NonNull String owner) {
        mId = id;
        mName = name;
        mCategory = category;
        mCompany = company;
        mPrice = price;
        mDeadline = deadline;
        mOwner = owner;
    }

    // generate new coupon from user
    public Coupon(@NonNull String name, @NonNull String category, @NonNull String company,
                  @NonNull Integer price, @NonNull Long deadline, @NonNull String owner) {
        this(UUID.randomUUID().toString(), name, category, company, price, deadline, owner);
    }
}
