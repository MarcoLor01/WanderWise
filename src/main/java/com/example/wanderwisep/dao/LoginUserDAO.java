package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class LoginUserDAO {
    private static final Logger logger = Logger.getLogger(LoginUserDAO.class.getName());


    public User findUser(String email, String password) throws SQLException, UserNotFoundException {
        Statement stmt = null;
        Connection conn = null;
        User user;

        try {
            conn = DBConnection.getConnection();
            System.out.println("QUA CONNESSIONE" + conn);
            System.out.println("QUA");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = Queries.loginUser(stmt, email, password);
            if (!rs.first()) {
                throw new UserNotFoundException("User not found");
            }
            rs.first();
            String name = rs.getString("nome");
            String surname = rs.getString("cognome");
            user = new User(name, surname, email);
            rs.close();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
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
        return user;
    }
}

