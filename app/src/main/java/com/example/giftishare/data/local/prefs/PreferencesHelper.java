package com.example.giftishare.data.local.prefs;

import android.support.annotation.NonNull;

/**
 * Created by KS-KIM on 19/02/06.
 */

public interface PreferencesHelper {

    String getUserName();

    void setUserName(@NonNull String userName);

    String getWalletPassword();

    void setWalletPassword(@NonNull String password);

    String getWalletPath();

    void setWalletPath(@NonNull String walletPath);
}
