package com.esime.gloves.UI.ActivityPresentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.Providers.FDBProvider;
import com.esime.gloves.R;
import com.esime.gloves.UI.ActivityPatient.ActivityPatient;
import com.esime.gloves.UI.ActivityPresentation.ViewModel.PresentationViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

import java.util.HashMap;
import java.util.Map;

public class RegisterPatient extends Fragment {
    private TextInputLayout etUserName, etPassword, etConfirmPassword;
    private Button btnRegisertpatient;
    private String userName, password, confirmPassword;
    private PresentationViewModel viewModel;

    public RegisterPatient() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PresentationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_register_patient, container, false);
        ConfigViewById(view);

        btnRegisertpatient.setOnClickListener(v -> {
            validateData();
        });
        return view;
    }

    private void validateData() {
        etUserName.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);

        userName= etUserName.getEditText().getText().toString();
        password = etPassword.getEditText().getText().toString();
        confirmPassword = etConfirmPassword.getEditText().getText().toString();

        if (userName.isEmpty()) {
            etUserName.setError("Ingrese Usuario");
        } else if (password.isEmpty()) {
            etPassword.setError("Ingrese Contraseña");
        } else if (password.length() < 6) {
            etPassword.setError("Minimo 6 caracteres en la contraseña");
        } else if (!password.equals(confirmPassword)){
            etConfirmPassword.setError("Contraseñas no coinciden");
        }else{
            registerPatient(userName,password);
        }
    }

    private void registerPatient(String userName, String password){
        viewModel.registerPatient(userName,password).observe(getActivity(),patient ->{
            if(patient){
                goActivity();
            }else{
                etUserName.setError("Nombre de usuario ya esta en uso");
            }
        });
    }


    private void goActivity(){
        Intent intent = new Intent(getActivity(), ActivityPatient.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void ConfigViewById(ViewGroup view) {
        etUserName = view.findViewById(R.id.registerPatient_etUserName);
        etPassword = view.findViewById(R.id.registerPatient_etpassword);
        etConfirmPassword = view.findViewById(R.id.registerPatient_etConfirmpassword);
        btnRegisertpatient = view.findViewById(R.id.btn_registerPatient);
    }


}