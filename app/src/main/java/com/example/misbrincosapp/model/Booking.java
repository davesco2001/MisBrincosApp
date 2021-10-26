package com.example.misbrincosapp.model;

import java.util.Date;

public class Booking {
    private String bookingId;
    private boolean assitance;
    private Date bookingDate;
    private int sessionId;

    public Booking(String bookingId, boolean assitance, Date bookingDate, int sessionId) {
        this.bookingId = bookingId;
        this.assitance = assitance;
        this.bookingDate = bookingDate;
        this.sessionId = sessionId;
    }
    public String getBookingId() {
        return bookingId;
    }

    public boolean isAssitance() {
        return assitance;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public int getSessionId() {
        return sessionId;
    }
}
