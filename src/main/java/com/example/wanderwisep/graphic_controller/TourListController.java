package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.GuidedTourBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.exception.TourException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TourListController extends NavigatorController implements InitializableController {

    BookTourControllerApplication bookTourController = new BookTourControllerApplication();
    Logger logger = Logger.getLogger(TourListController.class.getName());
    private String idSession;

    public void initializeData(Object data) {
        if (data instanceof TourListBean tourListBean) {
            System.out.println("Nel tourListController quindi grafico: =" + tourListBean.getIdSession());
            idSession = tourListBean.getIdSession();
            startView(tourListBean);
        }
    }

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
    public void logout() {

    }

    public void openTourDescription(String tourName, LocalDate departureDate, LocalDate returnData) {
        try {
            GuidedTourBean guidedTourBean = new GuidedTourBean();
            guidedTourBean.setTourName(tourName);
            guidedTourBean.setDepartureDate(departureDate);
            guidedTourBean.setReturnDate(returnData);
            System.out.println("nell'openTourDescription che dava null = " + idSession);
            guidedTourBean.setIdSession(idSession);
            guidedTourBean = bookTourController.getTourDescription(guidedTourBean);
            System.out.println("nell'openTourDescription prima del GuidedTour = " + idSession);
            goToPageInit(GUIDEDTOUR, guidedTourBean);
        } catch (TourException | SQLException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog(e.getMessage(), "Tour Visualization Error");
        }
    }

    @FXML
    public void openLogoutMenu() {

    }

    public void startView(TourListBean tourListBean) {
        List<String> tourName = tourListBean.getTourName();
        List<Blob> tourPhoto = tourListBean.getPhoto();
        List<LocalDate> departureDate = tourListBean.getDepartureDate();
        List<LocalDate> returnDate = tourListBean.getReturnDate();
        int tourNumber = tourName.size();
        int i = 0;
        double x = 0;
        double startX = 8.8;
        double startY = 10.0;
        double boxWidth = 100.0;
        double boxHeight = 110.0;
        double imageWidth = 98.0;
        double imageHeight = 74.0;

        while (i < tourNumber) {


            VBox vBox = new VBox();
            anchorPaneBase.getChildren().add(vBox);
            vBox.setStyle("-fx-border-color: white; -fx-border-width: 2;");
            AnchorPane.setLeftAnchor(vBox, startX + x);
            AnchorPane.setTopAnchor(vBox, startY);
            vBox.setPrefWidth(boxWidth);
            vBox.setPrefHeight(boxHeight);

            ImageView imageView = new ImageView();
            imageView.setImage(convertBlobToImage(tourPhoto.get(i)));
            imageView.setFitWidth(imageWidth);
            imageView.setFitHeight(imageHeight);

            String nameTour = tourName.get(i); //For the application logic
            LocalDate departureDateTour = departureDate.get(i);
            LocalDate returnDateTour = returnDate.get(i);

            Text tourNameText = new Text(nameTour);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Text departureDateText = new Text("From " + departureDateTour.format(formatter));
            Text returnDateText = new Text("To " + returnDateTour.format(formatter));
            setTextN(-2, tourNameText, 10);
            setTextN(-2, departureDateText, 10);
            setTextN(-2, returnDateText, 10);
            Line separatorLine = new Line(0, 0, boxWidth - 3, 0);
            separatorLine.setStroke(Color.WHITE);
            vBox.getChildren().addAll(imageView, tourNameText, separatorLine, departureDateText, returnDateText);
            VBox.setMargin(separatorLine, new Insets(0, 0, 1, 0));

            tourNameText.setOnMouseClicked(event -> openTourDescription(nameTour, departureDateTour, returnDateTour));
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


