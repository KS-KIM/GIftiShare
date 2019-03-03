package com.example.giftishare.view.addcoupon;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.utils.CategoryNameMapperUtils;
import com.example.giftishare.utils.NotificationUtils;

import java.util.Date;
import java.util.GregorianCalendar;

public class AddCouponViewModel extends AndroidViewModel {

    public final static String TAG = AddCouponViewModel.class.getSimpleName();

    public final MutableLiveData<String> mCouponName = new MutableLiveData<>();

    public final MutableLiveData<String> mCompanyName = new MutableLiveData<>();

    public final MutableLiveData<String> mPrice = new MutableLiveData<>();

    public final MutableLiveData<String> mBarcode = new MutableLiveData<>();

    public final MutableLiveData<Date> mDeadline = new MutableLiveData<>();

    public final MutableLiveData<String> mCouponCategory = new MutableLiveData<>();

    private final DataManager mDataManager;

    public AddCouponViewModel(Application context, DataManager dataManager) {
        super(context);
        mDataManager = dataManager;
    }

    public boolean isEmptyField(String field) {
        return field == null || field.replace(" ", "").equals("");
    }

    public void setDeadline(int year, int month, int date) {
        Date deadline = new GregorianCalendar(year, month, date).getTime();
        mDeadline.setValue(deadline);
    }

    public void createCoupon() {
        String walletAddress = mDataManager.getCredentials().getAddress();
        Coupon coupon = new Coupon(
                mCouponName.getValue(),
                CategoryNameMapperUtils.toEngName(mCouponCategory.getValue()),
                mCompanyName.getValue(),
                mPrice.getValue(),
                mBarcode.getValue(),
                mDeadline.getValue().getTime(),
                walletAddress);
        mDataManager.addCoupon(coupon).thenAccept(transactionReceipt -> {
            Log.i(TAG, "Transaction accepted. check at https://blockscout.com/eth/ropsten/tx/" +
                    transactionReceipt.getTransactionHash());
            mDataManager.saveCoupon(coupon);
            mDataManager.saveSaleCoupon(coupon);
            NotificationUtils.sendNotification(getApplication().getApplicationContext(),
                    1,
                    NotificationUtils.Channel.NOTICE,
                    "쿠폰 등록 성공",
                    "결과를 확인하시려면 눌러주세요",
                    "https://blockscout.com/eth/ropsten/tx/" + transactionReceipt.getTransactionHash());
        }).exceptionally(transactionReceipt -> {
            Log.d(TAG, "Exeptional event occur in ethereum transaction with message \""
                    + transactionReceipt.getMessage() + "\"");
            NotificationUtils.sendNotification(getApplication().getApplicationContext(),
                    1,
                    NotificationUtils.Channel.NOTICE,
                    "쿠폰 등록 실패",
                    "쿠폰 등록에 실패했습니다.",
                    "네트워크 상태와 이더리움 지갑 잔액을 확인하세요.",
                    null);
            return null;
        });
    }
}