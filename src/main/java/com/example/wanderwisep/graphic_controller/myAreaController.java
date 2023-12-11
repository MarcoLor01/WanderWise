package com.example.wanderwisep.graphic_controller;

import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.fxml.FXML;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.shape.Circle;
        import javafx.scene.text.Text;

public class myAreaController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="confirmationCircle"
    private Circle confirmationCircle; // Value injected by FXMLLoader

    @FXML // fx:id="guidedTourName"
    private Text guidedTourName; // Value injected by FXMLLoader

    @FXML // fx:id="homeButton"
    private Text homeButton; // Value injected by FXMLLoader

    @FXML // fx:id="myAreaButtonMyArea"
    private Text myAreaButtonMyArea; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private Text status; // Value injected by FXMLLoader

    @FXML // fx:id="touristGuideName"
    private Text touristGuideName; // Value injected by FXMLLoader

    @FXML // fx:id="viewGuidedTour"
    private Text viewGuidedTour; // Value injected by FXMLLoader

    @FXML
    void openGuidedTour(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert confirmationCircle != null : "fx:id=\"confirmationCircle\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert guidedTourName != null : "fx:id=\"guidedTourName\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert myAreaButtonMyArea != null : "fx:id=\"myAreaButtonMyArea\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert touristGuideName != null : "fx:id=\"touristGuideName\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert viewGuidedTour != null : "fx:id=\"viewGuidedTour\" was not injected: check your FXML file 'MyArea.fxml'.";

    }

}

