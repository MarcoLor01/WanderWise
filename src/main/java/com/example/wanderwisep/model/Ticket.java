package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.stateEnum;

import java.time.LocalDate;
import java.util.logging.Logger;


public class Ticket {
    private String idTicket;
    private stateEnum state;
    private LocalDate prenotationDate;
    private String myGuidedTour;
    private String user;
    private String myTouristGuide;
    private static final Logger logger = Logger.getLogger(Ticket.class.getName());


    public Ticket(String idTicket, stateEnum state, LocalDate prenotationDate, String myGuidedTour, String myTouristGuide, String user) {
        this.idTicket = idTicket;
        this.state = state;
        this.prenotationDate = prenotationDate;
        this.myGuidedTour = myGuidedTour;
        this.user = user;
        this.myTouristGuide = myTouristGuide;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public stateEnum getState() {
        return state;
    }

    public void setState(stateEnum state) {
        this.state = state;
    }

    public LocalDate getPrenotationDate() {
        return prenotationDate;
    }

    public void setPrenotationDate(LocalDate prenotationDate) {
        this.prenotationDate = prenotationDate;
    }

    public String getMyGuidedTour() {
        return myGuidedTour;
    }

    public void setMyGuidedTour(String myGuidedTour) {
        this.myGuidedTour = myGuidedTour;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getMyTouristGuide() {
        return myTouristGuide;
    }

    public void setMyTouristGuide(String myTouristGuide) {
        this.myTouristGuide = myTouristGuide;
    }

}
