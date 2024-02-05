package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.dao.db_connection.Queries;
import com.example.wanderwisep.exception.RequestNotFoundException;
import com.example.wanderwisep.model.TouristGuideRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TouristGuideRequestDAO {
    private final Logger logger = Logger.getLogger(TouristGuideRequestDAO.class.getName());

    public void createRequest(String userEmail, String touristGuideEmail, String guidedTourId) throws SQLException, RequestNotFoundException {
        Connection conn = DBConnection.getConnection();
        try (

                PreparedStatement stmt = conn.prepareStatement(
                        Queries.CREATE_REQUEST,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, guidedTourId);
            stmt.setString(2, userEmail);
            stmt.setString(3, touristGuideEmail);

            int result = stmt.executeUpdate();

            if (result == 1) {
                logger.log(Level.INFO, "Request inserted");
            } else {
                throw new RequestNotFoundException("Error creating request");
            }
        }
    }

    public List<TouristGuideRequest> retrieveRequestsForGuide(String touristGuideEmail) throws SQLException, RequestNotFoundException {
        List<TouristGuideRequest> touristGuideRequests = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (

                PreparedStatement stmt = conn.prepareStatement(
                        Queries.RETRIEVE_REQUESTS_FOR_TOURIST_GUIDE,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, touristGuideEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.first()) {
                    throw new RequestNotFoundException("No request available");
                }
                rs.first();
                do {
                    String userEmail = rs.getString("userEmail");
                    String idTour = rs.getString("tourId");
                    TouristGuideRequest request = new TouristGuideRequest(userEmail, touristGuideEmail, idTour);
                    touristGuideRequests.add(request);
                } while (rs.next());
            }
        }
        return touristGuideRequests;
    }

    public void deleteRequest(String userEmail, String touristGuide, String tourId) throws SQLException, RequestNotFoundException {
        Connection conn = DBConnection.getConnection();
        try (

                PreparedStatement stmt = conn.prepareStatement(
                        Queries.DELETE_REQUEST,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, userEmail);
            stmt.setString(2, touristGuide);
            stmt.setString(3, tourId);
            int result = stmt.executeUpdate();

            if (result == 1) {
                logger.log(Level.INFO, "Request inserted");
            } else {
                throw new RequestNotFoundException("Error deleting request");
            }
        }
    }
}