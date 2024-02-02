package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.roleEnum;

import java.util.List;

public class TouristGuide extends Person {

    private List<String> spokenLanguages;
    private List<GuidedTour> myTour;

    public TouristGuide(String email, String name, String surname, roleEnum role) {
        super(email, name, surname, role);
    }

    public TouristGuide(String email, String name, String surname, roleEnum role, List<String> spokenLanguages) {
        super(email, name, surname, role);
        this.spokenLanguages = spokenLanguages;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<String> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public List<GuidedTour> getMyTour() {
        return myTour;
    }

    public void setMyTour(List<GuidedTour> myTour) {
        this.myTour = myTour;
    }

}
