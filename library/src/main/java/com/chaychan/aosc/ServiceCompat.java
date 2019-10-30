package com.chaychan.aosc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

/**
 * @author chaychan
 * @description: Service adaptation for Android 8.0 or above
 * @date 2019/7/29  10:36
 */
public abstract class ServiceCompat extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //适配安卓8.0
            String channelId = getChannelId() + "";
            String channelName = getChannelName();
            NotificationChannel channel = new NotificationChannel(channelId, channelName,
                    NotificationManager.IMPORTANCE_MIN);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

            startForeground(getChannelId(), getNotification());
        }
    }

    /**
     * Notification channelName
     *
     */
    protected abstract String getChannelName();

    /**
     * Notification channelId,must not be 0
     *
     */
    protected abstract int getChannelId();


    /**
     * Default content for notification , subclasses can be overwritten and returned
     */
    public String getNotificationContent(){
        return "";
    }


    /**
     * Displayed notifications, subclasses can be overwritten and returned
     */
    public Notification getNotification() {
        return createNormalNotification(getNotificationContent());
    }

    protected Notification createNormalNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getChannelId() + "");
        if (TextUtils.isEmpty(content)) {
            return builder.build();
        }

        builder.setContentTitle(getString(R.string.app_name))
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(getSmallIcon())
                .setLargeIcon(getLargeIcon())
                .build();

        return builder.build();
    }

    /**
     * Large icon for notification , subclasses can be overwritten and returned
     */
    public Bitmap getLargeIcon() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    /**
     * Small icon for notification , subclasses can be overwritten and returned
     */
    public int getSmallIcon() {
        return R.mipmap.ic_launcher;
    }


    public static void startService(Context context, Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //适配安卓8.0
            context.startForegroundService(intent);
        }else{
            context.startService(intent);
        }
    }
}
