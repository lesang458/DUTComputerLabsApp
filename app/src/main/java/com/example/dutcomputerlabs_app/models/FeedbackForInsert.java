package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

public class FeedbackForInsert {
    @Expose
    private int bookingId;
    @Expose
    private String content;

    public FeedbackForInsert(int bookingId, String content) {
        this.bookingId = bookingId;
        this.content = content;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
