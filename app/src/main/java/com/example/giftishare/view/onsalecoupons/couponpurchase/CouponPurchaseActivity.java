package com.example.giftishare.view.onsalecoupons.couponpurchase;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.giftishare.Event;
import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.databinding.ActivityCouponPurchaseBinding;
import com.example.giftishare.helper.CategoryNameMapper;

import static com.example.giftishare.view.onsalecoupons.OnSaleCouponsAdapter.INTENT_BUY_COUPON;

public class CouponPurchaseActivity extends AppCompatActivity {

    private ActivityCouponPurchaseBinding mBinding;

    private CouponPurchaseViewModel mCouponPurchaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCouponPurchaseViewModel = obtainViewModel(this);

        mBinding = DataBindingUtil.setContentView(CouponPurchaseActivity.this, R.layout.activity_coupon_purchase);
        mBinding.setLifecycleOwner(this);
        mBinding.setCouponPurchaseViewModel(mCouponPurchaseViewModel);

        setupToolBar();

        Intent intent = getIntent();
        Coupon coupon = (Coupon) intent.getSerializableExtra(INTENT_BUY_COUPON);
        mBinding.cardInfo.ivCouponImage.setImageResource(CategoryNameMapper.toImageDrawable(coupon.getCategory()));
        mCouponPurchaseViewModel.start(coupon);

        mBinding.btnDecline.setOnClickListener((View view) -> onBackPressed());

        mCouponPurchaseViewModel.getBuyCouponEvent().observe(this,
                (Event<String> event) -> {
                    Toast.makeText(getApplicationContext(), event.getContentIfNotHandled(), Toast.LENGTH_LONG).show();
                    finish();
                });
    }

    private CouponPurchaseViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        CouponPurchaseViewModel couponPurchaseViewModel = ViewModelProviders.of(this, factory).get(CouponPurchaseViewModel.class);
        return couponPurchaseViewModel;
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBinding.btnBack.setOnClickListener((View v) -> onBackPressed());
    }
}
