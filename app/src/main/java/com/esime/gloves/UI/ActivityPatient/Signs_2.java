package com.esime.gloves.UI.ActivityPatient;

import android.Manifest;
import android.animation.ValueAnimator;
import android.bluetooth.BluetoothDevice;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.esime.gloves.BLE.BleControllListener;
import com.esime.gloves.BLE.BleController;
import com.esime.gloves.Model.Data;
import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.esime.gloves.Providers.FCMProvider;
import com.esime.gloves.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Signs_2 extends Fragment implements BleControllListener {
    private Data data;
    private ImageView ivGreen,ivYellow,ivRed;
    private DateFormat simpleDateFormat;
    private FCMProvider fcmProvider;
    private BleController bleController;
    private TextView signl1, signl2,signl3;
    private LottieAnimationView lottieAnimationView;

    public Signs_2() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleDateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        fcmProvider = new FCMProvider();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_signs_2, container, false);
        ConfigById(view);

        ivGreen.setOnClickListener(v -> {
            OnLottieAnimation();
        });

        return view;
    }

    private void ConfigById(View view) {
        ivGreen = view.findViewById(R.id.iv_s2_green);
        ivYellow = view.findViewById(R.id.iv_s2_yellow);
        ivRed = view.findViewById(R.id.iv_s2_red);

        signl1= view.findViewById(R.id.tv_s2_first);
        signl2= view.findViewById(R.id.tv_s2_second);
        signl3= view.findViewById(R.id.tv_s2_third);

        lottieAnimationView = view.findViewById(R.id.lottie_send);
    }


    private void  OnLottieAnimation(){
            lottieAnimationView.setSpeed(1f);
            lottieAnimationView.playAnimation();

    }

    private void goToSign1(){
        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_patient).navigate(R.id.signs_2);
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
                goToSign1();
                break;
            case "5":
                data = new Data(SharedPreferenceManager.getSomeStringValue(InstanceApp.USER_PATIENT),
                        signl2.getText().toString(),
                        "Esta es una prueba con la app y de la señal 2",
                        "stroy_1234",
                        simpleDateFormat.format(new Date()),
                        "Tipo 2");
                fcmProvider.sendNotification(data);
                goToSign1();
                break;
            case "6":
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_patient).navigate(R.id.signs_2);
                data = new Data(SharedPreferenceManager.getSomeStringValue(InstanceApp.USER_PATIENT),
                        signl3.getText().toString(),
                        "Esta es una prueba con la app y de la señal 3 ",
                        "stroy_12345",
                        simpleDateFormat.format(new Date()),
                        "Tipo 3");
                fcmProvider.sendNotification(data);
                goToSign1();
                break;
        }
    }
}