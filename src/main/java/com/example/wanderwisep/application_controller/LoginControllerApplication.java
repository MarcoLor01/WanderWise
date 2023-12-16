package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.dao.LoginDAO;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.exception.UserNotFoundException;
import com.example.wanderwisep.model.GenericUserProfile;
import com.example.wanderwisep.sessionManagment.SessionManagerSingleton;
import java.sql.SQLException;

public class LoginControllerApplication {
    public LoginBean login(LoginBean loginBean) throws UserNotFoundException, SQLException {
        LoginDAO loginDAO = new LoginDAO();
        GenericUserProfile genericUserProfile = loginDAO.findUser(loginBean.getEmail(), loginBean.getPassword());
        String email = genericUserProfile.getEmail();
        String role = genericUserProfile.getRole().getRoleName();
        String name = genericUserProfile.getName();
        String surname = genericUserProfile.getSurname();
        loginBean.setRole(role);
        SessionManagerSingleton.getInstance().getUserSession(email,name);
        return loginBean;
    }
}
