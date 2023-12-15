package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.dao.LoginDAO;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.User;
import com.example.wanderwisep.sessionManagment.SessionManagerSingleton;

import java.sql.SQLException;

public class LoginControllerApplication {

    public LoginBean login(LoginBean loginBean) throws UserNotFoundException, SQLException {
        LoginDAO loginDAO = new LoginDAO();
        User user = loginDAO.findUser(loginBean.getEmail(), loginBean.getPassword());
        String email = user.getEmail();
        String role = user.getRole().getRoleName();
        loginBean.setRole(role);
        SessionManagerSingleton.getInstance().getUserSession(email);
        return loginBean;
    }

}
