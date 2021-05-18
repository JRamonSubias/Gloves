package com.esime.gloves.MyApp;

import android.app.Application;
import android.content.Context;

import java.util.UUID;

public class InstanceApp extends Application {

    public static final String TAG_BLE = "[BLE]";
    public static final String TAG_Firebase = "[FIREBASE]";

    //IDENTIFICADORES UNICOS PARA LA CONEXION A ARDUINO
    public static final UUID DESCRIPTOR_CONFIG_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final  UUID SERVICE_UUID = UUID.fromString("19B10000-E8F2-537E-4F6C-D104768A1214");
    public static final  UUID CHARACTERISTIC_COUNTER_UUID = UUID.fromString("19B10001-E8F2-537E-4F6C-D104768A1214");

    public static final String APP_SETTING_FILE ="ID_GLOVES";
    public static final String CHOOSE_USER ="USER_CHOOSE";
    public static final String PATIENT ="PATIENT";
    public static final String ASSISTANT ="ASSISTANT";
    public static final String USER_PATIENT ="USER_PATIENT";
    public static final String TOPIC ="Topic";


    private static InstanceApp instance;

    public static InstanceApp getInstance(){return instance;}
    public static Context getContext(){return instance;}

    @Override
    public void onCreate() {
        instance=this;
        super.onCreate();
    }
}
