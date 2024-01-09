package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.stateEnum;

import java.time.LocalDate;
import java.util.logging.Logger;


public class Ticket {
    private String idTicket;
    private stateEnum state;
    private LocalDate prenotationDate;
    private String myGuidedTourId;
    private String user;
    private String myTouristGuide;
    private static final Logger logger = Logger.getLogger(Ticket.class.getName());


    public Ticket(String idTicket, stateEnum state, LocalDate prenotationDate, String myGuidedTourId, String myTouristGuide, String user) {
        this.idTicket = idTicket;
        this.state = state;
        this.prenotationDate = prenotationDate;
        this.myGuidedTourId = myGuidedTourId;
        this.user = user;
        this.myTouristGuide = myTouristGuide;
    }

    public Ticket(String idTicket, stateEnum state, String idTour, String touristGuide, String user) {
        this.idTicket = idTicket;
        this.user = user;
        this.myTouristGuide = touristGuide;
        this.myGuidedTourId = idTour;
        this.state = state;
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
        return myGuidedTourId;
    }

    public void setMyGuidedTour(String myGuidedTourId) {
        this.myGuidedTourId = myGuidedTourId;
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
