package com.example.wanderwisep.bean;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TourListBean implements Serializable {

    private List<String> tourName = new ArrayList<>();
    private List<LocalDate> departureDate = new ArrayList<>();
    private List<LocalDate> returnDate = new ArrayList<>();
    private transient List<Blob> photo = new ArrayList<>();
    private String idSession;

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public List<String> getTourName() {
        return tourName;
    }

    public void setTourName(String tourName, Integer i) {
        (this.tourName).add(i, tourName);
    }

    public List<LocalDate> getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate, Integer i) {
        this.departureDate.add(i, departureDate);
    }

    public void setPhoto(Blob photo, Integer i) {
            (this.photo).add(i, photo);
    }

    public List<Blob> getPhoto() {
        return photo;
    }

    public List<LocalDate> getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate, Integer i) {
        this.returnDate.add(i, returnDate);
    }
}
