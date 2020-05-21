package com.example.dutcomputerlabs_app.models;

public class Faculty {
    private int Id;
    private String Name;

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

    public Faculty(int id, String name) {
        Id = id;
        Name = name;
    }

    public Faculty(Faculty faculty){
        Id = faculty.getId();
        Name = faculty.getName();
    }
}
