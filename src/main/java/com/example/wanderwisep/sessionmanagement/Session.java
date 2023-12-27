package com.example.wanderwisep.sessionmanagement;

import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private String name;
    private String surname;
    private String email;
    private ArrayList<Ticket> myTicket;
    private String sessionId;
    private List<String> spokenLanguages;
    private ArrayList<GuidedTour> myTour;

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
}
