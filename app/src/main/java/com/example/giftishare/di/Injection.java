package com.example.giftishare.di;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.giftishare.data.AppDataManager;
import com.example.giftishare.data.DataManager;
import com.example.giftishare.data.local.db.AppDBHelper;
import com.example.giftishare.data.local.db.AppDatabase;
import com.example.giftishare.data.local.db.DBHelper;
import com.example.giftishare.data.local.file.AppKeystoreGenerationHelper;
import com.example.giftishare.data.local.file.KeystoreGenerationHelper;
import com.example.giftishare.data.local.prefs.AppPreferencesHelper;
import com.example.giftishare.data.local.prefs.PreferencesHelper;
import com.example.giftishare.data.remote.ethereum.AppSmartContractHelper;
import com.example.giftishare.data.remote.ethereum.GiftiShare;
import com.example.giftishare.data.remote.ethereum.SmartContractHelper;
import com.example.giftishare.data.remote.firebase.AppFirebaseDBHelper;
import com.example.giftishare.data.remote.firebase.FirebaseDBHelper;
import com.example.giftishare.utils.AppExecutors;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;

import static com.example.giftishare.data.remote.ethereum.AppSmartContractHelper.CONTRACT_ADDRESS;
import static com.example.giftishare.data.remote.ethereum.AppSmartContractHelper.ROPSTEN_NETWORK_ADDRESS;

/**
 * Created by KS-KIM on 19/03/08.
 */

public final class Injection {

    /* @TODO Implementation Dagger 2 */

    public static final String TAG = Injection.class.getSimpleName();

    public static AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    public static RoomDatabase provideAppDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }

    public static DBHelper provideDBHelper(Context context) {
        AppDatabase database = (AppDatabase) provideAppDatabase(context);
        return AppDBHelper.getInstance(provideAppExecutors(), database.couponDao());
    }

    public static FirebaseDBHelper provideFirebaseDBHelper() {
        return AppFirebaseDBHelper.getInstance();
    }

    public static PreferencesHelper providePreferenceHelper(Context context) {
        return AppPreferencesHelper.getInstance(context);
    }

    public static KeystoreGenerationHelper provideKeystoreGenerationHelper() {
        return new AppKeystoreGenerationHelper();
    }

    public static HttpService provideHttpService() {
        return new HttpService(ROPSTEN_NETWORK_ADDRESS);
    }

    public static Web3j provideWeb3j() {
        return Web3j.build(provideHttpService());
    }

    public static ContractGasProvider provideContractGasProvider() {
        return new DefaultGasProvider();
    }

    public static Credentials provideCredentials(Context context) {
        PreferencesHelper preferencesHelper = providePreferenceHelper(context);
        String password = preferencesHelper.getWalletPassword();
        String path = preferencesHelper.getWalletPath();
        try {
            return WalletUtils.loadCredentials(password, path);
        } catch (IOException
                | CipherException
                | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GiftiShare provideSmartContract(Context context) {
        try {
            GiftiShare giftishare = GiftiShare.load(CONTRACT_ADDRESS, provideWeb3j(), provideCredentials(context), provideContractGasProvider());
            return giftishare;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SmartContractHelper provideSmartContractHelper(Context context) {
        return AppSmartContractHelper.getInstance(provideWeb3j(), provideCredentials(context), provideSmartContract(context));
    }

    public static DataManager provideDataManager(Context context) {
        return AppDataManager.getInstance(provideDBHelper(context),
                provideFirebaseDBHelper(),
                providePreferenceHelper(context),
                provideKeystoreGenerationHelper(),
                provideSmartContractHelper(context));
    }
}
