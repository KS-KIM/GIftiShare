package com.example.giftishare.data.local.file;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.giftishare.utils.FileUtils;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by KS-KIM on 19/02/06.
 */

public class AppKeystoreGenerationHelper implements KeystoreGenerationHelper {

    private static final String TAG = AppKeystoreGenerationHelper.class.getSimpleName();

    private static final String WALLET_KEYSTORE_FILE_DIR = "/GiftiShare";

    @Override
    public String createWallet(@NonNull final String password) {
        File walletDirectory = FileUtils.getDirectory(WALLET_KEYSTORE_FILE_DIR);
        String walletFileName = null;
        try {
            Log.d(TAG, walletDirectory.toString());
            walletFileName = walletDirectory.getPath().concat("/");
            walletFileName += WalletUtils.generateLightNewWalletFile(password, walletDirectory);
        } catch (NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | IOException
                | CipherException e) {
            e.printStackTrace();
        }
        Log.d(TAG, walletFileName);
        return walletFileName;
    }
}
