package com.example.wanderwisep.bean;

import com.example.wanderwisep.enumeration.stateEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketListBean implements Serializable {
    private String email;
    private List<String> idTicket = new ArrayList<>();
    private List<stateEnum> stateEnum = new ArrayList<>();
    private List<LocalDate> prenotationDate = new ArrayList<>();
    private List<String> tourName = new ArrayList<>();


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTourName(String tourName, Integer i) {
        (this.tourName).add(i, tourName);
    }

    public void setIdTicket(String idTicket, Integer i) {
        (this.idTicket).add(i, idTicket);
    }

    public void setPrenotationDate(LocalDate prenotationDate, Integer i) {
        (this.prenotationDate).add(i, prenotationDate);
    }

    public void setStateEnum(stateEnum stateTicket, Integer i) {
        (this.stateEnum).add(i, stateTicket);
    }

    public List<String> getIdTicket() {
        return idTicket;
    }

    public List<com.example.wanderwisep.enumeration.stateEnum> getStateEnum() {
        return stateEnum;
    }

    public List<LocalDate> getPrenotationDate() {
        return prenotationDate;
    }

    public List<String> getTourName() {
        return tourName;
    }
}
