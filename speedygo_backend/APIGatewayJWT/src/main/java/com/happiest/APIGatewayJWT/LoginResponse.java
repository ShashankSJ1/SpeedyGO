package com.happiest.APIGatewayJWT;

import com.happiest.APIGatewayJWT.model.Users;

public class LoginResponse {
    private String token;
    private String email;
    private String username;
    private String role;
    private long phoneNumber;

    public LoginResponse(String token, String email, String username, String role, long phoneNumber) {
        this.token = token;
        this.email = email;
        this.username = username;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    public LoginResponse(String token, Users user) {
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
