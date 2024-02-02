package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.dao.LoginPersonDAO;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.Person;
import com.example.wanderwisep.session_management.SessionManager;

import java.sql.SQLException;

public class LoginControllerApplication {

    private String idSession;

    public LoginBean login(LoginBean loginBean) throws UserNotFoundException, SQLException {
        LoginPersonDAO loginPersonDAO = new LoginPersonDAO();
        Person person = loginPersonDAO.findUser(loginBean.getEmail(), loginBean.getPassword());
        idSession = SessionManager.getInstance().addSession(person);
        loginBean.setIdSession(idSession);
        loginBean.setRole(person.getRole().getRoleName());
        return loginBean;
    }

    public void logout(LoginBean loginBean) {
        SessionManager.getInstance().removeSession(loginBean.getIdSession());
    }

}