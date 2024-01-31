package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.ManageMyBookedGuidedToursController;
import com.example.wanderwisep.bean.TicketListBean;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
    private final Logger logger = Logger.getLogger(MyAreaController.class.getName());
    private final ManageMyBookedGuidedToursController manageMyGuidedTourApplication = new ManageMyBookedGuidedToursController();
    private String idSession;
    private int pageNumber = 0;
    private final int maxToursForRow = 2;
    private final int maxToursForPage = 6;
    private TicketListBean ticketListBean;
    private int minTicket = 0;
    private int maxTicket = 6;
    @FXML
    private AnchorPane anchorPaneBase;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="homeButton"
    private Text homeButton; // Value injected by FXMLLoader

    @FXML // fx:id="myAreaButtonMyArea"
    private Text myAreaButtonMyArea; // Value injected by FXMLLoader

    @Override
    public void initializeData(Object data) {
        if (data instanceof String id) {
            idSession = id;
            startView();
        }
    }

    private void startView() {
        try {

            TicketListBean ticketListB = new TicketListBean();
            ticketListB.setIdSession(idSession);
            ticketListBean = manageMyGuidedTourApplication.createMyArea(ticketListB);

            initializePage(ticketListBean.getIdTicket(), ticketListBean.getTourName(), ticketListBean.getStateEnum(), ticketListBean.getPrenotationDate());

        } catch (IOException | SQLException | CsvValidationException | TourException |
                 TouristGuideNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (TicketNotFoundException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog("No requests available", "Request Availability");
        }
    }

    private void initializePage(List<String> idTicket, List<String> tourName, List<String> stateEn, List<LocalDate> datePrenotation) {

        anchorPaneBase.getChildren().clear();
        int i = 0;
        double x = 0;
        double y = 0;
        int ticketNumber = idTicket.size();
        int maxNumber = Math.min(ticketNumber, maxToursForPage);
        while (i < maxNumber) {

            VBox vBox = new VBox();
            anchorPaneBase.getChildren().add(vBox);
            vBox.setStyle("-fx-border-color: white; -fx-border-width: 1;");

            double startX = 10;
            double startY = 10;
            double boxWidth = 260;
            double boxHeight = 70;

            AnchorPane.setLeftAnchor(vBox, startX + x);
            AnchorPane.setTopAnchor(vBox, startY + y);

            vBox.setPrefWidth(boxWidth);
            vBox.setPrefHeight(boxHeight);

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

            if (i % maxToursForRow == 1) {

                x = 0;
                y += boxHeight + startY;
            } else {
                x += boxWidth + startX;
            }
            i++;
        }
    }

    public void nextPage() {

        if (ticketListBean.getIdTicket().size() > maxTicket) {

            pageNumber++;
            minTicket = maxTicket;
            maxTicket += maxToursForPage;
            int maxNumber = Math.min(ticketListBean.getIdTicket().size(), maxTicket);

            initializePage(ticketListBean.getIdTicket().subList(minTicket, maxNumber),
                    ticketListBean.getTourName().subList(minTicket, maxNumber),
                    ticketListBean.getStateEnum().subList(minTicket, maxNumber),
                    ticketListBean.getPrenotationDate().subList(minTicket, maxNumber)
            );

        } else {
            logger.log(Level.INFO, "No more tickets");
            showErrorDialog("No more tickets", "WanderWise");

        }
    }

    public void previousPage() {

        if (pageNumber != 0) {

            pageNumber--;
            maxTicket = minTicket;
            minTicket = minTicket - maxToursForPage;

            initializePage(ticketListBean.getIdTicket().subList(minTicket, maxTicket),
                    ticketListBean.getTourName().subList(minTicket, maxTicket),
                    ticketListBean.getStateEnum().subList(minTicket, maxTicket),
                    ticketListBean.getPrenotationDate().subList(minTicket, maxTicket)
            );

        } else {
            logger.log(Level.INFO, "PAGE 0");
            showErrorDialog("This is the first page", "WanderWise");
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'MyArea.fxml'.";
        assert myAreaButtonMyArea != null : "fx:id=\"myAreaButtonMyArea\" was not injected: check your FXML file 'MyArea.fxml'.";
    }

}

