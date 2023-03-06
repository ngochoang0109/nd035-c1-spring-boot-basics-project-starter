package com.udacity.jwdnd.course1.cloudstorage.controller.request;

public class NoteRequest {
    private int noteid;
    private String username;
    private String title;
    private String description;

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
