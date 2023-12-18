package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.dao.LoginGuideDAO;
import com.example.wanderwisep.dao.LoginUserDAO;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;
import com.example.wanderwisep.sessionManagment.SessionManagerSingleton;
import java.sql.SQLException;
import java.util.List;

public class LoginControllerApplication {
    public void loginUser(LoginBean loginBean) throws UserNotFoundException, SQLException {
        LoginUserDAO loginUserDAO = new LoginUserDAO();
        User user = loginUserDAO.findUser(loginBean.getEmail(), loginBean.getPassword());
        String email = user.getEmail();
        String name = user.getName();
        String surname = user.getSurname();
        SessionManagerSingleton.getInstance().getUserSession(email, name);
        //Capire come si gestisce la sessione
    }
    public void loginGuide(LoginBean loginBean) throws UserNotFoundException, SQLException {
        LoginGuideDAO loginGuideDAO = new LoginGuideDAO();
        TouristGuide touristGuide = loginGuideDAO.findGuide(loginBean.getEmail(), loginBean.getPassword());
        String email = touristGuide.getEmail();
        String name = touristGuide.getName();
        String surname = touristGuide.getSurname();
        List<String> spokenLanguages = touristGuide.getSpokenLanguages();
}}
