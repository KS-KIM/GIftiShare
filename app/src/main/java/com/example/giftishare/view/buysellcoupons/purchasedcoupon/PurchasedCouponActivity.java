package com.example.giftishare.view.buysellcoupons.purchasedcoupon;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.databinding.ActivityPurchasedCouponBinding;
import com.example.giftishare.helper.CategoryNameMapper;
import com.example.giftishare.view.buysellcoupons.BuySellCouponsAdapter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PurchasedCouponActivity extends AppCompatActivity {

    private ActivityPurchasedCouponBinding mBinding;

    private PurchasedCouponViewModel mPurchasedCouponViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPurchasedCouponViewModel = obtainViewModel(this);

        mBinding = DataBindingUtil.setContentView(PurchasedCouponActivity.this, R.layout.activity_purchased_coupon);
        mBinding.setLifecycleOwner(this);
        mBinding.setPurchasedCouponViewModel(mPurchasedCouponViewModel);

        setupToolBar();
        Intent intent = getIntent();
        Coupon coupon = (Coupon) intent.getSerializableExtra(BuySellCouponsAdapter.INTENT_MY_COUPON);

        String barcode = coupon.getBarcode();
        setupBarcode(barcode);

        String couponCategory = coupon.getCategory();
        mBinding.ivCouponImage.setImageResource(CategoryNameMapper.toImageDrawable(couponCategory));

        mPurchasedCouponViewModel.start(coupon);
    }

    private PurchasedCouponViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        PurchasedCouponViewModel purchasedCouponViewModel = ViewModelProviders.of(this, factory).get(PurchasedCouponViewModel.class);
        return purchasedCouponViewModel;
    }

    private void setupBarcode(String barcode) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(barcode, BarcodeFormat.CODE_128, 1200, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            mBinding.ivCouponBarcode.setImageBitmap(bitmap);
        } catch(WriterException e) {
            e.printStackTrace();
        }
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBinding.btnBack.setOnClickListener((View v) -> onBackPressed());
    }
}
