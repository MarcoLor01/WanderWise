package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.dao.db_connection.Queries;
import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.TouristGuide;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuidedTourDAOJDBC extends GuidedTourDAO {

    public List<GuidedTour> findTours(String city, LocalDate departureDate, LocalDate returnDate) throws SQLException, TourException {
        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.FIND_TOURS,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, city);
            stmt.setDate(2, Date.valueOf(departureDate));
            stmt.setDate(3, Date.valueOf(returnDate));

            try (ResultSet rs = stmt.executeQuery()) {
                List<GuidedTour> tours = new ArrayList<>();

                if (!rs.first()) {
                    throw new TourException("No tours available");
                }

                rs.first();
                do {
                    String tourName = rs.getString("nametour");
                    Blob photoBlob = rs.getBlob("photo");
                    LocalDate departureD = rs.getDate("departureDate").toLocalDate();
                    LocalDate returnD = rs.getDate("returnDate").toLocalDate();
                    String idTour = rs.getString("idGuidedTour");
                    GuidedTour a = new GuidedTour(tourName, photoBlob, departureD, returnD, idTour);
                    tours.add(a);
                } while (rs.next());

                return tours;
            }
        }
    }

    public GuidedTour retrieveTour(String tourName, LocalDate departureDate, LocalDate returnDate) throws TourException, SQLException, TouristGuideNotFoundException {
        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.RETRIEVE_TOUR,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, tourName);
            stmt.setDate(2, Date.valueOf(departureDate));
            stmt.setDate(3, Date.valueOf(returnDate));

            try (ResultSet rs = stmt.executeQuery()) {
                GuidedTour guidedTour;

                if (!rs.first()) {
                    throw new TourException("Error in tour retrieving");
                }

                rs.first();
                String idTour = rs.getString("idGuidedTour");
                String nameTour = rs.getString("nametour");
                Blob photoBlob = rs.getBlob("photo");
                LocalDate departureD = rs.getDate("departureDate").toLocalDate();
                LocalDate returnD = rs.getDate("returnDate").toLocalDate();
                String cityName = rs.getString("cityName");
                String listOfAttraction = rs.getString("listOfAttraction");
                String touristGuideEmail = rs.getString("touristGuide");
                List<String> attractionsArray = List.of(listOfAttraction.split(","));
                TouristGuide touristGuide = retrieveTouristGuide(touristGuideEmail);
                guidedTour = new GuidedTour(cityName, attractionsArray, departureD, returnD, touristGuide, photoBlob, nameTour, idTour);
                return guidedTour;
            }
        }
    }

    public GuidedTour retrieveTourFromId(String id) throws SQLException, TourException, TouristGuideNotFoundException {
        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.RETRIEVE_TOUR_FROM_ID,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                GuidedTour guidedTour;

                if (!rs.first()) {
                    throw new TourException("Error in tour retrieving");
                }

                rs.first();
                String idTour = rs.getString("idGuidedTour");
                String nameTour = rs.getString("nametour");
                Blob photoBlob = rs.getBlob("photo");
                LocalDate departureD = rs.getDate("departureDate").toLocalDate();
                LocalDate returnD = rs.getDate("returnDate").toLocalDate();
                String cityName = rs.getString("cityName");
                String listOfAttraction = rs.getString("listOfAttraction");
                String touristGuideEmail = rs.getString("touristGuide");
                List<String> attractionsArray = List.of(listOfAttraction.split(","));
                TouristGuide touristGuide = retrieveTouristGuide(touristGuideEmail);
                guidedTour = new GuidedTour(cityName, attractionsArray, departureD, returnD, touristGuide, photoBlob, nameTour, idTour);

                return guidedTour;
            }
        }
    }
}
