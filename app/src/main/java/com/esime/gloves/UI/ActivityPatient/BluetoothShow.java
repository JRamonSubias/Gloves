package com.esime.gloves.UI.ActivityPatient;

import android.Manifest;
import android.animation.ValueAnimator;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.esime.gloves.BLE.BleControllListener;
import com.esime.gloves.BLE.BleController;
import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.R;
import com.esime.gloves.UI.ActivityPatient.ViewModel.PatientViewModel;
import com.esime.gloves.UI.ActivityPresentation.ActivityPresentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BluetoothShow extends Fragment implements BleControllListener {
    private LottieAnimationView lottieAnimationView;
    private ListView listViewDevicesBle;
    private ImageView imgSetting;

    private List<BluetoothDevice> listDevices = new LinkedList<>();
    private ArrayAdapter<String> adapter;
    private List<String> listBle = new ArrayList<>();

    private BleController bleController;
    private AuthProvider mAuth;

    private PatientViewModel  viewModel;


    public BluetoothShow() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<>(InstanceApp.getContext(), android.R.layout.simple_list_item_1,listBle);
        bleController = BleController.getInstance(InstanceApp.getContext());
        mAuth = AuthProvider.getInstance();
        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);
    }

    @Override
    public void onStart() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        super.onStart();
        if(!BluetoothAdapter.getDefaultAdapter().isEnabled()){
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, 1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermissions();
        checkBLESupport();
        this.bleController = BleController.getInstance(InstanceApp.getContext());   //Instanciar BleController
        this.bleController.addBLEControllListener(this); // Observador
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_bluetooth_show, container, false);
        ConfigViewById(viewGroup);

        lottieAnimationView.setOnClickListener(v -> {
                lottieAnimationView.setSpeed(1f);
                lottieAnimationView.playAnimation();
                lottieAnimationView.setMinProgress(0.5f);
                lottieAnimationView.setRepeatCount(ValueAnimator.INFINITE);
            if (ContextCompat.checkSelfPermission(InstanceApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                bleController.init();
            }

        });

        imgSetting.setOnClickListener(v -> {
            showMenu(v,R.id.img_settings_patient);
        });

        listViewDevicesBle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), listDevices.get(position).getAddress(), Toast.LENGTH_SHORT).show();
                bleController.connectToDevice(listDevices.get(position));

            }
        });

        return viewGroup;
    }



    private void showMenu(View v, int menu_settings) {
        PopupMenu popupMenu = new PopupMenu(InstanceApp.getContext(),v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_settings,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch ((item.getItemId())){
                    case R.id.menu_log_out:
                        mAuth.logOut();
                        SharedPreferenceManager.ClearPreference();
                        Intent intent = new Intent(getContext(), ActivityPresentation.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    default: return false;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void ConfigViewById(ViewGroup viewGroup) {
        listViewDevicesBle =  viewGroup.findViewById(R.id.listDevicesBle);
        lottieAnimationView = viewGroup.findViewById(R.id.lottie_bluetooth);
        imgSetting = viewGroup.findViewById(R.id.img_settings_patient);
    }


    private void checkPermissions(){
        // Checar si tiene los permisos si no pedirlos
        if (ContextCompat.checkSelfPermission(InstanceApp.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(InstanceApp.getContext(), "Whitout this permission Blutooth devices cannot be searched!", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    42);
        }
    }

    private void checkBLESupport() {
        // Check if BLE is supported on the device.
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(InstanceApp.getContext(), "BLE not supported!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override
    public void BLEControllerConnected() {
        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_patient).navigate(R.id.signs_1);
    }



    @Override
    public void BLEControllerDisconnected() {

    }

    @Override
    public void BLEDeviceFound(BluetoothDevice Devices) {
        this.listDevices.add(Devices);
        this.listViewDevicesBle.setAdapter(this.adapter);
        String device;
        if(Devices.getName() == null){
            device = "Unknow\n";
        }else{
            device = Devices.getName()+"\n";
        }
        device += Devices.getAddress();
        this.adapter.add(device);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void BLEReadDataDevice(String mData) {

    }

}