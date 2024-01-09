package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.bean.TouristGuideRequestsBean;
import com.example.wanderwisep.exception.TicketNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuideConfirmController extends NavigatorController implements InitializableController {
    BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();
    Logger logger = Logger.getLogger(GuideConfirmController.class.getName());
    private String idSession;
    @FXML
    private Text nameGuideText;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML
    private AnchorPane anchorPaneRequests;

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
    public void confirmTour(ActionEvent event) {

    }

    @FXML
    public void rejectTour(ActionEvent event) {

    }

    public void startView() {
        try {
            TouristGuideRequestsBean requestBean = new TouristGuideRequestsBean();
            requestBean.setIdSession(idSession);
            requestBean = bookTourControllerApplication.createTouristGuideArea(requestBean);
            List<String> userEmail = requestBean.getUserEmail();
            List<String> guidedTourId = requestBean.getGuidedTourId();
            Text confirmAvailability = new Text("confirm availability ");
            int i = 0;
            double x = 0;
            double startX = 12;
            double startY = 14;
            double boxWidth = 529;
            double boxHeight = 29;
            while (i < userEmail.size()) {
                Text userEmailText = new Text("user: " + userEmail.get(i));
                Text guidedTourIdText = new Text("tour: " + guidedTourId.get(i));
                Button yesButton = new Button("Yes");
                Button noButton = new Button("No");
                VBox vBox = new VBox(userEmailText, guidedTourIdText, confirmAvailability, yesButton, noButton);
                anchorPaneRequests.getChildren().add(vBox);
                vBox.setStyle("-fx-border-color: white; -fx-border-width: 1;");
                AnchorPane.setLeftAnchor(vBox, startX + x);
                AnchorPane.setTopAnchor(vBox, startY);
                vBox.setPrefWidth(boxWidth);
                vBox.setPrefHeight(boxHeight);
                i++;
                x += 2;
            }
        } catch (IOException | SQLException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog(e.getMessage(), "Request Visualization Error");
        } catch (TicketNotFoundException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog(e.getMessage(), "No request available");
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert anchorRequest != null : "fx:id=\"anchorRequest\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert availabilityNo != null : "fx:id=\"availabilityNo\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert availabilityYes != null : "fx:id=\"availabilityYes\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert textFor != null : "fx:id=\"textFor\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert textFrom != null : "fx:id=\"textFrom\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert anchorPaneRequests != null : "fx:id=\"anchorPaneRequests\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
    }

    @Override
    public void initializeData(Object data) {
        if (data instanceof LoginBean loginBean) {
            idSession = loginBean.getIdSession();
            startView();
        }
    }
}
