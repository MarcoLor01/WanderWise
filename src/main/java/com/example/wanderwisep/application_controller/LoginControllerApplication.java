package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.dao.LoginGuideDAO;
import com.example.wanderwisep.dao.LoginUserDAO;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;
import com.example.wanderwisep.sessionManagement.SessionManager;

import java.sql.SQLException;
public class LoginControllerApplication {

    private String idSession;

    public LoginBean loginUser(LoginBean loginBean) throws UserNotFoundException, SQLException {
        LoginUserDAO loginUserDAO = new LoginUserDAO();
        User user = loginUserDAO.findUser(loginBean.getEmail(), loginBean.getPassword());
        idSession = SessionManager.getInstance().addSession(user);
        loginBean.setIdSession(idSession);
        return loginBean;
    }

    public LoginBean loginGuide(LoginBean loginBean) throws UserNotFoundException, SQLException {
        LoginGuideDAO loginGuideDAO = new LoginGuideDAO();
        TouristGuide touristGuide = loginGuideDAO.findGuide(loginBean.getEmail(), loginBean.getPassword());
        idSession = SessionManager.getInstance().addSession(touristGuide);
        loginBean.setIdSession(idSession);
        return loginBean;
    }

    public void logout(LoginBean loginBean) {
        SessionManager.getInstance().removeSession(loginBean.getIdSession());
    }

}