package com.example.giftishare.view.addwallet;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.giftishare.R;
import com.example.giftishare.databinding.ActivityAddWalletBinding;

public class AddWalletActivity extends AppCompatActivity {

    private AddWalletViewModel mAddWalletViewModel;

    private ActivityAddWalletBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAddWalletViewModel = ViewModelProviders.of(this).get(AddWalletViewModel.class);

        mBinding = DataBindingUtil.setContentView(AddWalletActivity.this, R.layout.activity_add_wallet);
        mBinding.setLifecycleOwner(this);
        mBinding.setAddWalletViewModel(mAddWalletViewModel);


    }

}
