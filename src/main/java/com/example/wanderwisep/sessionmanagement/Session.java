package com.example.wanderwisep.sessionmanagement;

import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;

import java.util.List;

public class Session {
    private String name;
    private String surname;
    private String email;
    private GuidedTour actualGuidedTour;
    private List<Ticket> myTicket;
    private final String sessionId;
    private List<String> spokenLanguages;
    private List<GuidedTour> myTour;

    public Session(User user, String sessionId) {
        this.sessionId = sessionId;
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.myTicket = user.getMyTicket();
    }

    public Session(TouristGuide touristGuide, String sessionId) {
        this.sessionId = sessionId;
        this.name = touristGuide.getName();
        this.surname = touristGuide.getSurname();
        this.email = touristGuide.getEmail();
        this.spokenLanguages = touristGuide.getSpokenLanguages();
        this.myTour = touristGuide.getMyTour();
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
}
