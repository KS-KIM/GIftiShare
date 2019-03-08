package com.example.giftishare.data.remote.ethereum;

import com.example.giftishare.data.model.Coupon;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;

import java8.util.concurrent.CompletableFuture;

/**
 * Created by KS-KIM on 19/02/21.
 */

public interface SmartContractHelper {

    /* @TODO change returns CompletableFuture -> flowable */
    CompletableFuture<TransactionReceipt> buyCoupon(String uuid, String price);

    CompletableFuture<TransactionReceipt> resumeSaleCoupon(String uuid);

    CompletableFuture<TransactionReceipt> useCoupon(String uuid);

    CompletableFuture<TransactionReceipt> addCoupon(Coupon coupon);

    CompletableFuture<TransactionReceipt> stopSaleCoupon(String uuid);

    void loadCredentialsAndSmartContract(String password, String source);

    Credentials getCredentials();

    CompletableFuture<EthGetBalance> getBalance();

    CompletableFuture<TransactionReceipt> sendEther(String toAddress, BigDecimal balance);
}
