package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

public class PasswordToUpdate {
    @Expose
    private String oldPassword;
    @Expose
    private String newPassword;

    public PasswordToUpdate(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}