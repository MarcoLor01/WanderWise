package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.GuidedTourBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.exception.TourNotFoundException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TourListController extends NavigatorController implements InitializableController {

    BookTourControllerApplication bookTourController = new BookTourControllerApplication();
    private static final Logger logger = Logger.getLogger(SearchBarController.class.getName());

    public void initializeData(Object data) {
        if (data instanceof TourListBean) {
            startView((TourListBean) data);
        }
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private AnchorPane anchorPaneBase;
    @FXML // fx:id="homeButton"
    private Text homeButton; // Value injected by FXMLLoader

    @FXML // fx:id="logoutTourList"
    private Text logoutTourList; // Value injected by FXMLLoader

    @FXML // fx:id="logoutTourListLine"
    private Line logoutTourListLine; // Value injected by FXMLLoader

    @FXML // fx:id="tourListLogoutAnchor"
    private AnchorPane tourListLogoutAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="userIconTourlist"
    private ImageView userIconTourlist; // Value injected by FXMLLoader

    @FXML
    void logout(MouseEvent event) {

    }

    void openTourDescription(MouseEvent event, String tourName, LocalDate departureDate, LocalDate returnData) {
        try {
            GuidedTourBean guidedTourBean = new GuidedTourBean();
            guidedTourBean.setTourName(tourName);
            guidedTourBean.setDepartureDate(departureDate);
            guidedTourBean.setReturnDate(returnData);
            guidedTourBean = bookTourController.getTourDescription(guidedTourBean);
            goToPageInit(GUIDEDTOUR, guidedTourBean);
        } catch (TourNotFoundException | SQLException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog(e.getMessage(), "Tour Visualization Error");
        }
    }

    @FXML
    void openLogoutMenu(MouseEvent event) {

    }

    void startView(TourListBean tourListBean) {
        List<String> tourName = tourListBean.getTourName();
        List<Blob> tourPhoto = tourListBean.getPhoto();
        List<LocalDate> departureDate = tourListBean.getDepartureDate();
        List<LocalDate> returnDate = tourListBean.getReturnDate();
        int tourNumber = tourName.size();
        int i = 0;
        double x = 0;
        while (i < tourNumber) {
            VBox vBox = new VBox();
            anchorPaneBase.getChildren().add(vBox);
            vBox.setStyle("-fx-border-color: white; -fx-border-width: 2;");
            double startX = 8.8;
            AnchorPane.setLeftAnchor(vBox, startX + x); // Imposta la distanza a sinistra
            double startY = 10.0;
            double boxWidth = 100.0;
            double boxHeight = 110.0;
            double imageWidth = 98.0;
            double imageHeight = 74.0;
            AnchorPane.setTopAnchor(vBox, startY); // Imposta la distanza dall'alto
            vBox.setPrefWidth(boxWidth); // Imposta la larghezza preferita del VBox
            vBox.setPrefHeight(boxHeight); // Imposta l'altezza preferita del VBox
            ImageView imageView = new ImageView();
            imageView.setImage(convertBlobToImage(tourPhoto.get(i)));
            imageView.setFitWidth(imageWidth);
            imageView.setFitHeight(imageHeight);
            Text tourNameText = new Text(tourName.get(i));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Text departureDateText = new Text("From " + departureDate.get(i).format(formatter));
            Text returnDateText = new Text("To " + returnDate.get(i).format(formatter));
            setText(-2, tourNameText);
            setText(-2, departureDateText);
            setText(-2, returnDateText);
            Line separatorLine = new Line(0, 0, boxWidth - 3, 0);
            separatorLine.setStroke(Color.WHITE);
            vBox.getChildren().addAll(imageView, tourNameText, separatorLine, departureDateText, returnDateText);
            vBox.setMargin(separatorLine, new Insets(0, 0, 1, 0));
            tourNameText.setOnMouseClicked(event -> {
                String departureDateString = departureDateText.getText().substring(5);
                LocalDate depDate = LocalDate.parse(departureDateString, formatter);
                String returnDateString = returnDateText.getText().substring(3);
                LocalDate retDate = LocalDate.parse(returnDateString, formatter);
                openTourDescription(event, tourNameText.getText(), depDate, retDate);
            });
            x += boxWidth + startX;
            i++;
        }
    }


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert anchorPaneBase != null : "fx:id=\"anchorPaneBase\" was not injected: check your FXML file 'TourList.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'TourList.fxml'.";
        assert logoutTourList != null : "fx:id=\"logoutTourList\" was not injected: check your FXML file 'TourList.fxml'.";
        assert logoutTourListLine != null : "fx:id=\"logoutTourListLine\" was not injected: check your FXML file 'TourList.fxml'.";
        assert tourListLogoutAnchor != null : "fx:id=\"tourListLogoutAnchor\" was not injected: check your FXML file 'TourList.fxml'.";
        assert userIconTourlist != null : "fx:id=\"userIconTourlist\" was not injected: check your FXML file 'TourList.fxml'.";
    }

}


