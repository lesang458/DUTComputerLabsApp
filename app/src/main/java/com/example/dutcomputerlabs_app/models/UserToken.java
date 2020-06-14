package com.example.dutcomputerlabs_app.models;
import com.google.gson.annotations.Expose;

public class UserToken {
    @Expose
    private String token;
    @Expose
    private UserForDetailed user;

    public UserToken(String token, UserForDetailed user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserForDetailed getUser() {
        return user;
    }

    public void setUser(UserForDetailed user) {
        this.user = user;
    }
}
