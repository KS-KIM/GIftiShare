package com.example.giftishare.data;

import com.example.giftishare.data.local.db.DbHelper;
import com.example.giftishare.data.local.file.KeystoreGenerationHelper;
import com.example.giftishare.data.local.prefs.PreferencesHelper;

/**
 * Created by KS-KIM on 19/02/08.
 */

public interface DataManager extends DbHelper, PreferencesHelper, KeystoreGenerationHelper {

}
