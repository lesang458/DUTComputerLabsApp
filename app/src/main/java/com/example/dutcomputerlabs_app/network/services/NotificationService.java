package com.example.dutcomputerlabs_app.network.services;

import com.example.dutcomputerlabs_app.models.NotificationForDetailed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NotificationService {
    @GET("/api/notifications/booker")
    Call<List<NotificationForDetailed>> getNotifications(@Header("Authorization") String token);
}
