package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class UserForInsert {
    @Expose
    private String name;
    @Expose
    private String photo;
    @Expose
    private Date birthday;
    @Expose
    private boolean gender;
    @Expose
    private Faculty faculty;
    @Expose
    private String phoneNumber;
    @Expose
    private String email;
    @Expose
    private String address;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String role;

    public UserForInsert(String name, String photo, Date birthday, boolean gender, Faculty faculty, String phoneNumber, String email, String address, String username, String password, String role) {
        this.name = name;
        this.photo = photo;
        this.birthday = birthday;
        this.gender = gender;
        this.faculty = faculty;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public com.example.dutcomputerlabs_app.models.Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(com.example.dutcomputerlabs_app.models.Faculty faculty) {
        this.faculty = faculty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
