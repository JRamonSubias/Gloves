package com.esime.gloves.UI.ActivityPresentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.R;
import com.esime.gloves.UI.ActivityAssistan.ActivityAssistan;
import com.esime.gloves.UI.ActivityPresentation.ViewModel.PresentationViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends Fragment {

    private TextInputLayout etEmail,etPasword,etConfirmPassword;
    private Button btnSignUp;
    private String email, password,confirmPassword;
    private PresentationViewModel viewModel;

    public SignUp() {
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
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_sign_up, container, false);
        ConfigViewById(view);
        btnSignUp.setOnClickListener(v -> {
            validateData();
        });
        return view;
    }

    private void validateData() {
        etEmail.setError(null);
        etPasword.setError(null);
        etConfirmPassword.setError(null);

        email= etEmail.getEditText().getText().toString();
        password = etPasword.getEditText().getText().toString();
        confirmPassword = etConfirmPassword.getEditText().getText().toString();


        if (email.isEmpty()) {
            etEmail.setError("Ingrese Usuario");
        } else if (password.isEmpty()) {
            etPasword.setError("Ingrese Contraseña");
        } else if (password.length() < 6) {
            etPasword.setError("Minimo 6 caracteres en la contraseña");
        } else if (!password.equals(confirmPassword)){
            etConfirmPassword.setError("Contraseñas no coinciden");
        }else{
            registerAssistant(email,password);
        }
    }

    private void registerAssistant(String email, String password){
        viewModel.registerAssistant(email,password).observe(getActivity(), assistant->{
            if(assistant){
                goActivity();
            }else{
                etEmail.setError("Hubo un problema, intente con otro correo.");
            }
        });
    }



    private void goActivity(){
        Intent intent = new Intent(getActivity(), ActivityAssistan.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void ConfigViewById(View view) {
        etEmail = view.findViewById(R.id.singUpETEmail);
        etPasword = view.findViewById(R.id.singUpEtpassword);
        etConfirmPassword = view.findViewById(R.id.singUpEtConfirmPassword);
        btnSignUp = view.findViewById(R.id.SignUp_btnSign);
    }
}