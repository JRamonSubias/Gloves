package com.esime.gloves.BLE;

import android.bluetooth.BluetoothDevice;

public interface BleControllListener {

    public void BLEControllerConnected();
    public void BLEControllerDisconnected();
    public void BLEDeviceFound(BluetoothDevice Devices);
    public void BLEReadDataDevice(String mData);

}
