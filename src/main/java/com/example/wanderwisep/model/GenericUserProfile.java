package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.userRole;

import java.util.Vector;

public abstract class  GenericUserProfile {
    private String name;
    private String surname;
    private String email;
    private final userRole role;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public userRole getRole() {
        return role;
    }


    public GenericUserProfile(String name, String surname, String email, userRole type) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = type;
    }
}
