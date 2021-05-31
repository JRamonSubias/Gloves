package com.esime.gloves.UI.ActivityPatient;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationActions;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esime.gloves.BLE.BleControllListener;
import com.esime.gloves.BLE.BleController;
import com.esime.gloves.Model.Data;
import com.esime.gloves.Model.Notification;
import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.esime.gloves.Providers.FCMProvider;
import com.esime.gloves.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Signs_1 extends Fragment implements BleControllListener {
    private DateFormat simpleDateFormat;
    private FCMProvider fcmProvider;
    private Data data;
    private ImageView ivGreen,ivYellow,ivRed;
    private TextView signl1, signl2,signl3;
    private BleController bleController;
    private Activity activity;

    public Signs_1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleDateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        fcmProvider = new FCMProvider();
        activity = getActivity();
    }

    @Override
    public void onStart() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onStart();
        this.bleController = BleController.getInstance(InstanceApp.getContext());   //Instanciar BleController
        this.bleController.addBLEControllListener(this); // Observador
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_signs1, container, false);
        ConfigById(view);
        return view;
    }



    private void ConfigById(View view) {
        ivGreen = view.findViewById(R.id.iv_s1_green);
        ivYellow = view.findViewById(R.id.iv_s1_yellow);
        ivRed = view.findViewById(R.id.iv_s1_red);


        signl1= view.findViewById(R.id.tv_s1_first);
        signl2= view.findViewById(R.id.tv_s1_second);
        signl3= view.findViewById(R.id.tv_s1_third);
    }


    @Override
    public void BLEControllerConnected() {

    }

    @Override
    public void BLEControllerDisconnected() {

    }

    @Override
    public void BLEDeviceFound(BluetoothDevice Devices) {

    }

    @Override
    public void BLEReadDataDevice(String mData) {
        Log.i(InstanceApp.TAG_BLE,mData);
        switch (mData){
            case "1":
                    ivGreen.setImageResource(R.drawable.green_on);
                    ivYellow.setImageResource(R.drawable.yellow_off);
                    ivRed.setImageResource(R.drawable.red_off);
                break;
            case "2":
                    ivGreen.setImageResource(R.drawable.green_off);
                    ivYellow.setImageResource(R.drawable.yellow_on);
                    ivRed.setImageResource(R.drawable.red_off);
                break;
            case "3":
                    ivGreen.setImageResource(R.drawable.green_off);
                    ivYellow.setImageResource(R.drawable.yellow_off);
                    ivRed.setImageResource(R.drawable.red_on);
                break;

            case "4":
                data = new Data(SharedPreferenceManager.getSomeStringValue(InstanceApp.USER_PATIENT),
                        signl1.getText().toString(),
                        "Esta es una prueba con la app y de la señal 1 ",
                        "stroy_123",
                        simpleDateFormat.format(new Date()),
                        "Tipo 1");
                fcmProvider.sendNotification(data);
                break;
            case "5":
                data = new Data(SharedPreferenceManager.getSomeStringValue(InstanceApp.USER_PATIENT),
                        signl2.getText().toString(),
                        "Esta es una prueba con la app y de la señal 2",
                        "stroy_1234",
                        simpleDateFormat.format(new Date()),
                        "Tipo 2");
                fcmProvider.sendNotification(data);
                break;
            case "6":
                Navigation.findNavController(activity,R.id.nav_host_fragment_patient).navigate(R.id.signs_2);
                onDestroy();
                break;
        }
    }
}