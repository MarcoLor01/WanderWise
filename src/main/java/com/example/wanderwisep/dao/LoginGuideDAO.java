package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.TouristGuide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginGuideDAO {
    private static final Logger logger = Logger.getLogger(LoginGuideDAO.class.getName());

    public TouristGuide findGuide(String email, String password) throws UserNotFoundException, SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        TouristGuide touristGuide;
        String sql = "SELECT * FROM touristguide WHERE email = ? AND password = ?";

        try {
            conn = DBConnection.getConnection();

            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (!rs.first()) {
                throw new UserNotFoundException("User not found");
            }
            rs.first();
            String name = rs.getString("nome");
            String surname = rs.getString("cognome");
            String lingueparlateString = rs.getString("lingueparlate");
            List<String> lingueParlate = Arrays.asList(lingueparlateString.split(","));
            touristGuide = new TouristGuide(name, surname, email, lingueParlate);
            rs.close();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            closeDAO(stmt, conn, logger);
        }
        return touristGuide;
    }

    static void closeDAO(PreparedStatement stmt, Connection conn, Logger logger) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            logger.log(Level.INFO, e.getMessage());
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            logger.log(Level.INFO, e.getMessage());
        }
    }
}
