package com.esime.gloves.UI.ActivityPresentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.R;
import com.esime.gloves.UI.ActivityAssistan.ActivityAssistan;
import com.esime.gloves.UI.ActivityPatient.ActivityPatient;
import com.esime.gloves.UI.ActivityPresentation.ViewModel.PresentationViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class SignIn extends Fragment {
    private TextInputLayout etEmail,etPassword;
    private TextView tvSignUp;
    private Button btnSignIn;
    private String email, password;
    private AuthProvider mAuth;
    private String userOption;
    private PresentationViewModel viewModel;

    public SignIn() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = AuthProvider.getInstance();
        userOption = SharedPreferenceManager.getSomeStringValue(InstanceApp.CHOOSE_USER);
        viewModel = new ViewModelProvider(this).get(PresentationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_sign_in, container, false);
        ConfigViewById(viewGroup);

        btnSignIn.setOnClickListener(v -> {
            validateData();
        });

        tvSignUp.setOnClickListener(v -> {
            if(userOption.equals(InstanceApp.ASSISTANT)){
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_presentations).navigate(R.id.signUp);
            }else if (userOption.equals(InstanceApp.PATIENT)) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_presentations).navigate(R.id.registerPatient);
            }
        });
        return viewGroup;
    }

    private void validateData() {
        etPassword.setError(null);
        etEmail.setError(null);
        email = etEmail.getEditText().getText().toString();
        password = etPassword.getEditText().getText().toString();

        if(email.isEmpty()){
            etEmail.setError("Email vacio");
        }else if(password.isEmpty()){
            etPassword.setError("Password Vacio");
        }else{
            signInAuthentication(email,password);
        }
    }

    private void signInAuthentication(String email, String password){
        viewModel.signInAuthentication(email,password).observe(getActivity(), signIn->{
            if(signIn){
                if(userOption.equals(InstanceApp.ASSISTANT)){
                    Intent intent = new Intent(getActivity(), ActivityAssistan.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else if (userOption.equals(InstanceApp.PATIENT)) {
                    Intent intent = new Intent(getActivity(), ActivityPatient.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    SharedPreferenceManager.setSomeStringValue(InstanceApp.USER_PATIENT,email);
                    SharedPreferenceManager.setSomeStringValue(InstanceApp.TOPIC,"/topics/"+email);
                    startActivity(intent);
                }
            }else{
                Toast.makeText(getContext(), "Verifique sus datos", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void ConfigViewById(ViewGroup viewGroup) {
        etEmail = viewGroup.findViewById(R.id.singInETUser);
        etPassword = viewGroup.findViewById(R.id.singInEtpassword);
        btnSignIn = viewGroup.findViewById(R.id.singInBtn);
        tvSignUp = viewGroup.findViewById(R.id.singIn_textViewSingUp);
    }
}