package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class UserForDetailed {
    @Expose
    private int Id;
    @Expose
    private String Name;
    @Expose
    private Date Birthday;
    @Expose
    private String Gender;
    @Expose
    private String Faculty;
    @Expose
    private String PhoneNumber;
    @Expose
    private String Email;
    @Expose
    private String Address;
    @Expose
    private String Username;
    @Expose
    private String Role;

    public UserForDetailed(int id, String name, Date birthday, String gender, String faculty, String phoneNumber, String email, String address, String username, String role) {
        Id = id;
        Name = name;
        Birthday = birthday;
        Gender = gender;
        Faculty = faculty;
        PhoneNumber = phoneNumber;
        Email = email;
        Address = address;
        Username = username;
        Role = role;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
