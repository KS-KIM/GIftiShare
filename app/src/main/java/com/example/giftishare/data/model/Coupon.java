package com.example.giftishare.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by KS-KIM on 19/02/04.
 */

@Entity(tableName = "coupons")
public final class Coupon implements Serializable {

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
    public String mPrice;

    @ColumnInfo(name = "barcode")
    public String mBarcode;

    // Date is stored as a timestamp
    @ColumnInfo(name = "deadline")
    public Long mDeadline;

    @ColumnInfo(name = "owner")
    public String mOwner;

    @ColumnInfo(name = "onSale")
    public Boolean mOnSale;

    @ColumnInfo(name = "used")
    public Boolean mUsed;

    public Coupon() {
        // require empty constructor
    }

    @Ignore
    public Coupon(@NonNull String id, @NonNull String name, @NonNull String category,
                  @NonNull String company, @NonNull String price, @NonNull String barcode,
                  @NonNull Long deadline, @NonNull String owner, @NonNull Boolean onSale,
                  @NonNull Boolean used) {
        mId = id;
        mName = name;
        mCategory = category;
        mCompany = company;
        mPrice = price;
        mBarcode = barcode;
        mDeadline = deadline;
        mOwner = owner;
        mOnSale = onSale;
        mUsed = used;
    }

    // generate new coupon from user
    @Ignore
    public Coupon(@NonNull String name, @NonNull String category, @NonNull String company,
                  @NonNull String price, @NonNull String barcode, @NonNull Long deadline,
                  @NonNull String owner) {
        this(UUID.randomUUID().toString(), name, category, company, price, barcode, deadline, owner,
                true, false);
    }

    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        this.mCompany = company;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        this.mPrice = price;
    }

    public String getBarcode() {
        return mBarcode;
    }

    public void setBarcode(String barcode) {
        this.mBarcode = barcode;
    }

    public Long getDeadline() {
        return mDeadline;
    }

    public void setDeadline(Long deadline) {
        this.mDeadline = deadline;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        this.mOwner = owner;
    }

    public Boolean isOnSale() {
        return mOnSale;
    }

    public void setOnSale(Boolean onSale) {
        this.mOnSale = onSale;
    }

    public Boolean isUsed() {
        return mUsed;
    }

    public void setUsed(Boolean used) {
        this.mUsed = used;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(mDeadline);
    }

    @Exclude
    @Ignore
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", mId);
        result.put("name", mName);
        result.put("category", mCategory);
        result.put("company", mCompany);
        result.put("price", mPrice);
        result.put("barcode", mBarcode);
        result.put("deadline", mDeadline);
        result.put("owner", mOwner);
        result.put("onSale", mOnSale);
        result.put("used", mUsed);
        return result;
    }
}
