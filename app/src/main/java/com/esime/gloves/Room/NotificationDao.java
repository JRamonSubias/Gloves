package com.esime.gloves.Room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
        void insertNotification(NotificationEntity notificationEntity);


    @Query("SELECT * FROM notifications")
    List<NotificationEntity> selectAllNotification();
}
