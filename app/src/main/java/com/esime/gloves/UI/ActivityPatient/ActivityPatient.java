package com.esime.gloves.UI.ActivityPatient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

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

public class ActivityPatient extends AppCompatActivity implements BleControllListener {
    private DateFormat simpleDateFormat;
    private BleController bleController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        simpleDateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        bleController = BleController.getInstance(InstanceApp.getContext());
        bleController.addBLEControllListener(this);
    }


    @Override
    public void BLEControllerConnected() {

    }

    @Override
    public void BLEControllerDisconnected() {
        Log.i(InstanceApp.TAG_BLE,"Desconectado");
        FCMProvider fcmProvider = new FCMProvider();
        Data data = new Data(SharedPreferenceManager.getSomeStringValue(InstanceApp.USER_PATIENT),
                "Se Desconecto del Bluetooth",
                "El paciente se desconecto del bluetooth",
                "stroy_123",
                simpleDateFormat.format(new Date()),
                "Tipo 4");
        fcmProvider.sendNotification(data);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(ActivityPatient.this,R.id.nav_host_fragment_patient).navigate(R.id.bluetoothShow);
            }
        });
    }

    @Override
    public void BLEDeviceFound(BluetoothDevice Devices) {

    }

    @Override
    public void BLEReadDataDevice(String mData) {

    }
}