package com.example.giftishare.view.onSaleCoupons;

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
import com.example.giftishare.data.model.CouponsCategoryType;
import com.example.giftishare.databinding.ActivityOnSaleCouponsBinding;
import com.example.giftishare.utils.ActivityUtils;

import static com.example.giftishare.view.main.MainActivity.CATEGORY_COUPONS;

public class OnSaleCouponsActivity extends AppCompatActivity {

    private ActivityOnSaleCouponsBinding mBinding;

    private OnSaleCouponsViewModel mOnSaleCouponsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_sale_coupons);

        mBinding = DataBindingUtil.setContentView(OnSaleCouponsActivity.this, R.layout.activity_on_sale_coupons);
        mBinding.setLifecycleOwner(this);

        setupToolBar();
        setupViewFragment();

        mOnSaleCouponsViewModel = obtainViewModel(this);
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBinding.btnBack.setOnClickListener((View v) -> onBackPressed());

        setupTitle();
    }
    private void setupTitle() {
        Intent intent = getIntent();
        CouponsCategoryType category = (CouponsCategoryType) intent.getSerializableExtra(CATEGORY_COUPONS);
        mBinding.title.setText(category.toKor());
    }

    private void setupViewFragment() {
        OnSaleCouponsFragment onSaleCouponsFragment =
                (OnSaleCouponsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (onSaleCouponsFragment == null) {
            onSaleCouponsFragment = OnSaleCouponsFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), onSaleCouponsFragment, R.id.content_frame);
        }
    }

    public static OnSaleCouponsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        OnSaleCouponsViewModel viewModel = ViewModelProviders.of(activity, factory).get(OnSaleCouponsViewModel.class);
        return viewModel;
    }
}
