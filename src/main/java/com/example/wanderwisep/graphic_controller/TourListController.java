package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.bean.TourListBean;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TourListController extends NavigatorController implements InitializableController {

    private static final Logger logger = Logger.getLogger(TourListController.class.getName());
    public void initializeData(Object data) {
        if (data instanceof TourListBean) {
            startView((TourListBean) data);
        }
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML
    private VBox vBoxGuidedTour;

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

    private double startX = 8.8;
    private double startY = 14.0;
    private double boxWidth = 100.0;
    private double boxHeight = 110.0;
    private double imageWidth = 98.0;
    private double imageHeight = 74.0;
    private double spacing = 8.8;

    @FXML
    void logout(MouseEvent event) {

    }

    @FXML
    void openLogoutMenu(MouseEvent event) {

    }
    void startView(TourListBean tourListBean) {
            List<String> tourName = tourListBean.getTourName();
            List<Blob> tourPhoto = tourListBean.getPhoto();
            int tourNumber = tourName.size();
            System.out.println(tourNumber);
            int i = 0;
            double x = 0;
            while (i < tourNumber) {
                VBox vBox = new VBox();
                anchorPaneBase.getChildren().add(vBox);
                vBox.setStyle("-fx-border-color: white; -fx-border-width: 2;");
                AnchorPane.setLeftAnchor(vBox, startX + x); // Imposta la distanza a sinistra
                AnchorPane.setTopAnchor(vBox, startY); // Imposta la distanza dall'alto
                vBox.setPrefWidth(boxWidth); // Imposta la larghezza preferita del VBox
                vBox.setPrefHeight(boxHeight); // Imposta l'altezza preferita del VBox
                // Aggiungi ImageView
                ImageView imageView = new ImageView();
                imageView.setImage(convertBlobToImage(tourPhoto.get(i)));
                imageView.setFitWidth(98);
                imageView.setFitHeight(74);
                Text tourNameText = new Text(tourName.get(i));
                tourNameText.setTranslateY(-5);
                tourNameText.setFont(Font.font("Verdana Pro Cond Semibold", 10));
                vBox.getChildren().addAll(imageView, tourNameText);
                VBox.setMargin(imageView, new Insets(0, 0, 5, 0)); // Margine inferiore per separare l'ImageView dal Textus
                x += boxWidth + startX;
                i++;
            }
        }



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize(TourListBean tourListBean) {
        assert anchorPaneBase != null : "fx:id=\"anchorPaneBase\" was not injected: check your FXML file 'TourList.fxml'.";
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'TourList.fxml'.";
        assert logoutTourList != null : "fx:id=\"logoutTourList\" was not injected: check your FXML file 'TourList.fxml'.";
        assert logoutTourListLine != null : "fx:id=\"logoutTourListLine\" was not injected: check your FXML file 'TourList.fxml'.";
        assert tourListLogoutAnchor != null : "fx:id=\"tourListLogoutAnchor\" was not injected: check your FXML file 'TourList.fxml'.";
        assert userIconTourlist != null : "fx:id=\"userIconTourlist\" was not injected: check your FXML file 'TourList.fxml'.";
        assert vBoxGuidedTour != null : "fx:id=\"vBoxGuidedTour\" was not injected: check your FXML file 'TourList.fxml'.";
    }

}


