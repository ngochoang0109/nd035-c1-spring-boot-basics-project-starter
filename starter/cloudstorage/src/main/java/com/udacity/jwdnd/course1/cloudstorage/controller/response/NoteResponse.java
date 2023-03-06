package com.udacity.jwdnd.course1.cloudstorage.controller.response;

public class NoteResponse {
    private int noteid;
    private String notetitle;
    private String notedescription;

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }
}
