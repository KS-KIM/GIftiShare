package com.example.giftishare.view.buycoupon;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.giftishare.Event;
import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.utils.CategoryNameMapperUtils;
import com.example.giftishare.utils.NotificationUtils;

public class BuyCouponViewModel extends AndroidViewModel {

    private final MutableLiveData<Coupon> mCoupon = new MutableLiveData<>();

    private final MutableLiveData<Event<Object>> mBuyCouponEvent = new MutableLiveData<>();

    private final DataManager mDataManager;

    public BuyCouponViewModel(Application context, DataManager dataManager) {
        super(context);
        mDataManager = dataManager;
    }

    public void start(Coupon coupon) {
        mCoupon.setValue(coupon);
    }

    public LiveData<Coupon> getCoupon() {
        return mCoupon;
    }

    public LiveData<Event<Object>> getBuyCouponEvent() {
        return mBuyCouponEvent;
    }

    public void buyCoupon() {
        String id = mCoupon.getValue().mId;
        String price = mCoupon.getValue().mPrice;
        mDataManager.buyCoupon(id, price).thenAccept(transactionReceipt -> {
            String walletAddress = mDataManager.getCredentials().getAddress();
            Coupon coupon = mCoupon.getValue();
            coupon.setOnSale(false);
            coupon.setOwner(walletAddress);
            mDataManager.saveCoupon(coupon);
            NotificationUtils.sendNotification(getApplication().getApplicationContext(),
                    1,
                    NotificationUtils.Channel.NOTICE,
                    "쿠폰 구매 성공",
                    "결과를 확인하시려면 눌러주세요",
                    "https://blockscout.com/eth/ropsten/tx/" + transactionReceipt.getTransactionHash());
        }).exceptionally(transactionReceipt -> {
            NotificationUtils.sendNotification(getApplication().getApplicationContext(),
                    1,
                    NotificationUtils.Channel.NOTICE,
                    "쿠폰 구매 실패",
                    "쿠폰 구매에 실패했습니다.",
                    "네트워크 상태와 이더리움 지갑 잔액을 확인하세요.",
                    null);
            return null;
        });
        mBuyCouponEvent.postValue(new Event<>(new Object()));
    }
}
