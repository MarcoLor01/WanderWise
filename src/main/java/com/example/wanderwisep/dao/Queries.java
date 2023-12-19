package com.example.wanderwisep.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {
    public static ResultSet loginUser(Statement stmt, String email, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
        return stmt.executeQuery(sql);
    }

    public static ResultSet loginGuide(Statement stmt, String email, String password) throws SQLException {
        String sql = "SELECT * FROM touristguide WHERE email = '" + email + "' AND password = '" + password + "'";
        return stmt.executeQuery(sql);
    }

    public static ResultSet findTour(Statement stmt, String cityName, Date departureDate, Date returnDate) throws SQLException {
        String sql = "SELECT * FROM guidedtour WHERE cityName = '" + cityName + "' AND departureDate >= '" + departureDate + "' AND returnDate <= '" + returnDate + "'";
        return stmt.executeQuery(sql);
    }
}
