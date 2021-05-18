package com.esime.gloves.Providers;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.esime.gloves.Model.FCMBody;
import com.esime.gloves.Model.FCMResponse;
import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.Retrofit.IFCMApi;
import com.esime.gloves.MyApp.Retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;

public class NotificationProviders {



    private String url ="https://fcm.googleapis.com/";

    public NotificationProviders() {

    }
    public Call<FCMResponse> sendNotification(FCMBody body){
        return RetrofitClient.getClientObject(url).create(IFCMApi.class).send(body);
    }




}