package com.example.wanderwisep.bean;

import com.example.wanderwisep.exception.InvalidFormatException;

import java.io.Serializable;

public class LoginBean implements Serializable {
    public LoginBean() {
    }

    private String email;
    private String password;

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
        if(email.isEmpty() | password.isEmpty()){
            throw new InvalidFormatException("Please fill all the fields");
        }
    }
}
