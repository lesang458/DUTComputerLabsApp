package com.example.dutcomputerlabs_app.network.services;

import com.example.dutcomputerlabs_app.models.UserForLogin;
import com.example.dutcomputerlabs_app.models.UserToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/api/auths/login")
    Call<UserToken> login(@Body UserForLogin user);
}
