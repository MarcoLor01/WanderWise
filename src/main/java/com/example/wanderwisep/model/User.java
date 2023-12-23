package com.example.wanderwisep.model;

import java.util.ArrayList;

public class User {
    private String name;
    private String surname;
    private String email;
    private ArrayList<Ticket> myTicket;

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Ticket> getMyTicket() {
        return myTicket;
    }

    public void setMyTicket(ArrayList<Ticket> myTicket) {
        this.myTicket = myTicket;
    }
}

