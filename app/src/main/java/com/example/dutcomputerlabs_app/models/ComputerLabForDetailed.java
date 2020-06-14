package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

public class ComputerLabForDetailed {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String condition;
    @Expose
    private int computers;
    @Expose
    private int damagedComputers;
    @Expose
    private int aircons;
    @Expose
    private String owner;
    @Expose
    private String ownerPhoneNumber;
    @Expose
    private String ownerEmail;

    public ComputerLabForDetailed(int id, String name, String condition, int computers, int damagedComputers, int aircons, String owner, String ownerPhoneNumber, String ownerEmail) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.computers = computers;
        this.damagedComputers = damagedComputers;
        this.aircons = aircons;
        this.owner = owner;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.ownerEmail = ownerEmail;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getComputers() {
        return computers;
    }

    public void setComputers(int computers) {
        this.computers = computers;
    }

    public int getDamagedComputers() {
        return damagedComputers;
    }

    public void setDamagedComputers(int damagedComputers) {
        this.damagedComputers = damagedComputers;
    }

    public int getAircons() {
        return aircons;
    }

    public void setAircons(int aircons) {
        this.aircons = aircons;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
