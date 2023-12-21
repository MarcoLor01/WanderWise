package com.example.wanderwisep.bean;

import java.io.Serializable;
import java.time.LocalDate;

public class TicketBean implements Serializable {
    LocalDate prenotationDate;
    String emailSender;
    String guidedTour;
    Integer idTicket;

    public TicketBean() {
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
