package com.example.giftishare.view.buysellcoupons;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.databinding.ActivityBuySellCouponsBinding;
import com.example.giftishare.utils.ActivityUtils;

public class BuySellCouponsActivity extends AppCompatActivity {

    private ActivityBuySellCouponsBinding mBinding;

    private BuySellCouponsViewModel mBuyCouponsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell_coupons);

        mBinding = DataBindingUtil.setContentView(BuySellCouponsActivity.this, R.layout.activity_buy_sell_coupons);
        mBinding.setLifecycleOwner(this);

        setupToolBar();
        setupViewFragment();

        mBuyCouponsViewModel = obtainViewModel(this);
    }
    private void setupToolBar() {
        mBinding.title.setText("구매목록");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBinding.btnBack.setOnClickListener((View v) -> onBackPressed());
    }


    private void setupViewFragment() {
        BuySellCouponsFragment BuyCouponsFragment =
                (BuySellCouponsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (BuyCouponsFragment == null) {
            BuyCouponsFragment = BuyCouponsFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), BuyCouponsFragment, R.id.content_frame);
        }
    }

    public static BuySellCouponsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        BuySellCouponsViewModel viewModel = ViewModelProviders.of(activity, factory).get(BuySellCouponsViewModel.class);
        return viewModel;
    }
}
