package com.esime.gloves.Channel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.R;

public class NotificationHelper extends ContextWrapper {
    private static final String CHANNEL_ID = "com.esime.pruebacloudmessagin";
    private static final String CHANNEL_NAME = "Glove";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createChannel();
    }

    private void createChannel(){
        NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(ContextCompat.getColor(InstanceApp.getContext(), R.color.purple_200));
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationChannel.setSound(sound,null);
        getManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager getManager(){
        if(manager==null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public Notification.Builder getNotification(String title, String body, PendingIntent intent){
        return new Notification.Builder(InstanceApp.getContext(),CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setColor(color(R.color.pink))
                .setContentIntent(intent)
                .setSmallIcon(R.drawable.ic_notification);
    }

    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(this, res);
    }


}
