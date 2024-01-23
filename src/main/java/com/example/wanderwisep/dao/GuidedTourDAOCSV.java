package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.model.GuidedTour;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GuidedTourDAOCSV extends GuidedTourDAO {
    @Override
    public List<GuidedTour> findTours(String city, LocalDate departureDate, LocalDate returnDate) throws SQLException, TourException {
        return null;
    }

    @Override
    public GuidedTour retrieveTour(String tourName, LocalDate departureDate, LocalDate returnDate) throws TourException, SQLException {
        return null;
    }

    @Override
    public GuidedTour retrieveTourFromId(String idTour) throws TourException, SQLException {
        return null;
    }
}
