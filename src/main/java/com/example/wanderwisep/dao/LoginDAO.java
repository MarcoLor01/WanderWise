package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.enumeration.userRole;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public  class LoginDAO {

    private static final String ROLE = "ruolo";
    private static final String NAME = "nome";
    private static final String SURNAME = "cognome";
    private static final String EMAIL = "email";


    public User findUser(String email, String password) throws SQLException, UserNotFoundException {

        User user;
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE EMAIL = ? AND PASSWORD = ?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if(!rs.first()) throw new UserNotFoundException("User not found");
        user = getUser(rs);
        rs.close();
        stmt.close();
        return user;
    }


    private User getUser(ResultSet rs) throws UserNotFoundException, SQLException {
            User user;
            userRole type;
        if(rs.getString(ROLE).equals("user")) {
           type = userRole.USER;
        }else if(rs.getString(ROLE).equals("tourist guide")) {
           type = userRole.TOURISTGUIDE;
        }else{
           throw new UserNotFoundException("Role Not Found");
        }
        user = new User(
            rs.getString(EMAIL),
            rs.getString(NAME),
            rs.getString(SURNAME),
            type);
            return user;
        }
    }

