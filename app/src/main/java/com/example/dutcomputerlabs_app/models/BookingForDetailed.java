package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class BookingForDetailed {
    @Expose
    private int id;
    @Expose
    private ComputerLabForDetailed lab;
    @Expose
    private String bookerName;
    @Expose
    private Date bookingDate;
    @Expose
    private int startAt;
    @Expose
    private int endAt;
    @Expose
    private String status;
    @Expose
    private String description;
    @Expose
    private FeedbackForDetailed feedback;

    public BookingForDetailed(int id, ComputerLabForDetailed lab, String bookerName, Date bookingDate, int startAt, int endAt, String status, String description, FeedbackForDetailed feedback) {
        this.id = id;
        this.lab = lab;
        this.bookerName = bookerName;
        this.bookingDate = bookingDate;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = status;
        this.description = description;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ComputerLabForDetailed getLab() {
        return lab;
    }

    public void setLab(ComputerLabForDetailed lab) {
        this.lab = lab;
    }

    public String getBookerName() {
        return bookerName;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FeedbackForDetailed getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackForDetailed feedback) {
        this.feedback = feedback;
    }
}
