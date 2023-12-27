package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.LoginControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.exception.InvalidFormatException;
import com.example.wanderwisep.exception.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController extends NavigatorController{
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    LoginControllerApplication loginControllerApp = new LoginControllerApplication();
    @FXML // fx:id="buttonUserLogin"
    private Button buttonUserLogin; // Value injected by FXMLLoader

    @FXML // fx:id="emailGuideLogin"
    private TextField emailGuideLogin; // Value injected by FXMLLoader

    @FXML // fx:id="emailUserLogin"
    private TextField emailUserLogin; // Value injected by FXMLLoader

    @FXML // fx:id="guideButtonLogin"
    private Button guideButtonLogin; // Value injected by FXMLLoader

    @FXML // fx:id="passwordGuideLogin"
    private TextField passwordGuideLogin; // Value injected by FXMLLoader

    @FXML // fx:id="passwordUserLogin"
    private PasswordField passwordUserLogin; // Value injected by FXMLLoader

    @FXML
    void doLoginGuide() {
        try {
            LoginBean loginBean = new LoginBean();
            loginBean.setEmail(emailGuideLogin.getText());
            loginBean.setPassword(passwordGuideLogin.getText());
            loginBean.checkField(loginBean.getEmail(), loginBean.getPassword());
            loginControllerApp.loginGuide(loginBean);
            goToPage(GUIDECONFIRM);
        } catch (InvalidFormatException | UserNotFoundException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog(e.getMessage(), "Login Error");
        } catch (SQLException | IOException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog("Please retry later", "Login Error");
        }
    }

    @FXML
    void doLoginUser() {
        try {
            LoginBean loginBean = new LoginBean();
            loginBean.setEmail(emailUserLogin.getText());
            loginBean.setPassword(passwordUserLogin.getText());
            loginBean.checkField(loginBean.getEmail(), loginBean.getPassword());
            loginControllerApp.loginUser(loginBean);
            goToPage(SEARCHBAR);
        } catch (InvalidFormatException | UserNotFoundException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog(e.getMessage(), "Login Error");
        } catch (SQLException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog("Database Error", "Login Error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert buttonUserLogin != null : "fx:id=\"buttonUserLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert emailGuideLogin != null : "fx:id=\"emailGuideLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert emailUserLogin != null : "fx:id=\"emailUserLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert guideButtonLogin != null : "fx:id=\"guideButtonLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordGuideLogin != null : "fx:id=\"passwordGuideLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordUserLogin != null : "fx:id=\"passwordUserLogin\" was not injected: check your FXML file 'Login.fxml'.";

    }

}