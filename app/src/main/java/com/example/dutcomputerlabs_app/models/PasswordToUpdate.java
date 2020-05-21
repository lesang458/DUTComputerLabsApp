package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

public class PasswordToUpdate {
    @Expose
    private String OldPassword;
    @Expose
    private String NewPassword;

    public PasswordToUpdate(String oldPassword, String newPassword) {
        OldPassword = oldPassword;
        NewPassword = newPassword;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }
}