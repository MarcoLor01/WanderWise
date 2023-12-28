package com.example.wanderwisep.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class TicketBean implements Serializable {
    private LocalDate prenotationDate;
    private String guidedTour;
    private Integer idTicket;
    private String stateTicket;
    private String myTouristGuide;

    public TicketBean() {
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

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }
}
