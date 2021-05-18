package com.esime.gloves.UI.ActivityAssistan.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esime.gloves.Room.NotificationEntity;
import com.esime.gloves.UI.RoomRepository;

import java.util.List;

public class RoomViewModel extends ViewModel {
    private RoomRepository mRepository;

    public RoomViewModel(){
        mRepository = new RoomRepository();
    }

    public MutableLiveData<List<NotificationEntity>> getAllNotifications(){
        return mRepository.getAllNotifications();
    }
}
