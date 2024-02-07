package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.application_controller.LoginControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.bean.SearchBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.exception.InvalidFormatException;
import com.example.wanderwisep.exception.TourNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchBarController extends NavigatorController implements InitializableController {

    private final BookTourControllerApplication bookTourController = new BookTourControllerApplication();
    private final LoginControllerApplication loginController = new LoginControllerApplication();
    private final Logger logger = Logger.getLogger(SearchBarController.class.getName());
    private String idSession;
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

    @FXML
    public void logout() {
        LoginBean loginBean = new LoginBean();
        loginBean.setIdSession(idSession);
        loginController.logout(loginBean);
        goToPageInit(LOGIN, loginBean);
    }

    @FXML
    public void openMyArea() {
        goToPageInit(MYAREA, idSession);
    }

    @FXML
    public void openLogout() {
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
    public void searchDestination() {
        try {

            SearchBean newSearch = new SearchBean();
            newSearch.checkField(citySearch.getText(), dateInitial.getValue(), dateFinal.getValue());
            newSearch.setCityName((citySearch.getText()));
            newSearch.setDepartureDate(dateInitial.getValue());
            newSearch.setReturnDate(dateFinal.getValue());
            newSearch.setIdSession(idSession);
            TourListBean tourListBean = bookTourController.searchTour(newSearch);
            goToPageInit(TOURLIST, tourListBean);

        } catch (SQLException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog(e.getMessage(), "Search Tour Error");
        } catch (InvalidFormatException | TourNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage());
            showErrorDialog(e.getMessage(), "Search Tour Error");
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

    @Override
    public void initializeData(Object data) {
        if (data instanceof LoginBean loginBean) {
            idSession = loginBean.getIdSession();
        }
        if (data instanceof String sessionId) {
            idSession = sessionId;
        }
    }
}