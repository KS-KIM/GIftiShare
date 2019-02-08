package com.example.giftishare.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

/**
 * Created by KS-KIM on 19/02/04.
 */
@Entity(tableName = "coupons")
public class Coupon {

    @PrimaryKey
    @ColumnInfo(name = "id")
    String mId;

    @ColumnInfo(name = "name")
    String mName;

    @ColumnInfo(name = "category")
    String mCategory;

    @ColumnInfo(name = "company")
    String mCompany;

    @ColumnInfo(name = "price")
    Integer mPrice;

    @ColumnInfo(name = "deadline")
    Date mDeadline;

    @ColumnInfo(name = "owner")
    String mOwner;

    // get coupon from firebase realtime database
    public Coupon(@NonNull String id, @NonNull String name, @NonNull String category,
                  @NonNull String company, @NonNull Integer price, @NonNull Date deadline,
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
                  @NonNull Integer price, @NonNull Date deadline, @NonNull String owner) {
        this(UUID.randomUUID().toString(), name, category, company, price, deadline, owner);
    }
}