package com.esime.gloves.UI;

import androidx.lifecycle.MutableLiveData;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.Room.NotificationDao;
import com.esime.gloves.Room.NotificationEntity;
import com.esime.gloves.Room.NotificationRoomDatabase;

import java.util.List;

public class RoomRepository {
    private NotificationDao notificationDao;
    private MutableLiveData<List<NotificationEntity>> listNotificationLivedata;

    public RoomRepository(){
        notificationDao = NotificationRoomDatabase.getInstance(InstanceApp.getContext()).getRoomDao();
    }

    public MutableLiveData<List<NotificationEntity>> getAllNotifications(){
        if(listNotificationLivedata == null){
            listNotificationLivedata = new MutableLiveData<>();
        }
        listNotificationLivedata.setValue(notificationDao.selectAllNotification());
        return listNotificationLivedata;
    }


}
