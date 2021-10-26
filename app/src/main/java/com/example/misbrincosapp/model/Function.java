package com.example.misbrincosapp.model;

public class Function {
    private String name;
    private int type;

    public Function(String name, int type) {
        this.name = name;
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
