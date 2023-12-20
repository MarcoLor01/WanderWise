package com.example.wanderwisep.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class GuidedTour {
    private String cityName;
    private List<String> listOfAttraction;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String myTouristGuide;
    private Vector<Ticket> tourTicket;
    private String nameTour;
    private Blob photo;

    public GuidedTour(String nameTour, Blob photo, LocalDate departureDate, LocalDate returnDate) {
        this.cityName = nameTour;
        this.photo = photo;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
    }

    public GuidedTour(String cityName, List<String> listOfAttraction, LocalDate departureDate, LocalDate returnDate, String myTouristGuide, Blob photo, String tourName) {
        this.cityName = cityName;
        this.listOfAttraction = listOfAttraction;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.myTouristGuide = myTouristGuide;
        this.photo = photo;
        this.nameTour = tourName;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
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

    public String getMyTouristGuide() {
        return myTouristGuide;
    }

    public void setMyTouristGuide(String myTouristGuide) {
        this.myTouristGuide = myTouristGuide;
    }

    public Vector<Ticket> getTourTicket() {
        return tourTicket;
    }

    public void setTourTicket(Vector<Ticket> tourTicket) {
        this.tourTicket = tourTicket;
    }

    public Blob getPhoto() {
    return photo;
    }
}
