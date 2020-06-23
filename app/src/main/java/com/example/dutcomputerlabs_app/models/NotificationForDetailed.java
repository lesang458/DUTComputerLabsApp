package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class NotificationForDetailed {
    @Expose
    private int id;
    @Expose
    private BookingForDetailed booking;
    @Expose
    private String content;
    @Expose
    private Date noticeDate;

    public NotificationForDetailed(int id, BookingForDetailed booking, String content, Date noticeDate) {
        this.id = id;
        this.booking = booking;
        this.content = content;
        this.noticeDate = noticeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookingForDetailed getBooking() {
        return booking;
    }

    public void setBooking(BookingForDetailed booking) {
        this.booking = booking;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }
}
