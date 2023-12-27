package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.stateEnum;

import java.util.Date;

public class Ticket {
    private Integer idTicket; //
    private stateEnum state; //
    private Date prenotationDate; //
    private String myGuidedTour;
    private String user;


    public Ticket(Integer idTicket, stateEnum state, Date prenotationDate, String myGuidedTour, String user) {
        this.idTicket = idTicket;
        this.state = state;
        this.prenotationDate = prenotationDate;
        this.myGuidedTour = myGuidedTour;
        this.user = user;
    }

    public Integer getIdTicket() {

        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public stateEnum getState() {
        return state;
    }

    public void setState(stateEnum state) {
        this.state = state;
    }

    public Date getPrenotationDate() {
        return prenotationDate;
    }

    public void setPrenotationDate(Date prenotationDate) {
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
}
