package com.example.giftishare.data.remote.ethereum;

import android.support.annotation.NonNull;

import com.example.giftishare.data.model.Coupon;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java8.util.concurrent.CompletableFuture;

/**
 * Created by KS-KIM on 19/02/21.
 */

public class AppSmartContractHelper implements SmartContractHelper {

    private static volatile AppSmartContractHelper INSTANCE;

    public static final String ROPSTEN_NETWORK_ADDRESS = "https://https://ropsten.infura.io/v3/cab60b4fc0594563881813d8f5f5349b";

    public static final String CONTRACT_ADDRESS = "0x7755a9B5678358cf2736296Aa840734dF48b7791";

    private Web3j mWeb3j;

    private Credentials mCredentials;

    private GiftiShare mSmartContract;

    private AppSmartContractHelper(@NonNull Web3j web3j,
                                   @NonNull Credentials credentials,
                                   @NonNull GiftiShare smartContract) {
        mWeb3j = web3j;
        mCredentials = credentials;
        mSmartContract = smartContract;
    }

    public static AppSmartContractHelper getInstance(@NonNull Web3j web3j,
                                                     @NonNull Credentials credentials,
                                                     @NonNull GiftiShare smartContract) {
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
        BigInteger buyPrice = new BigInteger(price);
        return mSmartContract.buyCoupon(uuid, buyPrice).sendAsync();
    }

    @Override
    public CompletableFuture<TransactionReceipt> resumeSaleCoupon(@NonNull String uuid) {
        return mSmartContract.resumeCouponSale(uuid).sendAsync();
    }

    @Override
    public CompletableFuture<TransactionReceipt> useCoupon(@NonNull String uuid) {
        return mSmartContract.useCoupon(uuid).sendAsync();
    }

    @Override
    public CompletableFuture<TransactionReceipt> addCoupon(@NonNull Coupon coupon) {
        String uuid = coupon.getId();
        String name = coupon.getName();
        String category = coupon.getCategory();
        String company = coupon.getCompany();
        BigInteger price = new BigInteger(coupon.getPrice());
        String barcode = coupon.getBarcode();
        String deadline = coupon.getDeadline().toString();
        return mSmartContract.addCoupon(uuid, name, category, company, price, barcode, deadline).sendAsync();
    }

    @Override
    public CompletableFuture<TransactionReceipt> stopSaleCoupon(@NonNull String uuid) {
        return mSmartContract.stopCouponSale(uuid).sendAsync();
    }
}
