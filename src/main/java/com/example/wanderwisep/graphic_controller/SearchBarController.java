package com.example.wanderwisep.graphic_controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class SearchBarController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="AmsterdamSearchBarText"
    private Text AmsterdamSearchBarText; // Value injected by FXMLLoader

    @FXML // fx:id="amsterdamSearchBar"
    private ImageView amsterdamSearchBar; // Value injected by FXMLLoader

    @FXML // fx:id="anchorBaseSearchBar"
    private AnchorPane anchorBaseSearchBar; // Value injected by FXMLLoader

    @FXML // fx:id="barcelonaSearchBar"
    private ImageView barcelonaSearchBar; // Value injected by FXMLLoader

    @FXML // fx:id="barcelonaSearchBarText"
    private Text barcelonaSearchBarText; // Value injected by FXMLLoader

    @FXML // fx:id="citySearch"
    private TextField citySearch; // Value injected by FXMLLoader

    @FXML // fx:id="dateFinal"
    private DatePicker dateFinal; // Value injected by FXMLLoader

    @FXML // fx:id="dateInitial"
    private DatePicker dateInitial; // Value injected by FXMLLoader

    @FXML // fx:id="loginAnchor"
    private AnchorPane loginAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="loginLine"
    private Line loginLine; // Value injected by FXMLLoader

    @FXML // fx:id="loginSearch"
    private ImageView loginSearch; // Value injected by FXMLLoader

    @FXML // fx:id="logoutSearchBar"
    private Text logoutSearchBar; // Value injected by FXMLLoader

    @FXML // fx:id="myAreaSearchBar"
    private Text myAreaSearchBar; // Value injected by FXMLLoader

    @FXML // fx:id="parisSearchBar"
    private ImageView parisSearchBar; // Value injected by FXMLLoader

    @FXML // fx:id="parisSearchBarText"
    private Text parisSearchBarText; // Value injected by FXMLLoader

    @FXML // fx:id="searchButton"
    private Button searchButton; // Value injected by FXMLLoader

    @FXML // fx:id="tokyoSearchBar"
    private ImageView tokyoSearchBar; // Value injected by FXMLLoader

    @FXML // fx:id="tokyoSearchBarText"
    private Text tokyoSearchBarText; // Value injected by FXMLLoader

    @FXML
    void logout(MouseEvent event) {

    }

    @FXML
    void openAmsterdam(MouseEvent event) {

    }

    @FXML
    void openBarcelona(MouseEvent event) {

    }

    @FXML
    void openLogout(MouseEvent event) {
        if (loginAnchor.isVisible() || loginLine.isVisible() || logoutSearchBar.isVisible()) {
            // Se la casella di logout è già aperta, la chiudo
            loginAnchor.setVisible(false);
            loginLine.setVisible(false);
            logoutSearchBar.setVisible(false);
        } else {
            // Altrimenti, la apro
            loginAnchor.setVisible(true);
            loginLine.setVisible(true);
            logoutSearchBar.setVisible(true);
        }
    }

    @FXML
    void openMyArea(MouseEvent event) {

    }

    @FXML
    void openParis(MouseEvent event) {

    }

    @FXML
    void openTokyo(MouseEvent event) {

    }

    @FXML
    void searchDestination(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert AmsterdamSearchBarText != null : "fx:id=\"AmsterdamSearchBarText\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert amsterdamSearchBar != null : "fx:id=\"amsterdamSearchBar\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert anchorBaseSearchBar != null : "fx:id=\"anchorBaseSearchBar\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert barcelonaSearchBar != null : "fx:id=\"barcelonaSearchBar\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert barcelonaSearchBarText != null : "fx:id=\"barcelonaSearchBarText\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert citySearch != null : "fx:id=\"citySearch\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert dateFinal != null : "fx:id=\"dateFinal\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert dateInitial != null : "fx:id=\"dateInitial\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert loginAnchor != null : "fx:id=\"loginAnchor\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert loginLine != null : "fx:id=\"loginLine\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert loginSearch != null : "fx:id=\"loginSearch\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert logoutSearchBar != null : "fx:id=\"logoutSearchBar\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert myAreaSearchBar != null : "fx:id=\"myAreaSearchBar\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert parisSearchBar != null : "fx:id=\"parisSearchBar\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert parisSearchBarText != null : "fx:id=\"parisSearchBarText\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert tokyoSearchBar != null : "fx:id=\"tokyoSearchBar\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert tokyoSearchBarText != null : "fx:id=\"tokyoSearchBarText\" was not injected: check your FXML file 'SearchBar.fxml'.";

    }

}