package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

public class ComputerLab {
    @Expose
    private int Id;
    @Expose
    private String Name;
    @Expose
    private String Condition;
    @Expose
    private int Computers;
    @Expose
    private int DamagedComputers;
    @Expose
    private int Aircons;
    @Expose
    private int EditMode;

    public ComputerLab(int id, String name, String condition, int computers, int damagedComputers, int aircons, int editMode) {
        Id = id;
        Name = name;
        Condition = condition;
        Computers = computers;
        DamagedComputers = damagedComputers;
        Aircons = aircons;
        EditMode = editMode;
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

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public int getComputers() {
        return Computers;
    }

    public void setComputers(int computers) {
        Computers = computers;
    }

    public int getDamagedComputers() {
        return DamagedComputers;
    }

    public void setDamagedComputers(int damagedComputers) {
        DamagedComputers = damagedComputers;
    }

    public int getAircons() {
        return Aircons;
    }

    public void setAircons(int aircons) {
        Aircons = aircons;
    }

    public int getEditMode() {
        return EditMode;
    }

    public void setEditMode(int editMode) {
        EditMode = editMode;
    }
}
