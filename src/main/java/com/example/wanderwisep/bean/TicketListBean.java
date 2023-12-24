package com.example.wanderwisep.bean;

import com.example.wanderwisep.enumeration.stateEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketListBean implements Serializable {
    String email;
    List<Integer> idTicket = new ArrayList<>();
    List<stateEnum> stateEnum = new ArrayList<>();
    List<Date> prenotationDate = new ArrayList<>();
    List<String> tourName = new ArrayList<>();


    public TicketListBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTourName(String tourName, Integer i) {
        (this.tourName).add(i, tourName);
    }

    public void setIdTicket(Integer idTicket, Integer i) {
        (this.idTicket).add(i, idTicket);
    }

    public void setPrenotationDate(Date prenotationDate, Integer i) {
        (this.prenotationDate).add(i, prenotationDate);
    }

    public void setStateEnum(stateEnum stateTicket, Integer i) {
        (this.stateEnum).add(i, stateTicket);
    }

    public List<Integer> getIdTicket() {
        return idTicket;
    }

    public List<com.example.wanderwisep.enumeration.stateEnum> getStateEnum() {
        return stateEnum;
    }

    public List<Date> getPrenotationDate() {
        return prenotationDate;
    }

    public List<String> getTourName() {
        return tourName;
    }
}
