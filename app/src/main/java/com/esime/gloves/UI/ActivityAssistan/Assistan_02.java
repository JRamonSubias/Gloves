package com.esime.gloves.UI.ActivityAssistan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.R;
import com.esime.gloves.Room.NotificationDao;
import com.esime.gloves.Room.NotificationEntity;
import com.esime.gloves.Room.NotificationRoomDatabase;
import com.esime.gloves.UI.ActivityAssistan.ViewModel.RoomViewModel;

import java.util.ArrayList;
import java.util.List;


public class Assistan_02 extends Fragment {
    private ListView listNotification;
    private ArrayAdapter<String> adapter;
    private List<String> listTitle;
    private RoomViewModel viewModel;

    public Assistan_02() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        listTitle = new ArrayList<>();
        adapter = new ArrayAdapter<>(InstanceApp.getContext(), android.R.layout.simple_list_item_1,listTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_assistan_02, container, false);
        ConfigByid(view);
        showAllNotifications();

        return view;
    }

    private void showAllNotifications(){
        viewModel.getAllNotifications().observe(getActivity(), listNotifications->{
            if(!listNotifications.isEmpty()){
                for(int i=0; i<listNotifications.size();i++){
                    String data = listNotifications.get(i).getPatient();
                    data += "\n";
                    data += listNotifications.get(i).getTitle();
                    data += "\n";
                    data += listNotifications.get(i).getDataTime();
                    data += "\n";
                    data += listNotifications.get(i).getType();
                    listTitle.add(data);
                    adapter.notifyDataSetChanged();
                }
            }
            listNotification.setAdapter(adapter);

        });
    }

    private void ConfigByid(View view) {
        listNotification = view.findViewById(R.id.listNotification);
    }
}