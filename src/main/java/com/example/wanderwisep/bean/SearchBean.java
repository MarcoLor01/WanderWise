package com.example.wanderwisep.bean;

import com.example.wanderwisep.exception.InvalidFormatException;

import java.io.Serializable;
import java.time.LocalDate;

public class SearchBean implements Serializable {
    private String cityName;
    private LocalDate departureDate;
    private LocalDate returnDate;

    public SearchBean() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
    public void checkField(String cityName, LocalDate departureDate, LocalDate returnDate) throws InvalidFormatException{
        if(cityName.isEmpty() | departureDate == null | returnDate == null) throw new InvalidFormatException("Please fill all the fields");
        if(departureDate.isBefore(LocalDate.now())) throw new InvalidFormatException("Departure date cannot be in the past");
        if(returnDate.isBefore(departureDate)) throw new InvalidFormatException("Return date cannot be before the departure date");
    }
}
