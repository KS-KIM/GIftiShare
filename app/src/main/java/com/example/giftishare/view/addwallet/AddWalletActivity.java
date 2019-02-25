package com.example.giftishare.view.addwallet;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

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
        setupBouncyCastle();

        PermissionUtils.checkReadStoragePermission(this);
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
            if (mAddWalletViewModel.isEmptyField(mBinding.etUserName.getText().toString())) {
                mBinding.etUserName.setError("사용하실 이름을 입력하세요");
                mBinding.etUserName.requestFocus();
            } else if (mAddWalletViewModel.isEmptyField(mBinding.etWalletPassword.getText().toString())) {
                mBinding.etWalletPassword.setError("사용하실 비밀번호를 입력하세요");
                mBinding.etWalletPassword.requestFocus();
            } else if (!PermissionUtils.canReadStorage(this)) {
                showPermissionDeniedMessageAndStartSetting();
            } else {
                mAddWalletViewModel.createWallet();
            }
        });

        mAddWalletViewModel.getNewWalletEvent().observe(this,
                (@Nullable Event<Object> event) -> openMainActivity());
    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void showPermissionDeniedMessageAndStartSetting() {
        Toast.makeText(getApplicationContext(), "권한을 수락하셔야 합니다.\n\"애플리케이션 설정 - 권한\"에서 저장공간 권한을 수락해주세요.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + getPackageName()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
