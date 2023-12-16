package com.example.wanderwisep.graphic_controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.wanderwisep.sessionManagment.SessionManagerSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class GuideConfirmController {

    @FXML
    private Text nameGuideText;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="anchorRequest"
    private AnchorPane anchorRequest; // Value injected by FXMLLoader

    @FXML // fx:id="availabilityNo"
    private Button availabilityNo; // Value injected by FXMLLoader

    @FXML // fx:id="availabilityYes"
    private Button availabilityYes; // Value injected by FXMLLoader

    @FXML // fx:id="textFor"
    private Text textFor; // Value injected by FXMLLoader

    @FXML // fx:id="textFrom"
    private Text textFrom; // Value injected by FXMLLoader

    @FXML
    void confirmTour(ActionEvent event) {

    }

    @FXML
    void rejectTour(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert anchorRequest != null : "fx:id=\"anchorRequest\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert availabilityNo != null : "fx:id=\"availabilityNo\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert availabilityYes != null : "fx:id=\"availabilityYes\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert textFor != null : "fx:id=\"textFor\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert textFrom != null : "fx:id=\"textFrom\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        String name = SessionManagerSingleton.getInstance().getCurrentUserName();
        nameGuideText.setText("Hello " + name);
    }

}
