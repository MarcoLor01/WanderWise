package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.TicketListBean;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.TicketNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MyAreaController implements InitializableController {

    BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();

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

    @Override
    public void initializeData(Object data) {
        if (data instanceof TicketListBean ticketListBean) {
            startView(ticketListBean);
        }
    }

    private void startView(TicketListBean ticketListBean) {
        try {
            ticketListBean = bookTourControllerApplication.createMyArea(ticketListBean);
            List<Date> datePrenotation = ticketListBean.getPrenotationDate();
            List<String> tourName = ticketListBean.getTourName();
            List<stateEnum> stateEnum = ticketListBean.getStateEnum();
            List<Integer> idTicket = ticketListBean.getIdTicket();
            String email = ticketListBean.getEmail();
            int ticketNumber = tourName.size();
            int i = 0;
            double x = 0;
            while (i < ticketNumber) {
                VBox vBox = new VBox();
                anchorPaneBase.getChildren().add(vBox);
                vBox.setStyle("-fx-border-color: white; -fx-border-width: 1;");
                double startX = 3;
                double startY = 14;
                AnchorPane.setLeftAnchor(vBox, startX + x);
                double boxWidth = 260;
                double boxHeight = 123;
                double imageWidth = 98.0;
                double imageHeight = 119;
                AnchorPane.setTopAnchor(vBox, startY); // Imposta la distanza dall'alto
                vBox.setPrefWidth(boxWidth); // Imposta la larghezza preferita del VBox
                vBox.setPrefHeight(boxHeight); // Imposta l'altezza preferita del VBox
                ImageView imageView = new ImageView();
                //imageView.setImage(convertBlobToImage(tourPhoto.get(i))); //take the photo
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TicketNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

