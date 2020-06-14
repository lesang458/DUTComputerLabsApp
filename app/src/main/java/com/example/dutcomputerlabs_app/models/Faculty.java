package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

public class Faculty {
    @Expose
    private int id;
    @Expose
    private String name;

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

    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Faculty(Faculty faculty){
        id = faculty.getId();
        name = faculty.getName();
    }
}
