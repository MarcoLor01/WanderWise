package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public  class LoginUserDAO {
    private static final Logger logger = Logger.getLogger(LoginUserDAO.class.getName());


    public User findUser(String email, String password) throws SQLException, UserNotFoundException {
        PreparedStatement stmt = null;
        Connection conn = null;
        User user;
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

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
            user = new User(name, surname, email);
            rs.close();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            LoginGuideDAO.closeDAO(stmt, conn, logger);
        }
        return user;
    }
}

