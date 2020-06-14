package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class UserForDetailed {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private Date birthday;
    @Expose
    private String gender;
    @Expose
    private String faculty;
    @Expose
    private String phoneNumber;
    @Expose
    private String email;
    @Expose
    private String address;
    @Expose
    private String username;
    @Expose
    private String role;

    public UserForDetailed(int id, String name, Date birthday, String gender, String faculty, String phoneNumber, String email, String address, String username, String role) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.faculty = faculty;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.username = username;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
