package com.example.giftishare.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;

import com.example.giftishare.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class NotificationUtils {

    public static final String TAG = NotificationUtils.class.getSimpleName();

    private static NotificationManager getManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void createChannel(Context context) {
        NotificationChannel channelNotice = new NotificationChannel(
                Channel.NOTICE,
                context.getString(R.string.transaction_channel_name),
                NotificationManager.IMPORTANCE_HIGH
        );
        channelNotice.setDescription("트랜잭션 결과를 알리기 위한 채널입니다.");
        channelNotice.setLightColor(Color.BLUE);
        channelNotice.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager(context).createNotificationChannel(channelNotice);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void deleteChannel(Context context, String channel) {
        getManager(context).deleteNotificationChannel(channel);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void sendNotification(@NonNull Context context, @NonNull int id,
                                        @Channel String channel, @NonNull String title,
                                        @NonNull String body, @NonNull String details,
                                        @Nullable String address) {
        Notification.Builder builder = new Notification.Builder(context, channel)
                .setContentTitle(title)
                .setStyle(new Notification.BigTextStyle().bigText(details).setSummaryText(body))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true);
        if (address != null) {
            Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            builder.setContentIntent(contentIntent);
        }
        Notification notification = builder.build();
        getManager(context).notify(id, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void sendNotification(@NonNull Context context, @NonNull int id,
                                        @Channel String channel, @NonNull String title,
                                        @NonNull String body, @Nullable String address) {
        Notification.Builder builder = new Notification.Builder(context, channel)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true);
        if (address != null) {
            Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            builder.setContentIntent(contentIntent);
        }
        Notification notification = builder.build();
        getManager(context).notify(id, notification);
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ Channel.NOTICE })
    public @interface Channel {
        String NOTICE = "notice";
    }
}
