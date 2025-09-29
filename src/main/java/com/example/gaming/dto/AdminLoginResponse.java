package com.example.gaming.dto;

public class AdminLoginResponse {

    private String token;
    private String message;
    private String username;

    public AdminLoginResponse() {}

    public AdminLoginResponse(String token, String message, String username) {
        this.token = token;
        this.message = message;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}