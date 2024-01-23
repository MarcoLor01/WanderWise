package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.TouristGuide;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public abstract class GuidedTourDAO {
    abstract List<GuidedTour> findTours(String city, LocalDate departureDate, LocalDate returnDate) throws SQLException, TourException;

    abstract GuidedTour retrieveTour(String tourName, LocalDate departureDate, LocalDate returnDate) throws TourException, SQLException, TouristGuideNotFoundException;

    abstract GuidedTour retrieveTourFromId(String idTour) throws TourException, SQLException, TouristGuideNotFoundException;

    protected TouristGuide retrieveTouristGuide(String touristGuideEmail) throws SQLException, TouristGuideNotFoundException {
        TouristGuideDAO touristGuideDAO = new TouristGuideDAO();
        return touristGuideDAO.retrieveTouristGuide(touristGuideEmail);
    }

}