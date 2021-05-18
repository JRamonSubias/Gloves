package com.esime.gloves.MyApp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.room.Room;

import com.esime.gloves.Channel.NotificationHelper;
import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.Room.NotificationDao;
import com.esime.gloves.Room.NotificationEntity;
import com.esime.gloves.Room.NotificationRoomDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseCloudMessagin extends FirebaseMessagingService {
    private NotificationDao notificationDao;


    @Override public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String Patient = data.get("Patient");
        String Title = data.get("Title");
        String Body = data.get("Body");
        String time = data.get("Time");
        String type = data.get("Type");
        showNotification(Title,Body);
        saveNotification(Patient,Title,Body,time,type);
    }


    private void showNotification(String title,String body) {
        PendingIntent intent = PendingIntent.getActivity(getBaseContext(),0,new Intent(),PendingIntent.FLAG_ONE_SHOT);
        NotificationHelper notificationHelper = new NotificationHelper(getBaseContext());
        Notification.Builder builder = notificationHelper.getNotification(title,body,intent);
        notificationHelper.getManager().notify(1,builder.build());
    }

    private void saveNotification(String patient,String title, String body,String time,String type) {
         notificationDao = NotificationRoomDatabase.getInstance(InstanceApp.getContext()).getRoomDao();
         notificationDao.insertNotification(new NotificationEntity(patient,title,body,time,type));
    }


}
