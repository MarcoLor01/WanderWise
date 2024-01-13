package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.dao.db_connection.Queries;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public  class LoginUserDAO {

    public User findUser(String email, String password) throws SQLException, UserNotFoundException {
        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.findUser,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.first()) {
                    throw new UserNotFoundException("User not found");
                }
                rs.first();
                String name = rs.getString("nome");
                String surname = rs.getString("cognome");
                return new User(name, surname, email);
            }
        }
    }
}