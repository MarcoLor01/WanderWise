package com.example.wanderwisep.dao;

import java.sql.*;

public class Queries {

    public static ResultSet loginUser(Statement stmt, String email, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        // Utilizzo di un PreparedStatement
        try (PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            return preparedStatement.executeQuery();
        }
    }

    public static ResultSet loginGuide(Statement stmt, String email, String password) throws SQLException {
        String sql = "SELECT * FROM touristguide WHERE email = ? AND password = ?";

        // Utilizzo di un PreparedStatement
        try (PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            return preparedStatement.executeQuery();
        }
    }

    public static ResultSet findTour(Statement stmt, String cityName, Date departureDate, Date returnDate) throws SQLException {
        String sql = "SELECT * FROM guidedtour WHERE cityName = ? AND departureDate >= ? AND returnDate <= ?";

        // Utilizzo di un PreparedStatement
        try (PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, cityName);
            preparedStatement.setDate(2, departureDate);
            preparedStatement.setDate(3, returnDate);

            return preparedStatement.executeQuery();
        }
    }
}


