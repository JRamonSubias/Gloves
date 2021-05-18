package com.esime.gloves;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.Providers.FCMProvider;
import com.esime.gloves.Providers.FDBProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRepository {
    private AuthProvider mAuth;
    private FCMProvider messaging;
    private MutableLiveData<Boolean> userExist;
    private MutableLiveData<List<String>> listPatientSaved;
    private MutableLiveData<Boolean> registerPatient;
    private MutableLiveData<Boolean> signIn;
    private MutableLiveData<Boolean> signUp;

    public FirebaseRepository() {
        mAuth = AuthProvider.getInstance();
        messaging = FCMProvider.getInstance();
    }

    public MutableLiveData<Boolean> addUserPatient(String username) {
        if(userExist == null){
            userExist = new MutableLiveData<>();
        }
        FDBProvider.taskPatients().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int flag = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.i(InstanceApp.TAG_Firebase, "Datos: " + snapshot.getValue());
                    if (dataSnapshot.getKey().equals(username)) {
                        flag++;
                        Map<String, Object> mPatient = new HashMap<>();
                        mPatient.put("Status", false);
                        FDBProvider.taskAssistant()
                                .child(mAuth.getID())
                                .child("Patients")
                                .child(username).setValue(mPatient).addOnCompleteListener(task -> {
                            messaging.subcribeToTopic(username);
                        });
                        userExist.setValue(true);
                    }
                } if(flag == 0){
                    userExist.setValue(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
        return userExist;
    }

    public MutableLiveData<List<String>>  getPatientsSaved(){
        if(listPatientSaved == null){
            listPatientSaved = new MutableLiveData<>();
        }
        FDBProvider.taskAssistant().child(mAuth.getID()).child("Patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> patients = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Log.i(InstanceApp.TAG_Firebase,dataSnapshot.getKey());
                    patients.add(dataSnapshot.getKey());
                }
                listPatientSaved.setValue(patients);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listPatientSaved;
    }

    public void UnsubscribeToTopicAndDeletePatient(String topic){
        FDBProvider.taskAssistant().child(mAuth.getID()).child("Patients").child(topic).removeValue();
        messaging.UnsubscribeToTopic(topic);
    }

    public MutableLiveData<Boolean> registerPatient(String username,String password){
        if(registerPatient == null){
            registerPatient = new MutableLiveData<>();
        }
        mAuth.SignUp(username+"@gmail.com",password).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                registerPatient.setValue(false);
                return;
            }else{
                Map<String,Object> mapPatient = new HashMap<>();
                mapPatient.put("Status",false);
                FDBProvider.taskPatients().child(username).setValue(mapPatient).addOnCompleteListener(taskDatabase -> {
                    if(taskDatabase.isSuccessful()){
                        SharedPreferenceManager.setSomeStringValue(InstanceApp.USER_PATIENT,username);
                        SharedPreferenceManager.setSomeStringValue(InstanceApp.TOPIC,"/topics/"+username);
                        registerPatient.setValue(true);
                    }else{
                        registerPatient.setValue(false);
                    }
                });
            }
        });

        return registerPatient;
    }

    public MutableLiveData<Boolean> registerAssistant(String email,String password){
        if(signUp == null){
            signUp = new MutableLiveData<>();
        }
        mAuth.SignUp(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                signUp.setValue(true);
                Map<String,Object> mapAssistant = new HashMap<>();
                mapAssistant.put("user",email);
                FDBProvider.taskAssistant().child(mAuth.getID()).setValue(mapAssistant).addOnCompleteListener(taskDatabase->{
                    if(taskDatabase.isSuccessful()){
                        signUp.setValue(true);
                    }
                });
            }else {
                signUp.setValue(false);
            }
        });
        return signUp;
    }

    public MutableLiveData<Boolean> singIn(String email,String password){
        if(signIn == null){
            signIn = new MutableLiveData<>();
        }
        if(SharedPreferenceManager.getSomeStringValue(InstanceApp.CHOOSE_USER).equals(InstanceApp.PATIENT)){
            email += "@gmail.com";
        }
        mAuth.SignIn(email,password).addOnCompleteListener(task -> {
            Log.i(InstanceApp.TAG_Firebase,task.getResult().toString());
            if(task.isSuccessful()){
                signIn.setValue(true);
            }else{
                signIn.setValue(false);
            }
        });
        return signIn;
    }

    public void ChangeStatusPatient(Boolean conection){
        Map<String,Object> status = new HashMap<>();
        if(conection)
        status.put("Status",true);
        else
            status.put("Status",false);

        FDBProvider.taskPatients().child(InstanceApp.USER_PATIENT).updateChildren(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               Log.i(InstanceApp.TAG_Firebase, "Status: "+conection);
            }
        });
    }
}