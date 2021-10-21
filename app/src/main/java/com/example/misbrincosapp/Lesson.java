package com.example.misbrincosapp;

public class Lesson {

    private String name;
    private int duration;
    private int limit;


    public Lesson(String name, int duration, int limit) {
        this.name = name;
        this.duration = duration;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getLimit() {
        return limit;
    }

}
