package com.example.giftishare.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.giftishare.ViewModelFactory;
import com.example.giftishare.data.AppDataManager;
import com.example.giftishare.di.Injection;
import com.example.giftishare.utils.FileUtils;
import com.example.giftishare.helper.NotificationHelper;
import com.example.giftishare.view.addwallet.AddWalletActivity;
import com.example.giftishare.view.main.MainActivity;

/**
 * Created by KS-KIM on 19/02/08.
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        String walletPath = Injection.providePreferenceHelper(getApplicationContext()).getWalletPath();
        if (walletPath == null || !FileUtils.isExistFile(walletPath)) {
            if (walletPath != null) {
                Log.d(TAG, walletPath);
            }
            intent = new Intent(this, AddWalletActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        NotificationHelper.createChannel(getApplicationContext());
        startActivity(intent);
        finish();
    }
}
