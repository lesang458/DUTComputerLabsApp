package com.example.dutcomputerlabs_app.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserForLogin {
    @Expose
    @SerializedName("username")
    private String Username;
    @Expose
    @SerializedName("password")
    private String Password;

    public UserForLogin(String username, String password) {
        Username = username;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
