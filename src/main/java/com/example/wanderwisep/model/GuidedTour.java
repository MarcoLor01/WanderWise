package com.example.wanderwisep.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class GuidedTour {
    private String cityName;
    private ArrayList<String> listOfAttraction;
    private Date departureDate;
    private Date returnDate;
    private Date expirationDate;
    private TouristGuide myTouristGuide;
    private Vector<Ticket> tourTicket;
    private Integer idTicket;

    public GuidedTour(String cityName, ArrayList<String> listOfAttraction, Date departureDate, Date returnDate, Date expirationDate, TouristGuide myTouristGuide, Integer idTicket) {
        this.cityName = cityName;
        this.listOfAttraction = listOfAttraction;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.expirationDate = expirationDate;
        this.myTouristGuide = myTouristGuide;
        this.idTicket = idTicket;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public ArrayList<String> getListOfAttraction() {
        return listOfAttraction;
    }

    public void setListOfAttraction(ArrayList<String> listOfAttraction) {
        this.listOfAttraction = listOfAttraction;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public TouristGuide getMyTouristGuide() {
        return myTouristGuide;
    }

    public void setMyTouristGuide(TouristGuide myTouristGuide) {
        this.myTouristGuide = myTouristGuide;
    }

    public Vector<Ticket> getTourTicket() {
        return tourTicket;
    }

    public void setTourTicket(Vector<Ticket> tourTicket) {
        this.tourTicket = tourTicket;
    }

    public Integer getIdTicket() {
        return idTicket;
    }
}
