package com.example.misbrincosapp.model;

import java.util.Date;

public class Session {
    private int id;
    private String lessonName;
    private Date date;
    private String day;
    private String hour;
    private String teacherName;
    private int roomNumber;
    private int typeOfRoom;

    public Session(int id, String lessonName, Date date, String day, String hour, String teacherName, int roomNumber, int typeOfRoom) {
        this.id = id;
        this.lessonName = lessonName;
        this.date = date;
        this.day = day;
        this.hour = hour;
        this.teacherName = teacherName;
        this.roomNumber = roomNumber;
        this.typeOfRoom = typeOfRoom;
    }
    public Session(int id, String lessonName, Date date){
        this.id = id;
        this.lessonName = lessonName;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public Date getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getTypeOfRoom() {
        return typeOfRoom;
    }

}
