package com.esime.gloves.MyApp.Retrofit;

import com.esime.gloves.Model.FCMBody;
import com.esime.gloves.Model.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAIGZWnYg:APA91bGHnMR5YB6j5a6HKZTL9MwJxlDPG7lUaxbJ4jCVxfGGUdS6syou4lNIAOX4wREiany4oupiW8Z30xJveU3LycN6SsdeF88NGC1-FVrui_hPpg3wuezRBHWU15Zkcn0AJw_jSSRd"
    })
    @POST("fcm/send")
    Call<FCMResponse> send(@Body FCMBody body);
}
