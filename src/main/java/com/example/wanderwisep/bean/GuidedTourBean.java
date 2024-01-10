package com.example.wanderwisep.bean;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuidedTourBean implements Serializable {
    private String tourName;
    private List<String> listOfAttraction = new ArrayList<>();
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String touristGuideName;
    private String touristGuideSurname;
    private transient Blob photo;
    private String cityName;
    private String idTour;
    private String idSession;

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getIdTour() {
        return idTour;
    }

    public void setIdTour(String idTour) {
        this.idTour = idTour;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public List<String> getListOfAttraction() {
        return listOfAttraction;
    }

    public void setListOfAttraction(List<String> listOfAttraction) {
        this.listOfAttraction = listOfAttraction;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getTouristGuideName() {
        return touristGuideName;
    }

    public void setTouristGuideName(String touristGuideName) {
        this.touristGuideName = touristGuideName;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTouristGuideSurname() {
        return touristGuideSurname;
    }

    public void setTouristGuideSurname(String touristGuideSurname) {
        this.touristGuideSurname = touristGuideSurname;
    }
}
