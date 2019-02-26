package com.example.giftishare.view.sellcoupons;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.databinding.ActivitySellCouponsBinding;
import com.example.giftishare.utils.ActivityUtils;

public class SellCouponsActivity extends AppCompatActivity {

    private ActivitySellCouponsBinding mBinding;

    private SellCouponsViewModel mSellCouponsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_coupons);

        mBinding = DataBindingUtil.setContentView(SellCouponsActivity.this, R.layout.activity_sell_coupons);
        mBinding.setLifecycleOwner(this);

        setupToolBar();
        setupViewFragment();

        mSellCouponsViewModel = obtainViewModel(this);
    }
    private void setupToolBar() {
        mBinding.title.setText("판매목록");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBinding.btnBack.setOnClickListener((View v) -> onBackPressed());
    }


    private void setupViewFragment() {
        SellCouponsFragment SellCouponsFragment =
                (SellCouponsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (SellCouponsFragment == null) {
            SellCouponsFragment = SellCouponsFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), SellCouponsFragment, R.id.content_frame);
        }
    }

    public static SellCouponsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        SellCouponsViewModel viewModel = ViewModelProviders.of(activity, factory).get(SellCouponsViewModel.class);
        return viewModel;
    }
}
