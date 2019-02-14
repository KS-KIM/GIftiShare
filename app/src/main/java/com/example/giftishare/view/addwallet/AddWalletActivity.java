package com.example.giftishare.view.addwallet;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.giftishare.Event;
import com.example.giftishare.R;
import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.databinding.ActivityAddWalletBinding;
import com.example.giftishare.utils.PermissionUtils;
import com.example.giftishare.view.main.MainActivity;

/**
 * Created by KS-KIM on 19/02/08.
 */

public class AddWalletActivity extends AppCompatActivity {

    private static final String TAG = AddWalletActivity.class.getSimpleName();

    private AddWalletViewModel mAddWalletViewModel;

    private ActivityAddWalletBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAddWalletViewModel = obtainViewModel(this);

        mBinding = DataBindingUtil.setContentView(AddWalletActivity.this, R.layout.activity_add_wallet);
        mBinding.setLifecycleOwner(this);
        mBinding.setAddWalletViewModel(mAddWalletViewModel);

        setupToolbar();
        setupCreateWalletButton();
    }

    private AddWalletViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        AddWalletViewModel addWalletViewModel = ViewModelProviders.of(this, factory).get(AddWalletViewModel.class);
        return addWalletViewModel;
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupCreateWalletButton() {
        mBinding.btnCreateWallet.setOnClickListener((View v) -> {
            if (!PermissionUtils.checkReadStoragePermission(this)) {
                showPermissionDeniedMessage();
                return;
            }
            if (!mAddWalletViewModel.isUserNameVaild()) {
                mBinding.etUserName.setError("사용하실 이름을 입력하세요");
                mBinding.etUserName.requestFocus();
                return;
            }
            if (!mAddWalletViewModel.isWalletPasswordVaild()) {
                mBinding.etWalletPassword.setError("사용하실 비밀번호를 입력하세요");
                mBinding.etWalletPassword.requestFocus();
                return;
            }
            mAddWalletViewModel.createWallet();
        });

        mAddWalletViewModel.getNewWalletEvent().observe(this,
                (@Nullable Event<Object> event) -> openMainActivity());
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void showPermissionDeniedMessage() {
        Toast.makeText(getApplicationContext(), "권한을 수락하셔야 합니다.", Toast.LENGTH_SHORT).show();
    }
}
