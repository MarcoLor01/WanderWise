package com.example.wanderwisep.model;

import java.util.List;
import java.util.Vector;

public class TouristGuide {
    private String name;
    private String surname;
    private String email;
    private List<String> spokenLanguages;
    private Vector<GuidedTour> myTour;

    public TouristGuide(String name, String surname, String email, List<String> spokenLanguages) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.spokenLanguages = spokenLanguages;
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

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<String> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public Vector<GuidedTour> getMyTour() {
        return myTour;
    }

    public void setMyTour(Vector<GuidedTour> myTour) {
        this.myTour = myTour;
    }
}
