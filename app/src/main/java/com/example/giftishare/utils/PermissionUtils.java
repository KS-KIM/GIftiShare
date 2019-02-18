package com.example.giftishare.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by KS-KIM on 19/02/08.
 */

public final class PermissionUtils {

    public static final int PERMISSION_STORAGE_TAG = 255;

    public static boolean canReadStorage(Activity activity) {
        return ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkReadStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !canReadStorage(activity)) {
            requestStoragePermission(activity);
            return false;
        }
        return true;
    }

    private static void requestStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_STORAGE_TAG);
    }
}
