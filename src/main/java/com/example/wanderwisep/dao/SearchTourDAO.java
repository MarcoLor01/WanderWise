package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.dao.db_connection.Queries;
import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.model.GuidedTour;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchTourDAO {

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

    public GuidedTour retrieveTour(String tourName, LocalDate departureDate, LocalDate returnDate) throws TourException, SQLException {
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
                String touristGuideName = rs.getString("touristGuideName");
                String touristGuideSurname = rs.getString("touristGuideSurname");
                List<String> attractionsArray = List.of(listOfAttraction.split(","));
                guidedTour = new GuidedTour(cityName, attractionsArray, departureD, returnD, touristGuideName, touristGuideSurname, photoBlob, nameTour, idTour);

                return guidedTour;
            }
        }
    }
}
