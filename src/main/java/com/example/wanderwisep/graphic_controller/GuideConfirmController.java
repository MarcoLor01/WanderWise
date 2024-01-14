package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.bean.TouristGuideAnswerBean;
import com.example.wanderwisep.bean.TouristGuideRequestsBean;
import com.example.wanderwisep.exception.RequestNotFoundException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    public void confirmTour(String user, String guidedTourId) {
        try {
            TouristGuideAnswerBean answerBean = new TouristGuideAnswerBean();
            answerBean.setIdSession(idSession);
            answerBean.setIdTour(guidedTourId);
            answerBean.setUserEmail(user);
            answerBean.setGuideDecision("Confirmed");
            bookTourControllerApplication.guideDecision(answerBean);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tour Confirmation");
            alert.setContentText("Request accepted");
            alert.showAndWait();
        } catch (IOException | SQLException | CsvValidationException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog("Please retry", "Tour confirmation");
        } catch (RequestNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @FXML
    public void rejectTour(String user, String guidedTourId) {
        try {
            TouristGuideAnswerBean answerBean = new TouristGuideAnswerBean();
            answerBean.setIdSession(idSession);
            answerBean.setIdTour(guidedTourId);
            answerBean.setUserEmail(user);
            answerBean.setGuideDecision("Refused");
            bookTourControllerApplication.guideDecision(answerBean);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tour Confirmation");
            alert.setContentText("Request rejected");
            alert.showAndWait();
        } catch (IOException | SQLException | CsvValidationException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog("Please retry", "Tour confirmation");
        } catch (RequestNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void startView() {
        try {
            TouristGuideRequestsBean requestBean = new TouristGuideRequestsBean();
            requestBean.setIdSession(idSession);
            requestBean = bookTourControllerApplication.createTouristGuideArea(requestBean);
            List<String> userEmail = requestBean.getUserEmail();
            List<String> guidedTourId = requestBean.getGuidedTourId();
            int i = 0;
            double x = 0;
            double startX = 12;
            double startY = 14;
            double boxWidth = 529;
            double boxHeight = 30;
            while (i < userEmail.size()) {
                String user = userEmail.get(i);
                String guidedTour = guidedTourId.get(i);
                Text userEmailText = new Text("User: " + user);
                Text guidedTourIdText = new Text("Tour Id: " + guidedTour);
                setTextN(0, userEmailText, 12);
                setTextN(0, guidedTourIdText, 12);
                Text confirmAvailability = new Text("Confirm availability? ");
                setTextN(-23, confirmAvailability, 12);
                confirmAvailability.setTranslateX(330);
                Button yesButton = new Button("Yes");
                Button noButton = new Button("No");
                HBox hBox = new HBox(confirmAvailability, yesButton, noButton);
                yesButton.setFont(Font.font(12));
                yesButton.setTranslateX(335);
                yesButton.setTranslateY(-28);
                noButton.setFont(Font.font(12));
                noButton.setTranslateX(340);
                noButton.setTranslateY(-28);
                VBox vBox = new VBox(userEmailText, guidedTourIdText, hBox);
                vBox.setStyle("-fx-border-color: white; -fx-border-width: 1;");
                AnchorPane.setLeftAnchor(vBox, startX + x);
                AnchorPane.setTopAnchor(vBox, startY);
                vBox.setPrefWidth(boxWidth);
                vBox.setPrefHeight(boxHeight);
                anchorPaneRequests.getChildren().add(vBox);
                yesButton.setOnAction(event -> confirmTour(user, guidedTour));
                noButton.setOnAction(event -> rejectTour(user, guidedTour));
                i++;
                x += 2;
            }
        } catch (IOException | SQLException | CsvValidationException e) {
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
