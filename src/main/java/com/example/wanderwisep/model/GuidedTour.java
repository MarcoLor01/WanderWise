package com.example.wanderwisep.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuidedTour {
    private String cityName;
    private List<String> listOfAttraction;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String guidedTourId;
    private String myTouristGuideName;
    private String myTouristGuideSurname;
    private ArrayList<Ticket> tourTicket;
    private String nameTour;
    private Blob photo;


    public String getGuidedTourId() {
        return guidedTourId;
    }

    public void setGuidedTourId(String guidedTourId) {
        this.guidedTourId = guidedTourId;
    }

    public GuidedTour(String guidedTourId) {
        this.guidedTourId = guidedTourId;
    }

    public GuidedTour(String nameTour, Blob photo, LocalDate departureDate, LocalDate returnDate, String idTour) {
        this.cityName = nameTour;
        this.photo = photo;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.guidedTourId = idTour;
    }

    public GuidedTour(String cityName, List<String> listOfAttraction, LocalDate departureDate, LocalDate returnDate, String myTouristGuideName, String myTouristGuideSurname, Blob photo, String tourName, String idTour) {
        this.cityName = cityName;
        this.listOfAttraction = listOfAttraction;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.myTouristGuideName = myTouristGuideName;
        this.myTouristGuideSurname = myTouristGuideSurname;
        this.photo = photo;
        this.nameTour = tourName;
        this.guidedTourId = idTour;
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

    public String getMyTouristGuideName() {
        return myTouristGuideName;
    }

    public void setMyTouristGuideName(String myTouristGuideName) {
        this.myTouristGuideName = myTouristGuideName;
    }

    public String getMyTouristGuideSurname() {
        return myTouristGuideSurname;
    }

    public void setMyTouristGuideSurname(String myTouristGuideSurname) {
        this.myTouristGuideSurname = myTouristGuideSurname;
    }

    public ArrayList<Ticket> getTourTicket() {
        return tourTicket;
    }

    public void setTourTicket(ArrayList<Ticket> tourTicket) {
        this.tourTicket = tourTicket;
    }

    public Blob getPhoto() {
    return photo;
    }
}
