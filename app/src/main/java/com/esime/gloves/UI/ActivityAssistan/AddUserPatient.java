package com.esime.gloves.UI.ActivityAssistan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.Providers.FCMProvider;
import com.esime.gloves.Providers.FDBProvider;
import com.esime.gloves.R;
import com.esime.gloves.UI.ActivityAssistan.ViewModel.AssistantViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddUserPatient extends DialogFragment {
    private TextInputLayout etUsername;
    private String username;
    private Button btnAddAssistant;
    private AssistantViewModel viewModel;

    public AddUserPatient() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AssistantViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_add_user_asisstant, container, false);
        ConfigViewById(viewGroup);
        btnAddAssistant.setOnClickListener(v -> {
            validateData();
        });
        return viewGroup;
    }

    private void validateData() {
        etUsername.setError(null);
        username = etUsername.getEditText().getText().toString();
         if (username.isEmpty()){
            etUsername.setError("Ingrese nombre de usuario");
        }else{
             addPatient(username);
         }

    }

    private void addPatient(String username) {
        viewModel.addUserPatient(username).observe(getActivity(), patient ->{
            if(!patient){
                etUsername.setError("Usuario no encontrado");
            }else{
                Toast.makeText(getContext(), "Se agrego paciente", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    private void ConfigViewById(ViewGroup viewGroup) {
        etUsername = viewGroup.findViewById(R.id.addUA_username);
        btnAddAssistant = viewGroup.findViewById(R.id.btn_addAssistant);
    }
}