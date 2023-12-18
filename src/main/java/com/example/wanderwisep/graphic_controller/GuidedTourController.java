package com.example.wanderwisep.graphic_controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class GuidedTourController extends NavigatorController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="bookTour"
    private Button bookTour; // Value injected by FXMLLoader

    @FXML // fx:id="firstStopText"
    private Text firstStopText; // Value injected by FXMLLoader

    @FXML // fx:id="flowPaneId"
    private FlowPane flowPaneId; // Value injected by FXMLLoader

    @FXML // fx:id="homeGuidedTour"
    private Text homeGuidedTour; // Value injected by FXMLLoader

    @FXML // fx:id="loginAnchorGuidedTour"
    private AnchorPane loginAnchorGuidedTour; // Value injected by FXMLLoader

    @FXML // fx:id="loginGuidedTour"
    private ImageView loginGuidedTour; // Value injected by FXMLLoader

    @FXML // fx:id="logoutGuidedTour"
    private Text logoutGuidedTour; // Value injected by FXMLLoader

    @FXML // fx:id="myAreaGuidedTour"
    private Text myAreaGuidedTour; // Value injected by FXMLLoader

    @FXML // fx:id="tourImage"
    private ImageView tourImage; // Value injected by FXMLLoader

    @FXML
    void bookTour(ActionEvent event) {

    }

    @FXML
    void logout(MouseEvent event) {

    }

    @FXML
    void openHome(MouseEvent event) {

    }

    @FXML
    void openLogout(MouseEvent event) {

    }

    @FXML
    void openMyArea(MouseEvent event) {

    }



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert bookTour != null : "fx:id=\"bookTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert firstStopText != null : "fx:id=\"firstStopText\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert flowPaneId != null : "fx:id=\"flowPaneId\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert homeGuidedTour != null : "fx:id=\"homeGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert loginAnchorGuidedTour != null : "fx:id=\"loginAnchorGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert loginGuidedTour != null : "fx:id=\"loginGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert logoutGuidedTour != null : "fx:id=\"logoutGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert myAreaGuidedTour != null : "fx:id=\"myAreaGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert tourImage != null : "fx:id=\"tourImage\" was not injected: check your FXML file 'GuidedTour.fxml'.";

    }

}