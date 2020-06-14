package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

public class ComputerLabForList {
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
    private int editMode;

    public ComputerLabForList(int id, String name, String condition, int computers, int damagedComputers, int aircons, int editMode) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.computers = computers;
        this.damagedComputers = damagedComputers;
        this.aircons = aircons;
        this.editMode = editMode;
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

    public int getEditMode() {
        return editMode;
    }

    public void setEditMode(int editMode) {
        this.editMode = editMode;
    }
}
