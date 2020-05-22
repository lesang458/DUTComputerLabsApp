package com.example.dutcomputerlabs_app.ultis;

import com.example.dutcomputerlabs_app.network.RetrofitClient;
import com.example.dutcomputerlabs_app.network.services.AuthService;
import com.example.dutcomputerlabs_app.network.services.UserService;

public class ApiUtils {
    public static final String BASE_URL = "https://192.168.1.12:5001";

    public static AuthService getAuthService() {
        return RetrofitClient.getClient(BASE_URL).create(AuthService.class);
    }

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
