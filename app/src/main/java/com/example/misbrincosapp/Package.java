package com.example.misbrincosapp;

public class Package {
    private int id;
    private String plan;
    private int totalOfDays;
    private int totalOfLessons;
    private String price;

    public Package(int id, String plan, int totalOfDays, int totalOfLessons, String price) {
        this.id = id;
        this.plan = plan;
        this.totalOfDays = totalOfDays;
        this.totalOfLessons = totalOfLessons;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public String getPlan() {
        return plan;
    }

    public int getTotalOfDays() {
        return totalOfDays;
    }

    public int getTotalOfLessons() {
        return totalOfLessons;
    }

    public String getPrice() {
        return price;
    }
}
