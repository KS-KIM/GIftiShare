package com.example.giftishare.util;

import android.os.Environment;
import android.support.annotation.NonNull;

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

public class FileUtil {

    public static final String WALLET_KEYSTORE_FILE_DIR = "/giftishare";

    public String createWallet(@NonNull final String password) {
        File walletDirectory = getDirectory(WALLET_KEYSTORE_FILE_DIR);
        String walletFileName = null;
        try {
            walletFileName = WalletUtils.generateLightNewWalletFile(password, walletDirectory);
        } catch (NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | IOException
                | CipherException e) {
            e.printStackTrace();
        }
        return walletFileName;
    }

    private File getDirectory(@NonNull String specificDirectory) {
        File walletDirectory = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                .concat(specificDirectory)
        );
        if (!walletDirectory.exists()) {
            walletDirectory.mkdir();
        }
        return walletDirectory;
    }
}
