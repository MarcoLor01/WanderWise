package com.example.wanderwisep.graphic_controller;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.SearchBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.exception.InvalidFormatException;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.sessionManagment.SessionManagerSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class SearchBarController extends NavigatorController {
    BookTourControllerApplication BookTourController = new BookTourControllerApplication();
    private static final Logger logger = Logger.getLogger(SearchBarController.class.getName());
    @FXML
    private Text userText;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
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

    public SearchBarController() throws TourNotFoundException {
    }

    @FXML
    void logout(MouseEvent event) {

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
    void searchDestination(MouseEvent event) {
        try{
            SearchBean newSearch = new SearchBean();
            newSearch.checkField(citySearch.getText(), dateInitial.getValue(), dateFinal.getValue());
            newSearch.setCityName((citySearch.getText()));
            newSearch.setDepartureDate(dateInitial.getValue());
            newSearch.setReturnDate(dateFinal.getValue());
            TourListBean tourListBean = BookTourController.searchTour(newSearch);
            goToPage(TOURLIST);
        }catch (InvalidFormatException | TourNotFoundException | SQLException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog(e.getMessage(),"Search Tour Error");
        }
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert citySearch != null : "fx:id=\"citySearch\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert dateFinal != null : "fx:id=\"dateFinal\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert dateInitial != null : "fx:id=\"dateInitial\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert loginAnchor != null : "fx:id=\"loginAnchor\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert loginLine != null : "fx:id=\"loginLine\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert loginSearch != null : "fx:id=\"loginSearch\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert logoutSearchBar != null : "fx:id=\"logoutSearchBar\" was not injected: check your FXML file 'SearchBar.fxml'.";
        assert myAreaSearchBar != null : "fx:id=\"myAreaSearchBar\" was not injected: check your FXML file 'SearchBar.fxml'.";
    }
}