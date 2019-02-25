package com.example.giftishare.view.buycoupon;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.databinding.ActivityBuyCouponBinding;
import com.example.giftishare.utils.CategoryNameMapperUtils;

import static com.example.giftishare.view.onSaleCoupons.OnSaleCouponsAdapter.INTENT_BUY_COUPON;

public class BuyCouponActivity extends AppCompatActivity {

    private ActivityBuyCouponBinding mBinding;

    private BuyCouponViewModel mBuyCouponViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBuyCouponViewModel = obtainViewModel(this);

        mBinding = DataBindingUtil.setContentView(BuyCouponActivity.this, R.layout.activity_buy_coupon);
        mBinding.setLifecycleOwner(this);
        mBinding.setBuyCouponViewModel(mBuyCouponViewModel);

        setupToolBar();

        Intent intent = getIntent();
        Coupon coupon = (Coupon) intent.getSerializableExtra(INTENT_BUY_COUPON);
        mBinding.tvCouponName.setText(coupon.getName());
        mBinding.tvCouponCompany.setText(coupon.getCompany());
        mBinding.tvCouponDeadline.setText(coupon.getDate());
        mBinding.tvCouponPrice.setText(coupon.getPrice());
        mBinding.ivCouponImage.setImageResource(CategoryNameMapperUtils.toImageDrawable(coupon.getCategory()));
        mBuyCouponViewModel.start(coupon);
    }

    private BuyCouponViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        BuyCouponViewModel buyCouponViewModel = ViewModelProviders.of(this, factory).get(BuyCouponViewModel.class);
        return buyCouponViewModel;
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBinding.btnBack.setOnClickListener((View v) -> onBackPressed());
    }
}
