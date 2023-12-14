package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.dao.LoginDAO;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.model.UserLogin;
import com.example.wanderwisep.sessionManagment.SessionManagerSingleton;

public class LoginControllerApplication {

    public Boolean login(LoginBean loginBean) {
        LoginDAO loginDAO = new LoginDAO();
        UserLogin user = loginDAO.login(loginBean.getEmail(), loginBean.getPassword());
        String username = user.getUsername();
        String role = user.getRole();
        SessionManagerSingleton.getInstance().getUserSession(user.getUsername());
        Boolean result = false;
        return result;
    }

}
