package com.esime.gloves.Providers;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.esime.gloves.Model.Data;
import com.esime.gloves.Model.FCMBody;
import com.esime.gloves.Model.FCMResponse;
import com.esime.gloves.Model.Notification;
import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.MyApp.SharedPreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FCMProvider {
    private NotificationProviders mNotificationProvider;
    private FirebaseMessaging messaging;
    private static FCMProvider instance;

    public FCMProvider(){
        mNotificationProvider = new NotificationProviders();
        messaging = FirebaseMessaging.getInstance();
    }

    public static FCMProvider getInstance(){
        if(instance == null){
            instance = new FCMProvider();
        }
        return instance;
    }


    public void sendNotification(Data data) {
                FCMBody fcmBody = new FCMBody(SharedPreferenceManager.getSomeStringValue(InstanceApp.TOPIC),data);
                mNotificationProvider.sendNotification(fcmBody).enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                        if(response.body() != null){
                                Toast.makeText(InstanceApp.getContext(), "La notificacion se ah enviado correctamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(InstanceApp.getContext(), "no se pudo", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<FCMResponse> call, Throwable t) {
                        Log.d("ERROR", "ErrorRetrofit: "+ t.getMessage());
                        Toast.makeText(InstanceApp.getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void subcribeToTopic(String topic){
        messaging.subscribeToTopic(topic).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(InstanceApp.TAG_Firebase,"Subscribe to "+ topic);
                }else{
                    Log.i(InstanceApp.TAG_Firebase,"NO Subscribe to "+ topic);
                }
            }
        });
    }

    public void UnsubscribeToTopic(String topic){
        messaging.unsubscribeFromTopic(topic).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(InstanceApp.TAG_Firebase,"UnSubscribe to "+ topic);
                }else{
                    Log.i(InstanceApp.TAG_Firebase,"NO UnSubscribe to "+ topic);
                }
            }
        });
    }


}
