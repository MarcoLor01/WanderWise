package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.enumeration.userRole;
import com.example.wanderwisep.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class LoginDAO {

    private static final String ROLE = "ruolo";
    private static final String NAME = "nome";
    private static final String SURNAME = "cognome";
    private static final String EMAIL = "email";


    public User findUser(String email, String password) {
        User user = null;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE EMAIL = ? AND PASSWORD = ?")
        ) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = getUser(rs);
                } else {
                    System.out.println("User not found in the database");
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(DBConnection.class.getName());
            logger.log(Level.INFO, e.getMessage());
        }

        return user;
    }


    private User getUser(ResultSet rs) throws SQLException {
            User user;
            userRole type;

            if (rs.getString(ROLE).equals("user")) {
                type = userRole.USER;
        } else if (rs.getString(ROLE).equals("tourist guide")) {
                type = userRole.TOURISTGUIDE;
            } else {
                throw new SQLException("Wrong Role");
        }

            user = new User(
                    rs.getString(EMAIL),
                rs.getString(NAME),
                    rs.getString(SURNAME),
                    type);


            return user;
        }
    }

