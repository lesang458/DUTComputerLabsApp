package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class UserForInsert {
    @Expose
    private String Name;
    @Expose
    private Date Birthday;
    @Expose
    private boolean Gender;
    @Expose
    private Faculty Faculty;
    @Expose
    private String PhoneNumber;
    @Expose
    private String Email;
    @Expose
    private String Address;
    @Expose
    private String Username;
    @Expose
    private String Password;
    @Expose
    private String Role;

    public UserForInsert(String name, Date birthday, boolean gender, com.example.dutcomputerlabs_app.models.Faculty faculty, String phoneNumber, String email, String address, String username, String password, String role) {
        Name = name;
        Birthday = birthday;
        Gender = gender;
        Faculty = faculty;
        PhoneNumber = phoneNumber;
        Email = email;
        Address = address;
        Username = username;
        Password = password;
        Role = role;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public com.example.dutcomputerlabs_app.models.Faculty getFaculty() {
        return Faculty;
    }

    public void setFaculty(com.example.dutcomputerlabs_app.models.Faculty faculty) {
        Faculty = faculty;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
