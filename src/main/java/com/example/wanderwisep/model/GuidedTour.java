package com.example.wanderwisep.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;

public class GuidedTour {
    private String cityName;
    private List<String> listOfAttraction;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String guidedTourId;
    private TouristGuide touristGuide;
    private List<Ticket> tourTicket;
    private String nameTour;
    private Blob photo;


    public String getGuidedTourId() {
        return guidedTourId;
    }

    public void setGuidedTourId(String guidedTourId) {
        this.guidedTourId = guidedTourId;
    }

    public GuidedTour(String nameTour, Blob photo, LocalDate departureDate, LocalDate returnDate, String idTour) {
        this.cityName = nameTour;
        this.photo = photo;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.guidedTourId = idTour;
    }

    public GuidedTour(String cityName, List<String> listOfAttraction, LocalDate departureDate, LocalDate returnDate, TouristGuide touristGuide, String nameTour, String guidedTourId) {
        this(cityName, null, departureDate, returnDate, guidedTourId);
        this.listOfAttraction = listOfAttraction;
        this.touristGuide = touristGuide;
        this.nameTour = nameTour;
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

    public TouristGuide getTouristGuide() {
        return touristGuide;
    }

    public void setTouristGuide(TouristGuide touristGuide) {
        this.touristGuide = touristGuide;
    }

    public List<Ticket> getTourTicket() {
        return tourTicket;
    }

    public void setTourTicket(List<Ticket> tourTicket) {
        this.tourTicket = tourTicket;
    }

    public Blob getPhoto() {
    return photo;
    }
}
