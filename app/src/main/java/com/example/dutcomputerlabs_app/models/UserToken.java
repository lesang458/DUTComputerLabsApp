package com.example.dutcomputerlabs_app.models;
import com.google.gson.annotations.Expose;

public class UserToken {
    @Expose
    private String Token;
    @Expose
    private int UserId;
    @Expose
    private String RoleName;

    public UserToken(String token, int userId, String roleName) {
        Token = token;
        UserId = userId;
        RoleName = roleName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
