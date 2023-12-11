package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.stateEnum;

import java.util.Date;

public class Ticket {
    private Integer idTicket;
    private stateEnum state;
    private Date prenotationDate;
    private GuidedTour myGuidedTour;
    private User user;



    public Ticket(Integer idTicket, stateEnum state, Date prenotationDate, GuidedTour myGuidedTour, User user) {
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

    public GuidedTour getMyGuidedTour() {
        return myGuidedTour;
    }

    public void setMyGuidedTour(GuidedTour myGuidedTour) {
        this.myGuidedTour = myGuidedTour;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
