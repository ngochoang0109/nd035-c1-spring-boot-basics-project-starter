package com.udacity.jwdnd.course1.cloudstorage.controller.response;

public class CredentialResponse {
    private int credentialid;
    private String url;
    private String username;
    private String password;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(int credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
