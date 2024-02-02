package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.bean.TouristGuideAnswerBean;
import com.example.wanderwisep.bean.TouristGuideRequestsBean;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.RequestNotFoundException;
import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
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
    private final BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();
    private final Logger logger = Logger.getLogger(GuideConfirmController.class.getName());
    private String idSession;
    private TouristGuideRequestsBean requestBean;
    private static final int MAX_REQUEST_FOR_PAGE = 4;
    private int minTicket = 0;
    private int maxTicket = 4;
    private int pageNumber = 0;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML
    private AnchorPane anchorPaneRequests;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="anchorRequest"
    private AnchorPane anchorRequest; // Value injected by FXMLLoader


    @FXML
    public void confirmTour(String user, String guidedTourId) {
        try {
            setTour(user, guidedTourId, "Confirmed");
            showAlertDialog("Request accepted", "Tour Confirmation");

        } catch (IOException | SQLException | CsvValidationException | DAOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog("Please retry", "Tour confirmation");
        } catch (RequestNotFoundException | TourException | TouristGuideNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void setTour(String user, String guidedTourId, String decision) throws DAOException, CsvValidationException, SQLException, TourException, TouristGuideNotFoundException, RequestNotFoundException, IOException {
        TouristGuideAnswerBean answerBean = new TouristGuideAnswerBean();
        answerBean.setIdSession(idSession);
        answerBean.setIdTour(guidedTourId);
        answerBean.setUserEmail(user);
        answerBean.setGuideDecision(decision);
        bookTourControllerApplication.guideDecision(answerBean);
    }

    @FXML
    public void rejectTour(String user, String guidedTourId) {
        try {
            setTour(user, guidedTourId, "Refused");
            showAlertDialog("Request rejected", "Tour Confirmation");

        } catch (IOException | SQLException | CsvValidationException | DAOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog("Please retry", "Tour confirmation");
        } catch (RequestNotFoundException | TourException | TouristGuideNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void startView() {
        try {

            TouristGuideRequestsBean requestB = new TouristGuideRequestsBean();
            requestB.setIdSession(idSession);
            requestBean = bookTourControllerApplication.createTouristGuideArea(requestB);

            initializePage(requestBean.getUserEmail(), requestBean.getGuidedTourId());

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog(e.getMessage(), "Request Visualization Error");
        } catch (RequestNotFoundException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog(e.getMessage(), "No request available");
        }
    }

    private void initializePage(List<String> userEmail, List<String> guidedTourId) {

        anchorPaneRequests.getChildren().clear();
        int i = 0;
        double x = 0;
        double y = 0;
        double startX = 12;
        double startY = 4;
        double boxWidth = 529;
        double boxHeight = 30;
        int maxNumber = Math.min(userEmail.size(), MAX_REQUEST_FOR_PAGE);
        while (i < maxNumber) {

            String user = userEmail.get(i);
            String guidedTour = guidedTourId.get(i);

            Text userEmailText = new Text("User: " + user);
            Text guidedTourIdText = new Text("Tour Id: " + guidedTour);
            Text confirmAvailability = new Text("Confirm availability? ");

            setTextN(0, userEmailText, 12);
            setTextN(0, guidedTourIdText, 12);
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
            AnchorPane.setTopAnchor(vBox, startY + y);
            vBox.setPrefWidth(boxWidth);
            vBox.setPrefHeight(boxHeight);

            anchorPaneRequests.getChildren().add(vBox);

            yesButton.setOnAction(event -> confirmTour(user, guidedTour));
            noButton.setOnAction(event -> rejectTour(user, guidedTour));

            y += 2 * boxHeight;
            i++;
        }
    }

    @Override
    public void initializeData(Object data) {
        if (data instanceof LoginBean loginBean) {
            idSession = loginBean.getIdSession();
            startView();
        }
    }

    public void previousPage() {

        if (pageNumber != 0) {
            pageNumber--;
            maxTicket = minTicket;
            minTicket = minTicket - MAX_REQUEST_FOR_PAGE;
            initializePage(requestBean.getUserEmail().subList(minTicket, maxTicket),
                    requestBean.getGuidedTourId().subList(minTicket, maxTicket)
            );
        } else {
            logger.log(Level.INFO, "PAGE 0");
            showErrorDialog("This is the first page", "WanderWise");
        }
    }

    public void nextPage() {

        if (requestBean.getGuidedTourId().size() > maxTicket) {
            pageNumber++;
            minTicket = maxTicket;
            maxTicket += 4;
            int maxNumber = Math.min(requestBean.getGuidedTourId().size(), maxTicket);
            initializePage(requestBean.getUserEmail().subList(minTicket, maxNumber),
                    requestBean.getGuidedTourId().subList(minTicket, maxNumber)
            );

        } else {
            logger.log(Level.INFO, "No more requests");
            showErrorDialog("No more requests", "WanderWise");
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert anchorRequest != null : "fx:id=\"anchorRequest\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
        assert anchorPaneRequests != null : "fx:id=\"anchorPaneRequests\" was not injected: check your FXML file 'GuideConfirm.fxml'.";
    }

}
