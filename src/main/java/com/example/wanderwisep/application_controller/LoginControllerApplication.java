package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.dao.LoginGuideDAO;
import com.example.wanderwisep.dao.LoginUserDAO;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;
import com.example.wanderwisep.sessionmanagement.SessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class LoginControllerApplication {

    public static String idSession;

    public void loginUser(LoginBean loginBean) throws UserNotFoundException, SQLException, IOException {
        LoginUserDAO loginUserDAO = new LoginUserDAO();
        User user = loginUserDAO.findUser(loginBean.getEmail(), loginBean.getPassword());
        SessionManager.getInstance().addSession(user);

    }

    public void loginGuide(LoginBean loginBean) throws UserNotFoundException, SQLException, IOException {
        LoginGuideDAO loginGuideDAO = new LoginGuideDAO();
        TouristGuide touristGuide = loginGuideDAO.findGuide(loginBean.getEmail(), loginBean.getPassword());
        SessionManager.getInstance().addSession(touristGuide);
    }

    public void logout() {
        SessionManager.getInstance().removeSession(idSession);
    }
}
