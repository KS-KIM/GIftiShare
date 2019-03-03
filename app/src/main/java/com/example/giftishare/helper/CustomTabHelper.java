package com.example.giftishare.helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static android.support.customtabs.CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION;

/**
 * Simplified version based on the CustomTabsHelper from:
 * https://github.com/GoogleChrome/custom-tabs-client
 * Copyright Google Inc. Licence: http://www.apache.org/licenses/LICENSE-2.0
 */
public final class CustomTabHelper {

    private static final String CUSTOM_TABS_EXTRA_SESSION =
            "android.support.customtabs.extra.SESSION";
    private static final String CUSTOM_TABS_TOOLBAR_COLOR =
            "android.support.customtabs.extra.TOOLBAR_COLOR";

    public static String getPackageNameToUse(Context context, String urlToLoad) {

        PackageManager pm = context.getPackageManager();

        // Get default VIEW intent handler
        Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToLoad));
        ResolveInfo defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0);

        String defaultViewHandlerPackageName = null;

        if (defaultViewHandlerInfo != null) {
            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName;
        }

        // Get all apps that can handle VIEW intents
        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, 0);
        List<String> packagesSupportingCustomTabs = new ArrayList<>();

        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(info.activityInfo.packageName);
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName);
            }
        }

        // Now our list contains all apps that can handle both VIEW intents & service calls
        if (packagesSupportingCustomTabs.size() > 0) {
            if (!TextUtils.isEmpty(defaultViewHandlerPackageName) &&
                    packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {
                // Prefer the defined default
                return defaultViewHandlerPackageName;
            } else {
                // Otherwise just select the first one, to keep this demo simple for now.
                // NOTE: If there are multiple options, it would probably be better to ask the user
                // to decide which app to use. See Andre Bandarra's Best Practices guide:
                // https://medium.com/google-developers/best-practices-for-custom-tabs-5700e55143ee
                return packagesSupportingCustomTabs.get(0);
            }
        }
        return null;
    }

    public static Intent createCustomTabIntent(Context context, String urlToLoad, int toolbarColor) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToLoad));
        String packageName = CustomTabHelper.getPackageNameToUse(context, urlToLoad);
        // If custom tab support, otherwise should fallback to simply opening in the browser
        if (packageName != null) {
            intent.setPackage(packageName);
            Bundle extras = new Bundle();
            extras.putInt(CUSTOM_TABS_TOOLBAR_COLOR, toolbarColor);
            extras.putBinder(CUSTOM_TABS_EXTRA_SESSION, null);
            extras.putParcelable(Intent.EXTRA_REFERRER, Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + context.getPackageName()));
            intent.putExtras(extras);
        }
        return intent;
    }
}
