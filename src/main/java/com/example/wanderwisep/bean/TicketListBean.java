package com.example.wanderwisep.bean;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketListBean implements Serializable {
    private List<String> idTicket = new ArrayList<>(); //
    private List<String> stateEnum = new ArrayList<>(); //
    private List<LocalDate> prenotationDate = new ArrayList<>(); //
    private List<String> tourId = new ArrayList<>();
    private List<String> touristGuideName = new ArrayList<>();
    private List<String> tourName = new ArrayList<>();
    private List<LocalDate> departureDate = new ArrayList<>();
    private List<LocalDate> returnDate = new ArrayList<>();
    private String idSession;

    public String getIdSession() {
        return idSession;
    }

    public List<String> getTourId() {
        return tourId;
    }

    public void setTourId(String tourId, Integer i) {
        (this.tourId).add(i, tourId);
    }

    public List<String> getTouristGuideName() {
        return touristGuideName;
    }

    public void setTouristGuideName(String touristGuideName, Integer i) {
        (this.touristGuideName).add(i, touristGuideName);
    }

    public List<LocalDate> getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate, Integer i) {
        (this.departureDate).add(i, departureDate);
    }

    public List<LocalDate> getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate, Integer i) {
        (this.returnDate).add(i, returnDate);
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
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

    public void setStateEnum(String stateTicket, Integer i) {
        (this.stateEnum).add(i, stateTicket);
    }

    public List<String> getIdTicket() {
        return idTicket;
    }

    public List<String> getStateEnum() {
        return stateEnum;
    }

    public List<LocalDate> getPrenotationDate() {
        return prenotationDate;
    }

    public List<String> getTourName() {
        return tourName;
    }
}
