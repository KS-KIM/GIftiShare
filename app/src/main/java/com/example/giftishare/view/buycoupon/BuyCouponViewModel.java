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

    private final MutableLiveData<Event<String>> mBuyCouponEvent = new MutableLiveData<>();

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

    public LiveData<Event<String>> getBuyCouponEvent() {
        return mBuyCouponEvent;
    }

    public void buyCoupon(Coupon coupon) {
        if (coupon.getOwner().equals(mDataManager.getCredentials().getAddress())) {
            mBuyCouponEvent.postValue(new Event<>("본인의 쿠폰은 구매할 수 없습니다."));
            return;
        }

        mDataManager.buyCoupon(coupon.getId(), coupon.getPrice()).thenAccept(transactionReceipt -> {
            String walletAddress = mDataManager.getCredentials().getAddress();
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
        mBuyCouponEvent.postValue(new Event<>("구매 요청을 완료했습니다. 알림을 통해 결과를 알려드립니다."));
    }
}
