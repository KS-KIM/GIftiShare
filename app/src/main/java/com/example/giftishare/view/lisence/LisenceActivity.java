package com.example.giftishare.view.lisence;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.example.giftishare.R;
import com.example.giftishare.databinding.ActivityLisenceBinding;

public class LisenceActivity extends AppCompatActivity {
    private ActivityLisenceBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lisence);

        mBinding = DataBindingUtil.setContentView(LisenceActivity.this, R.layout.activity_lisence);
        mBinding.setLifecycleOwner(this);

        setupToolBar();

        mBinding.webview.loadUrl("file:///android_asset/www/index.html");
    }
    private void setupToolBar() {
        mBinding.title.setText("Lisence");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBinding.btnBack.setOnClickListener((View v) -> onBackPressed());
    }
}
