package com.esime.gloves.Room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NotificationEntity.class},version = 2, exportSchema = false)
public abstract class NotificationRoomDatabase extends RoomDatabase {
    private static final String DB_NAME = "db_notification";
    private static  NotificationRoomDatabase instance;


    public static NotificationRoomDatabase getInstance(Context context){
        if(instance == null){
            synchronized (NotificationRoomDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            NotificationRoomDatabase.class,DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }

    public abstract NotificationDao getRoomDao();



}
