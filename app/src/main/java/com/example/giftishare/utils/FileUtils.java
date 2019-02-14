package com.example.giftishare.utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by KS-KIM on 19/02/06.
 */

public final class FileUtils {

    private FileUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isExistFile(@Nullable String filePath) {
        File directory = new File(filePath);
        if (directory.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static File getDirectory(@NonNull String specificDirectory) {
        File directory = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                .concat(specificDirectory)
        );
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }
}
