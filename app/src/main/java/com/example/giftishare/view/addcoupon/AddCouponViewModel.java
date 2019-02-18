package com.example.giftishare.view.addcoupon;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;

import com.example.giftishare.Event;
import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.utils.CategoryNameMapperUtils;

import java.util.Date;
import java.util.regex.Pattern;

public class AddCouponViewModel extends AndroidViewModel {

    public Long mDeadLine = null;
    public String mErrorId = null;
    public boolean getErrorBl = true;
    public String mErrorType = null;
    public String mCouponCategory = null;

    public final MutableLiveData<String> mCouponName = new MutableLiveData<>();

    public final MutableLiveData<String> mBarCode = new MutableLiveData<>();

    public final MutableLiveData<String> mSalePrice = new MutableLiveData<>();

    public final MutableLiveData<String> mCompanyName = new MutableLiveData<>();



    private final DataManager mDataManager;

    private final MutableLiveData<Event<Object>> mAddCouponErrorEvent = new MutableLiveData<>();

    public MutableLiveData<String> getBarCode(){return mBarCode;}
    public MutableLiveData<String> getSalePrice(){return mSalePrice;}
    public MutableLiveData<String> getCompanyName(){return mCompanyName;}
    public String getErrorId(){return mErrorId;}
    public boolean getErrorBl(){return getErrorBl;}
    public String getErrorType(){return mErrorType;}

    public void setDeadLine(Date mDeadLine){this.mDeadLine = mDeadLine.getTime();}
    public void setCouponCategory(String mCouponCategory){this.mCouponCategory = mCouponCategory;}


    public MutableLiveData<Event<Object>> getAddCouponErrorEvent() {return mAddCouponErrorEvent;}

    public boolean isBarCodeValueCheck() {return !TextUtils.isEmpty(mBarCode.getValue());}
    public boolean isSalePriceValueCheck() {return !TextUtils.isEmpty(mSalePrice.getValue());}
    public boolean isCompanyNameValueCheck() {return !TextUtils.isEmpty(mCompanyName.getValue());}
    public boolean isDeadLineCheck(){return ((mDeadLine != null) ? true : false);}

    public AddCouponViewModel(Application context, DataManager dataManager) {
        super(context);
        mDataManager = dataManager;
    }

    public void createCoupon(){
        Coupon coupon = new Coupon(mCouponName.getValue(),mCouponCategory,mCompanyName.getValue(),
                (mSalePrice.getValue()),mBarCode.getValue(),mDeadLine , "koo");
        mDataManager.saveCoupon(coupon);
    }

    public void createCouponValueCheck(){
        Pattern pattern = Pattern.compile("(^[0-9]*$)");

        if(TextUtils.isEmpty(mBarCode.getValue()) || !pattern.matcher(mBarCode.getValue()).find()){
            this.mErrorId = "barCode";
            this.mErrorType = (TextUtils.isEmpty(mBarCode.getValue()) ? "vt" : (!pattern.matcher(mBarCode.getValue()).find()? "vtint" : null));
            this.getErrorBl = false;
        }else if(TextUtils.isEmpty(mCouponName.getValue())){
            this.mErrorId = "couponName";
            this.mErrorType = "vt";
            this.getErrorBl = false;
        }else if(TextUtils.isEmpty(mCompanyName.getValue())){
            this.mErrorId = "companyName";
            this.mErrorType = "vt";
            this.getErrorBl = false;
        }else if(TextUtils.isEmpty(mSalePrice.getValue()) || !pattern.matcher(mSalePrice.getValue()).find()){
            this.mErrorId = "salePrice";
            this.mErrorType = "vt";
            this.getErrorBl = false;
        }else if(mDeadLine == null ){
            this.mErrorId = "dateBtn";
            this.mErrorType = "bt";
            this.getErrorBl = false;
        }else if(mCouponCategory == null ){
            this.mErrorId = "couponCaterogy";
            this.mErrorType = "bt";
            this.getErrorBl = false;
        }else{
            CategoryNameMapperUtils util = new CategoryNameMapperUtils();

            this.mCouponCategory = util.toEngName(this.mCouponCategory);
            this.getErrorBl = true;
        }
        mAddCouponErrorEvent.setValue(new Event<>(new Object()));
    }
}
