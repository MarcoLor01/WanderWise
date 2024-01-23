package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.dao.db_connection.Queries;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.model.TouristGuide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TouristGuideDAO {
    public TouristGuide retrieveTouristGuide(String touristGuideEmail) throws SQLException, TouristGuideNotFoundException {
        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.RETRIEVE_TOURIST_GUIDE,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, touristGuideEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                TouristGuide touristGuide;

                if (!rs.first()) {
                    throw new TouristGuideNotFoundException("No Tourist Guide Profile available");
                }
                rs.first();
                String touristGuideName = rs.getString("nome");
                String touristGuideSurname = rs.getString("cognome");
                touristGuide = new TouristGuide(touristGuideName, touristGuideSurname, touristGuideEmail);
                return touristGuide;
            }
        }
    }
}
