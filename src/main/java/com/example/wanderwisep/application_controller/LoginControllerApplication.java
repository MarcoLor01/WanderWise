package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.dao.LoginDAO;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.model.User;
import com.example.wanderwisep.sessionManagment.SessionManagerSingleton;

import java.io.IOException;
import java.sql.SQLException;

public class LoginControllerApplication {

    public LoginBean login(LoginBean loginBean) throws SQLException, IOException {
        LoginDAO loginDAO = new LoginDAO();
        User user = loginDAO.findUser(loginBean.getEmail(), loginBean.getPassword()); //ERRORE, E' NULLO L'USER
        String email = user.getEmail();
        String role = user.getRole().getRoleName();
        loginBean.setRole(role);
        SessionManagerSingleton.getInstance().getUserSession(email);
        return loginBean;
    }

}
