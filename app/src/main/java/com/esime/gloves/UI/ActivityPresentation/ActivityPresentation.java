package com.esime.gloves.UI.ActivityPresentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.esime.gloves.R;
import com.esime.gloves.UI.ActivityAssistan.ActivityAssistan;
import com.esime.gloves.UI.ActivityPatient.ActivityPatient;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityPresentation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_Gloves);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);


    }

    @Override
    protected void onStart() {
        super.onStart();

      if(SharedPreferenceManager.getSomeStringValue(InstanceApp.CHOOSE_USER) != null )
         if(SharedPreferenceManager.getSomeStringValue(InstanceApp.CHOOSE_USER).equals(InstanceApp.ASSISTANT)){
            if(FirebaseAuth.getInstance().getCurrentUser() != null){
                Intent intent = new Intent(this, ActivityAssistan.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
         }else if(SharedPreferenceManager.getSomeStringValue(InstanceApp.CHOOSE_USER).equals(InstanceApp.PATIENT)){
             if(FirebaseAuth.getInstance().getCurrentUser() != null){
                 Intent intent = new Intent(this, ActivityPatient.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 startActivity(intent);
             }
         }

    }
}