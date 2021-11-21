package com.francotte.go4lunch_opc.service;



import com.francotte.go4lunch_opc.models.NOTIFICATION.MyResponse;
import com.francotte.go4lunch_opc.models.NOTIFICATION.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MessageAPIService {

    
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA6P9WHbs:APA91bHbiVKHR11rEfJfLpDI9MTSrMf8NPoQ1IYVyR_jv57fbGHCWAMZa-q0NTUr9zxHjznxRtpzD2TpqzwRTHZWq-uEdomuqYaJHUoUGDd57Q-c74ZwiWLv-uqHKhtzClPzY1bbDFD4"
    })

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Notification body);
}

