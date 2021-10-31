package com.example.misbrincosapp.model;

public class Teacher {
    private String cC;
    private String name;
    private String email;

    public Teacher(String cC, String name, String email) {
        this.cC = cC;
        this.name = name;
        this.email = email;
    }

    public String getcC() {
        return cC;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
