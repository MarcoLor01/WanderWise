package com.example.wanderwisep.model;

import java.util.Vector;

public class TouristGuide {
    private String name;
    private String surname;
    private String email;
    private Vector<GuidedTour> myTour;

    public TouristGuide(String name, String surname, String email) {
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

    public Vector<GuidedTour> getMyTour() {
        return myTour;
    }

    public void setMyTour(Vector<GuidedTour> myTour) {
        this.myTour = myTour;
    }
}
