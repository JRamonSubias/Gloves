package com.esime.gloves.Providers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FDBProvider {

    public static DatabaseReference getInstance(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference taskPatients(){
        return FDBProvider.getInstance().child("Patients");
    }

    public static DatabaseReference taskAssistant(){
        return FDBProvider.getInstance().child("Assistants");
    }
}
