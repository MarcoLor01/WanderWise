package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.LoginControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.exception.InvalidFormatException;
import com.example.wanderwisep.exception.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController extends NavigatorController {
    private final Logger logger = Logger.getLogger(LoginController.class.getName());
    private final LoginControllerApplication loginControllerApp = new LoginControllerApplication();
    @FXML // fx:id="buttonUserLogin"
    private Button buttonUserLogin; // Value injected by FXMLLoader

    @FXML // fx:id="emailUserLogin"
    private TextField emailUserLogin; // Value injected by FXMLLoader

    @FXML // fx:id="passwordUserLogin"
    private PasswordField passwordUserLogin; // Value injected by FXMLLoader
    private static final String LOGIN_ERROR = "Login Error";

    @FXML
    public void doLogin() {
        try {
            LoginBean loginBean = new LoginBean();
            loginBean.setEmail(emailUserLogin.getText());
            loginBean.setPassword(passwordUserLogin.getText());
            loginBean.checkField(loginBean.getEmail(), loginBean.getPassword());
            loginBean = loginControllerApp.login(loginBean);

            if (loginBean.getRole().equals("TouristGuide")) goToPageInit(GUIDECONFIRM, loginBean);

            if (loginBean.getRole().equals("User")) goToPageInit(SEARCHBAR, loginBean);

        } catch (InvalidFormatException | UserNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog(e.getMessage(), LOGIN_ERROR);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog("Please retry later", LOGIN_ERROR);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert buttonUserLogin != null : "fx:id=\"buttonUserLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert emailUserLogin != null : "fx:id=\"emailUserLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordUserLogin != null : "fx:id=\"passwordUserLogin\" was not injected: check your FXML file 'Login.fxml'.";

    }

}