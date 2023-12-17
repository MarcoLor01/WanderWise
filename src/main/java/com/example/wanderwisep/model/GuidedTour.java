package com.example.wanderwisep.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class GuidedTour {
    private String cityName;
    private ArrayList<String> listOfAttraction;
    private Date departureDate;
    private Date returnDate;
    private TouristGuide myTouristGuide;
    private Vector<Ticket> tourTicket;
    private String nameTour;
    private Blob photo;

    public GuidedTour(String nameTour, Blob photo) {
        this.cityName = nameTour;
        this.photo = photo;
    }

    public GuidedTour(String cityName, ArrayList<String> listOfAttraction, Date departureDate, Date returnDate, TouristGuide myTouristGuide) {
        this.cityName = cityName;
        this.listOfAttraction = listOfAttraction;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.myTouristGuide = myTouristGuide;
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

    public Blob getPhoto() {
    return photo;
    }
}
