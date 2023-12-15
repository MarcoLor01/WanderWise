package com.example.wanderwisep.graphic_controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.wanderwisep.application_controller.LoginControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.exception.InvalidFormatException;
import com.example.wanderwisep.exception.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LoginController extends NavigatorController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="emailLogin"
    private TextField emailLogin; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private Text loginButton; // Value injected by FXMLLoader

    @FXML // fx:id="passwordLogin"
    private PasswordField passwordLogin; // Value injected by FXMLLoader

    LoginControllerApplication loginController = new LoginControllerApplication();

    @FXML
    void doLogin(MouseEvent event) throws SQLException {
        try {
            LoginBean loginBean = new LoginBean();
            loginBean.setEmail(emailLogin.getText());
            loginBean.setPassword(passwordLogin.getText());
            String role = loginController.login(loginBean).getRole();
            if (Objects.equals(role, "User")) {
                goToPage(SEARCHBAR);
            } else if (Objects.equals(role, "TouristGuide")) {
                goToPage(GUIDECONFIRM);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


        @FXML // This method is called by the FXMLLoader when initialization is complete
        void initialize () {
            assert emailLogin != null : "fx:id=\"emailLogin\" was not injected: check your FXML file 'Login.fxml'.";
            assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'Login.fxml'.";
            assert passwordLogin != null : "fx:id=\"passwordLogin\" was not injected: check your FXML file 'Login.fxml'.";

        }

    }