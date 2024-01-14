package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.TicketListBean;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.scene.paint.Color.*;

public class MyAreaController extends NavigatorController implements InitializableController {
    Logger logger = Logger.getLogger(MyAreaController.class.getName());
    BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();
    String idSession;
    @FXML
    private AnchorPane anchorPaneBase;

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

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert confirmationCircle != null : "fx:id=\"confirmationCircle\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert guidedTourName != null : "fx:id=\"guidedTourName\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert myAreaButtonMyArea != null : "fx:id=\"myAreaButtonMyArea\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert touristGuideName != null : "fx:id=\"touristGuideName\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert viewGuidedTour != null : "fx:id=\"viewGuidedTour\" was not injected: check your FXML file 'MyArea.fxml'.";
    }

    @Override
    public void initializeData(Object data) {
        if (data instanceof String id) {
            idSession = id;
            startView();
        }
    }

    private void startView() {
        try {
            TicketListBean ticketListBean = new TicketListBean();
            ticketListBean.setIdSession(idSession);
            ticketListBean = bookTourControllerApplication.createMyArea(ticketListBean);
            List<String> idTicket = ticketListBean.getIdTicket();
            List<String> stateEn = ticketListBean.getStateEnum();
            List<LocalDate> datePrenotation = ticketListBean.getPrenotationDate();
            List<String> tourName = ticketListBean.getTourName();
            List<LocalDate> departureDate = ticketListBean.getDepartureDate();
            List<LocalDate> returnDate = ticketListBean.getReturnDate();

            int ticketNumber = idTicket.size();
            int i = 0;
            double x = 0;
            while (i < ticketNumber) {
                VBox vBox = new VBox();
                anchorPaneBase.getChildren().add(vBox);
                vBox.setStyle("-fx-border-color: white; -fx-border-width: 1;");
                double startX = 10;
                double startY = 14;
                AnchorPane.setLeftAnchor(vBox, startX + x);
                double boxWidth = 260;
                double boxHeight = 70;
                AnchorPane.setTopAnchor(vBox, startY); // Imposta la distanza dall'alto
                vBox.setPrefWidth(boxWidth); // Imposta la larghezza preferita del VBox
                vBox.setPrefHeight(boxHeight); // Imposta l'altezza preferita del VBox
                Text tourN = new Text("Tour name: " + tourName.get(i));
                String ticketState = stateEn.get(i);
                Circle circle;
                if (Objects.equals(ticketState, "Waiting for confirmation")) {
                    circle = new Circle(4, GREY);
                } else if (Objects.equals(ticketState, "Confirmed")) {
                    circle = new Circle(4, BLUE);
                } else {
                    circle = new Circle(4, RED);
                }
                circle.setStroke(BLACK);
                Text prenotationD = new Text(("Prenotation Date: " + datePrenotation.get(i).toString()));
                Text stateEnum = new Text(" State: " + stateEn.get(i));
                setTextN(-2, tourN, 10);
                setTextN(-2, prenotationD, 10);
                setTextN(-2, stateEnum, 10);
                HBox circleAndStateBox = new HBox(circle, stateEnum); // Posiziona il cerchio e il testo dello stato in un HBox
                VBox.setMargin(circleAndStateBox, new Insets(0, 15, 0, 0)); // Aggiunge un margine tra il cerchio e lo stato
                vBox.getChildren().addAll(tourN, prenotationD, circleAndStateBox);
                x += boxWidth + startX;
                i++;
            }
        } catch (IOException | SQLException | CsvValidationException e) {
            logger.log(Level.INFO, e.getMessage());
        } catch (TicketNotFoundException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog("No requests available", "Request Available");
        }
    }
}

