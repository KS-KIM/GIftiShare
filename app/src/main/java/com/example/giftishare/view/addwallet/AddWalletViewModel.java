package com.example.giftishare.view.addwallet;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.giftishare.util.FileUtil;

public class AddWalletViewModel extends ViewModel {

    public MutableLiveData<String> mUserName = new MutableLiveData<>();
    public MutableLiveData<String> mWalletPassword = new MutableLiveData<>();

    public void createWallet() {

    }

}
