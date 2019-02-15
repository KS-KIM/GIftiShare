package com.example.giftishare.view.onSaleCoupons;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.utils.ActivityUtils;

public class OnSaleCouponsActivity extends AppCompatActivity {

    private OnSaleCouponsViewModel mOnSaleCouponsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_sale_coupons);
        setupViewFragment();
        mOnSaleCouponsViewModel = obtainViewModel(this);
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
