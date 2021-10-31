package com.example.misbrincosapp.model;

import java.util.Date;

public class Booking {
    private int bookingId;
    private String lessonName;
    private Date bookingDate;
    private int sessionId;
    private boolean assistance;
    public Booking(int bookingId, String lessonName, Date bookingDate, int sessionId) {
        this.bookingId = bookingId;
        this.lessonName = lessonName;
        this.bookingDate = bookingDate;
        this.sessionId = sessionId;
    }
    public Booking(int bookingId, String lessonName, Date bookingDate) {
        this.bookingId = bookingId;
        this.lessonName = lessonName;
        this.bookingDate = bookingDate;
    }
    public int getBookingId() {
        return bookingId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public int getSessionId() {
        return sessionId;
    }
}
