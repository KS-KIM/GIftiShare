package com.example.giftishare.view.buycoupons;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.databinding.ActivityBuyCouponsBinding;
import com.example.giftishare.utils.ActivityUtils;

public class BuyCouponsActivity extends AppCompatActivity {

    private ActivityBuyCouponsBinding mBinding;

    private BuyCouponsViewModel mBuyCouponsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_coupons);

        mBinding = DataBindingUtil.setContentView(BuyCouponsActivity.this, R.layout.activity_buy_coupons);
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
        BuyCouponsFragment BuyCouponsFragment =
                (BuyCouponsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (BuyCouponsFragment == null) {
            BuyCouponsFragment = BuyCouponsFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), BuyCouponsFragment, R.id.content_frame);
        }
    }

    public static BuyCouponsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        BuyCouponsViewModel viewModel = ViewModelProviders.of(activity, factory).get(BuyCouponsViewModel.class);
        return viewModel;
    }
}
