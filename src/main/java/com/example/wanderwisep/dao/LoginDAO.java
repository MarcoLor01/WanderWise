package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.enumeration.userRole;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.GenericUserProfile;
import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class LoginDAO {

    private static final String ROLE = "ruolo";
    private static final String NAME = "nome";
    private static final String SURNAME = "cognome";
    private static final String EMAIL = "email";
    private static final Logger logger = Logger.getLogger(LoginDAO.class.getName());


    public GenericUserProfile findUser(String email, String password) throws SQLException, UserNotFoundException {
        PreparedStatement stmt = null;
        Connection conn = null;
        GenericUserProfile user;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM user WHERE EMAIL = ? AND PASSWORD = ?",
                     ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
                if (!rs.first()) {
                    throw new UserNotFoundException("User not found");
                }
                rs.first();
                user =  getUser(rs);

            rs.close();
            }finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                logger.log(Level.INFO, e.getMessage());
            }
        }
        return user;
    }



    private GenericUserProfile getUser(ResultSet rs) throws UserNotFoundException, SQLException {
        GenericUserProfile userProfile;
        userRole type;
        if(rs.getString(ROLE).equals("user")) {
            type = userRole.USER;
            userProfile = new User(rs.getString(NAME),
                    rs.getString(SURNAME),
                    rs.getString(EMAIL),type);
        }else if(rs.getString(ROLE).equals("tourist guide")) {
           type = userRole.TOURISTGUIDE;
           userProfile = new TouristGuide(rs.getString(NAME),
                   rs.getString(SURNAME),
                   rs.getString(EMAIL),type);
        }else{
           throw new UserNotFoundException("Role Not Found");
        }
            return userProfile;
        }
    }

