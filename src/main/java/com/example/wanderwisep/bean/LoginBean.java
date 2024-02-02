package com.example.wanderwisep.bean;

import com.example.wanderwisep.exception.InvalidFormatException;

import java.io.Serializable;

public class LoginBean implements Serializable {
    private String email;
    private String password;
    private String idSession;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void checkField(String email, String password) throws InvalidFormatException{
        if (email.isEmpty() || password.isEmpty()) {
            throw new InvalidFormatException("Please fill all the fields");
        }
    }
}
