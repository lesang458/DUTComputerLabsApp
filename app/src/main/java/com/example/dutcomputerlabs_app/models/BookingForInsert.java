package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

import retrofit2.http.POST;

public class BookingForInsert {
    @Expose
    private ComputerLab Lab;
    @Expose
    private Date BookingDate;
    @Expose
    private int StartAt;
    @Expose
    private int EndAt;
    @Expose
    private String Description;

    public BookingForInsert(ComputerLab lab, Date bookingDate, int startAt, int endAt, String description) {
        Lab = lab;
        BookingDate = bookingDate;
        StartAt = startAt;
        EndAt = endAt;
        Description = description;
    }

    public ComputerLab getLab() {
        return Lab;
    }

    public void setLab(ComputerLab lab) {
        Lab = lab;
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
    }

    public int getStartAt() {
        return StartAt;
    }

    public void setStartAt(int startAt) {
        StartAt = startAt;
    }

    public int getEndAt() {
        return EndAt;
    }

    public void setEndAt(int endAt) {
        EndAt = endAt;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
