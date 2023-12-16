package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.userRole;

import java.util.Vector;

public class TouristGuide extends GenericUserProfile {

    private Vector<GuidedTour> myTour;

    public TouristGuide(String name, String surname, String email, userRole role) {
        super(name, surname, email, role);
    }

    public Vector<GuidedTour> getMyTour() {
        return myTour;
    }

    public void setMyTour(Vector<GuidedTour> myTour) {
        this.myTour = myTour;
    }
}
