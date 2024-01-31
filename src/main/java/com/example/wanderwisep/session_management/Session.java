package com.example.wanderwisep.session_management;

import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;

import java.util.List;
import java.util.UUID;

public class Session {
    private String name;
    private String surname;
    private String email;
    private GuidedTour actualGuidedTour;
    private final String sessionId;
    private List<GuidedTour> guidedTourList;

    public Session(User user) {
        this.sessionId = generateSessionId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
    }

    public Session(TouristGuide touristGuide) {
        this.sessionId = generateSessionId();
        this.name = touristGuide.getName();
        this.surname = touristGuide.getSurname();
        this.email = touristGuide.getEmail();
    }

    public List<GuidedTour> getGuidedTourList() {
        return guidedTourList;
    }

    public void setGuidedTourList(List<GuidedTour> guidedTourList) {
        this.guidedTourList = guidedTourList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GuidedTour getActualGuidedTour() {
        return actualGuidedTour;
    }

    public void setActualGuidedTour(GuidedTour actualGuidedTour) {
        this.actualGuidedTour = actualGuidedTour;
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

    public String getSessionId() {
        return sessionId;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

}
