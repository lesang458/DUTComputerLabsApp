package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class BookingForInsert {
    @Expose
    private ComputerLabForList lab;
    @Expose
    private Date bookingDate;
    @Expose
    private int startAt;
    @Expose
    private int endAt;
    @Expose
    private String description;

    public BookingForInsert(ComputerLabForList lab, Date bookingDate, int startAt, int endAt, String description) {
        this.lab = lab;
        this.bookingDate = bookingDate;
        this.startAt = startAt;
        this.endAt = endAt;
        this.description = description;
    }

    public ComputerLabForList getLab() {
        return lab;
    }

    public void setLab(ComputerLabForList lab) {
        this.lab = lab;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public int getEndAt() {
        return endAt;
    }

    public void setEndAt(int endAt) {
        this.endAt = endAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
