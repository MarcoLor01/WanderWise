package com.example.wanderwisep.graphic_controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
public class LoginController {

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

    @FXML
    void doLogin(MouseEvent event) {
        String email = emailLogin.getText();
        String password = passwordLogin.getText();
        if (email.isEmpty() || password.isEmpty()) {
            showErrorDialog("Username or password empty");
        } else {
            //Gestire logica di login corretta
        }
    }
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert emailLogin != null : "fx:id=\"emailLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordLogin != null : "fx:id=\"passwordLogin\" was not injected: check your FXML file 'Login.fxml'.";

    }

}
