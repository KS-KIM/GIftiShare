package com.example.giftishare.view.license;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.giftishare.R;
import com.example.giftishare.databinding.ActivityLicenseBinding;

public class LicenseActivity extends AppCompatActivity {
    private ActivityLicenseBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        mBinding = DataBindingUtil.setContentView(LicenseActivity.this, R.layout.activity_license);
        mBinding.setLifecycleOwner(this);

        setupToolBar();
        mBinding.webview.setInitialScale(1);
        mBinding.webview.getSettings().setUseWideViewPort(true);
        mBinding.webview.getSettings().setLoadWithOverviewMode(true);
        mBinding.webview.loadUrl("file:///android_asset/www/index.html");
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBinding.btnBack.setOnClickListener((View v) -> onBackPressed());
    }
}
