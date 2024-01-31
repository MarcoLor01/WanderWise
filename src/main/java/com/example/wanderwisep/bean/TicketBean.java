package com.example.wanderwisep.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class TicketBean implements Serializable {
    private LocalDate prenotationDate;
    private String guidedTour;
    private String idTicket;
    private String stateTicket;
    private String myTouristGuide;
    private String idSession;

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getMyTouristGuide() {
        return myTouristGuide;
    }

    public void setMyTouristGuide(String myTouristGuide) {
        this.myTouristGuide = myTouristGuide;
    }

    public String getStateTicket() {
        return stateTicket;
    }

    public void setStateTicket(String stateTicket) {
        this.stateTicket = stateTicket;
    }

    public LocalDate getPrenotationDate() {
        return prenotationDate;
    }

    public void setPrenotationDate(LocalDate prenotationDate) {
        this.prenotationDate = prenotationDate;
    }

    public String getGuidedTour() {
        return guidedTour;
    }

    public void setGuidedTour(String guidedTour) {
        this.guidedTour = guidedTour;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }
}
