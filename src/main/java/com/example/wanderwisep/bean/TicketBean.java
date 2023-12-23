package com.example.wanderwisep.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class TicketBean implements Serializable {
    LocalDate prenotationDate;
    String emailSender;
    String guidedTour;
    Integer idTicket;
    String stateTicket;
    int result;

    public TicketBean() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
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
