package com.esime.gloves.UI.ActivityPresentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.esime.gloves.R;

public class ChooseOption extends Fragment {
    private ImageView btnAssistant, btnPatient;

    public ChooseOption() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_choose_option, container, false);
        configById(viewGroup);

        btnAssistant.setOnClickListener(v -> {
            SharedPreferenceManager.setSomeStringValue(InstanceApp.CHOOSE_USER,InstanceApp.ASSISTANT);
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment_presentations).navigate(R.id.signIn);
        });

        btnPatient.setOnClickListener(v -> {
            SharedPreferenceManager.setSomeStringValue(InstanceApp.CHOOSE_USER,InstanceApp.PATIENT);
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment_presentations).navigate(R.id.signIn);
        });

        return viewGroup;
    }

    private void configById(ViewGroup viewGroup) {
        btnAssistant = viewGroup.findViewById(R.id.btn_assitant);
        btnPatient = viewGroup.findViewById(R.id.btn_patient);
    }
}