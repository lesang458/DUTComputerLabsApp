package com.example.dutcomputerlabs_app.models;

import com.google.gson.annotations.Expose;

import java.util.Date;


public class FeedbackForDetailed {
    @Expose
    private int id;
    @Expose
    private UserForList user;
    @Expose
    private String content;
    @Expose
    private Date feedbackDate;

    public FeedbackForDetailed(int id, UserForList user, String content, Date feedbackDate) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.feedbackDate = feedbackDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserForList getUser() {
        return user;
    }

    public void setUser(UserForList user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
}
