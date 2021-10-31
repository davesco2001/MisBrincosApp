package com.example.misbrincosapp;

public class Shoppings {
    private String ccStudent;
    private int idPackage;

    public Shoppings(String ccStudent, int idPackage) {
        this.ccStudent = ccStudent;
        this.idPackage = idPackage;
    }

    public String getCcStudent() {
        return ccStudent;
    }

    public int getIdPackage() {
        return idPackage;
    }
}
