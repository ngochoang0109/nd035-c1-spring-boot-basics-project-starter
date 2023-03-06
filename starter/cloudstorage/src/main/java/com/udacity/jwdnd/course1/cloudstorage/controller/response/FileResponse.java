package com.udacity.jwdnd.course1.cloudstorage.controller.response;

public class FileResponse {

    private String filename;
    private int fileid;
    private String contenttype;
    private double filesize;
    private byte[] filedata;

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public double getFilesize() {
        return filesize;
    }

    public void setFilesize(double filesize) {
        this.filesize = filesize;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getFileId() {
        return fileid;
    }

    public void setFileId(int fileId) {
        this.fileid = fileId;
    }
}
