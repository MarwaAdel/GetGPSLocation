package com.example.getgpslocation.models;

/**
 * Created by Eman on 11/28/2016.
 */

public class User {
    int id;
    String username;
    String token;
    String token_expire;
    String role;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getToken_expire() {
        return token_expire;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setToken_expire(String token_expire) {
        this.token_expire = token_expire;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
