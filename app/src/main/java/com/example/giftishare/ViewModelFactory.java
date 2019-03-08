/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.giftishare;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.giftishare.data.DataManager;
import com.example.giftishare.di.Injection;
import com.example.giftishare.view.addcoupon.AddCouponViewModel;
import com.example.giftishare.view.addwallet.AddWalletViewModel;
import com.example.giftishare.view.buycoupon.BuyCouponViewModel;
import com.example.giftishare.view.buysellcoupons.BuySellCouponsViewModel;
import com.example.giftishare.view.main.MainViewModel;
import com.example.giftishare.view.onsalecoupons.OnSaleCouponsViewModel;

/**
 * A creator is used to inject the product ID into the ViewModel
 * <p>
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final DataManager mDataManager;

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    DataManager dataManager = Injection.provideDataManager(application.getApplicationContext());
                    INSTANCE = new ViewModelFactory(application, dataManager);
                }
            }
        }
        return INSTANCE;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application, DataManager dataManager) {
        mApplication = application;
        mDataManager = dataManager;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddWalletViewModel.class)) {
            return (T) new AddWalletViewModel(mApplication, mDataManager);
        } else if (modelClass.isAssignableFrom(OnSaleCouponsViewModel.class)) {
            return (T) new OnSaleCouponsViewModel(mApplication, mDataManager);
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mApplication, mDataManager);
        } else if (modelClass.isAssignableFrom(AddCouponViewModel.class)) {
            return (T) new AddCouponViewModel(mApplication, mDataManager);
        } else if (modelClass.isAssignableFrom(BuySellCouponsViewModel.class)) {
            return (T) new BuySellCouponsViewModel(mApplication, mDataManager);
        } else if (modelClass.isAssignableFrom(BuyCouponViewModel.class)) {
            return (T) new BuyCouponViewModel(mApplication, mDataManager);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
