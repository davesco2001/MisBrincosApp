package com.example.misbrincosapp;

public class Student {
    private String name;
    private String cc;
    private String phoneNumber;
    private String email;

    public Student(String name, String cc, String phoneNumber, String email) {
        this.name = name;
        this.cc = cc;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getCc() {
        return cc;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

}
