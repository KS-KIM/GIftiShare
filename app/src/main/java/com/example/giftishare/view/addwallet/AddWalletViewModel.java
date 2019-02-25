package com.example.giftishare.view.addwallet;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;

import com.example.giftishare.Event;
import com.example.giftishare.data.DataManager;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

/**
 * Created by KS-KIM on 19/02/08.
 */

public class AddWalletViewModel extends AndroidViewModel {

    public final MutableLiveData<String> mUserName = new MutableLiveData<>();

    public final MutableLiveData<String> mWalletPassword = new MutableLiveData<>();

    private final DataManager mDataManager;

    private final MutableLiveData<Event<Object>> mNewWalletEvent = new MutableLiveData<>();

    public AddWalletViewModel(Application context, DataManager dataManager) {
        super(context);
        mDataManager = dataManager;
    }

    public boolean isEmptyField(String field) {
        return field == null || field.replace(" ", "").equals("");
    }

    public MutableLiveData<String> getUserName() {
        return mUserName;
    }

    public MutableLiveData<String> getWalletPassword() {
        return mWalletPassword;
    }

    public MutableLiveData<Event<Object>> getNewWalletEvent() {
        return mNewWalletEvent;
    }

    public void createWallet() {
        String password = mWalletPassword.getValue();
        String source = checkNotNull(mDataManager.createWallet(password));
        mDataManager.setUserName(mUserName.getValue());
        mDataManager.setWalletPassword(mWalletPassword.getValue());
        mDataManager.setWalletPath(source);
        mDataManager.loadCredentialsAndSmartContract(password, source);
        mNewWalletEvent.setValue(new Event<>(new Object()));
    }
}
