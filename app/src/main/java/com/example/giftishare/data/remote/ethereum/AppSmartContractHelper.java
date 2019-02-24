package com.example.giftishare.data.remote.ethereum;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.giftishare.data.model.Coupon;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;

import java8.util.concurrent.CompletableFuture;

/**
 * Created by KS-KIM on 19/02/21.
 */

public class AppSmartContractHelper implements SmartContractHelper {

    private static final String TAG = AppSmartContractHelper.class.getSimpleName();

    private static volatile AppSmartContractHelper INSTANCE;

    public static final String ROPSTEN_NETWORK_ADDRESS = "https://ropsten.infura.io/v3/cab60b4fc0594563881813d8f5f5349b";

    public static final String CONTRACT_ADDRESS = "0x7755a9B5678358cf2736296Aa840734dF48b7791";

    private Web3j mWeb3j;

    private Credentials mCredentials;

    private GiftiShare mSmartContract;

    private AppSmartContractHelper(@NonNull Web3j web3j,
                                   @Nullable Credentials credentials,
                                   @Nullable GiftiShare smartContract) {
        mWeb3j = web3j;
        mCredentials = credentials;
        mSmartContract = smartContract;
    }

    public static AppSmartContractHelper getInstance(@NonNull Web3j web3j,
                                                     @Nullable Credentials credentials,
                                                     @Nullable GiftiShare smartContract) {
        if (INSTANCE == null) {
            synchronized (AppSmartContractHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppSmartContractHelper(web3j, credentials, smartContract);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public CompletableFuture<TransactionReceipt> buyCoupon(@NonNull String uuid, @NonNull String price) {
        if (mSmartContract == null) {
            throw new NullPointerException();
        }
        BigInteger buyPrice = new BigInteger(price);
        Log.i(TAG, "buyCoupon function called with Address: " + mCredentials.getAddress());
        return mSmartContract.buyCoupon(uuid, buyPrice).sendAsync();
    }

    @Override
    public CompletableFuture<TransactionReceipt> resumeSaleCoupon(@NonNull String uuid) {
        if (mSmartContract == null) {
            throw new NullPointerException();
        }
        Log.i(TAG, "resumeSaleCoupon function called with Address: " + mCredentials.getAddress());
        return mSmartContract.resumeCouponSale(uuid).sendAsync();
    }

    @Override
    public CompletableFuture<TransactionReceipt> useCoupon(@NonNull String uuid) {
        if (mSmartContract == null) {
            throw new NullPointerException();
        }
        Log.i(TAG, "useCoupon function called with Address: " + mCredentials.getAddress());
        return mSmartContract.useCoupon(uuid).sendAsync();
    }

    @Override
    public CompletableFuture<TransactionReceipt> addCoupon(@NonNull Coupon coupon) {
        if (mSmartContract == null) {
            throw new NullPointerException();
        }
        String uuid = coupon.getId();
        String name = coupon.getName();
        String category = coupon.getCategory();
        String company = coupon.getCompany();
        BigInteger price = new BigInteger(coupon.getPrice());
        String barcode = coupon.getBarcode();
        String deadline = coupon.getDeadline().toString();
        Log.i(TAG, "addCoupon function called with Address: " + mCredentials.getAddress());
        return mSmartContract.addCoupon(uuid, name, category, company, price, barcode, deadline).sendAsync();
    }

    @Override
    public CompletableFuture<TransactionReceipt> stopSaleCoupon(@NonNull String uuid) {
        Log.i(TAG, "stopSaleCoupon function called with Address: " + mCredentials.getAddress());
        return mSmartContract.stopCouponSale(uuid).sendAsync();
    }

    @Override
    public void loadCredentialsAndSmartContract(String password, String source) {
        try {
            mCredentials = WalletUtils.loadCredentials(password, source);
            mSmartContract = GiftiShare.load(CONTRACT_ADDRESS, mWeb3j, mCredentials, new DefaultGasProvider());
        } catch (IOException
                | CipherException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Credentials getCredentials() {
        return mCredentials;
    }

    @Override
    public CompletableFuture<EthGetBalance> getBalance() {
        if (mCredentials == null) {
            throw new NullPointerException();
        }
        return mWeb3j.ethGetBalance(mCredentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync();
    }
}
