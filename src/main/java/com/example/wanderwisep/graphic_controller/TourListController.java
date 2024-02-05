package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.GuidedTourBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
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

    private final BookTourControllerApplication bookTourController = new BookTourControllerApplication();
    private final Logger logger = Logger.getLogger(TourListController.class.getName());
    private String idSession;
    private TourListBean toursList;
    private int pageNumber = 0;

    private int minTicket = 0;

    private int maxTicket = 10;

    private static final int MAX_TOURS_FOR_PAGE = 10;

    public void initializeData(Object data) {
        if (data instanceof TourListBean tourListBean) {
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

    public void openTourDescription(String tourName, LocalDate departureDate, LocalDate returnData) {
        try {
            GuidedTourBean guidedTourBean = new GuidedTourBean();
            guidedTourBean.setTourName(tourName);
            guidedTourBean.setDepartureDate(departureDate);
            guidedTourBean.setReturnDate(returnData);
            guidedTourBean.setIdSession(idSession);
            guidedTourBean = bookTourController.getTourDescription(guidedTourBean);
            goToPageInit(GUIDEDTOUR, guidedTourBean);
        } catch (TourNotFoundException | SQLException | TouristGuideNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog(e.getMessage(), "Tour Visualization Error");
        }
    }

    public void startView(TourListBean tourListBean) {
        toursList = tourListBean;
        initializePage(tourListBean.getTourName(), tourListBean.getPhoto(), tourListBean.getDepartureDate(), tourListBean.getReturnDate());
    }

    private void initializePage(List<String> tourName, List<Blob> tourPhoto, List<LocalDate> departureDate, List<LocalDate> returnDate) {

        anchorPaneBase.getChildren().clear();
        int tourNumber = tourName.size();

        int i = 0;
        double x = 0;
        double y = 0;
        double startX = 8.8;
        double startY = 10.0;
        double boxWidth = 100.0;
        double boxHeight = 110.0;
        double imageWidth = 98.0;
        double imageHeight = 74.0;

        int maxNumber = Math.min(tourNumber, MAX_TOURS_FOR_PAGE);
        while (i < maxNumber) {

            VBox vBox = new VBox();
            anchorPaneBase.getChildren().add(vBox);
            vBox.setStyle("-fx-border-color: white; -fx-border-width: 2;");
            AnchorPane.setLeftAnchor(vBox, startX + x);
            AnchorPane.setTopAnchor(vBox, startY + y);
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

            if (i == 4) {
                x = 0;
                y += startY + boxHeight + 2;
            } else {
                x += boxWidth + startX;
            }
            i++;
        }
    }

    @FXML
    private void nextPage() {

        if (toursList.getTourName().size() > maxTicket) {
            pageNumber++;
            minTicket = maxTicket;
            maxTicket += MAX_TOURS_FOR_PAGE;
            int maxNumber = Math.min(toursList.getTourName().size(), maxTicket);
            initializePage(toursList.getTourName().subList(minTicket, maxNumber),
                    toursList.getPhoto().subList(minTicket, maxNumber),
                    toursList.getDepartureDate().subList(minTicket, maxNumber),
                    toursList.getReturnDate().subList(minTicket, maxNumber)
            );

        } else {
            logger.log(Level.INFO, "No more tickets");
            showErrorDialog("No more tickets", "WanderWise");

        }
    }

    @FXML
    private void previousPage() {

        if (pageNumber != 0) {
            pageNumber--;
            maxTicket = minTicket;
            minTicket = minTicket - MAX_TOURS_FOR_PAGE;

            initializePage(toursList.getTourName().subList(minTicket, maxTicket),
                    toursList.getPhoto().subList(minTicket, maxTicket),
                    toursList.getDepartureDate().subList(minTicket, maxTicket),
                    toursList.getReturnDate().subList(minTicket, maxTicket)
            );

        } else {
            logger.log(Level.INFO, "PAGE 0");
            showErrorDialog("This is the first page", "WanderWise");
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


