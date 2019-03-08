package com.example.giftishare.data;

import com.example.giftishare.data.local.db.DBHelper;
import com.example.giftishare.data.local.file.KeystoreGenerationHelper;
import com.example.giftishare.data.local.prefs.PreferencesHelper;
import com.example.giftishare.data.model.Coupon;
import com.example.giftishare.data.remote.ethereum.SmartContractHelper;
import com.example.giftishare.data.remote.firebase.FirebaseDBHelper;

/**
 * Created by KS-KIM on 19/02/08.
 */

public interface DataManager extends DBHelper, FirebaseDBHelper, PreferencesHelper, KeystoreGenerationHelper, SmartContractHelper {

    void deleteSaleCoupon(Coupon coupon);
}
