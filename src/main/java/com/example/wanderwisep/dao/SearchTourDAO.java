package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.model.GuidedTour;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchTourDAO {

    public List<GuidedTour> findTours(String city, LocalDate departureDate, LocalDate returnDate) throws SQLException, TourException {
        PreparedStatement stmt = null;
        Connection conn = null;
        List<GuidedTour> tours = new ArrayList<>();
        String sql = "SELECT * FROM guidedtour WHERE cityName = ? AND departureDate >= ? AND returnDate <= ?";
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, city);
            stmt.setDate(2, Date.valueOf(departureDate));
            stmt.setDate(3, Date.valueOf(returnDate));
            ResultSet rs = stmt.executeQuery();
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
            rs.close();
        } finally {
            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
        return tours;
    }

    public GuidedTour retrieveTour(String tourName, LocalDate departureDate, LocalDate returnDate) throws TourException, SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        String sql = "SELECT * FROM guidedtour WHERE nametour = ? AND departureDate = ? AND returnDate = ?";
        GuidedTour guidedTour;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, tourName);
            stmt.setDate(2, Date.valueOf(departureDate));
            stmt.setDate(3, Date.valueOf(returnDate));
            ResultSet rs = stmt.executeQuery();
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
            rs.close();
        } finally {
            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
        return guidedTour;
    }
}
