package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.roleEnum;

public class Person {

    protected String email;

    protected String name;

    protected String surname;

    protected final roleEnum role;

    public Person(String email, String name, String surname, roleEnum role) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public roleEnum getRole() {
        return role;
    }
}
