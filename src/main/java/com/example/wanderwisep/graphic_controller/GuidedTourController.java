package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.GuidedTourBean;
import com.example.wanderwisep.bean.TicketBean;
import com.example.wanderwisep.bean.TicketListBean;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.DuplicateTourException;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuidedTourController extends NavigatorController implements InitializableController {

    private static final Logger logger = Logger.getLogger(SearchBarController.class.getName());
    BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();

    @Override
    public void initializeData(Object data) {
        if (data instanceof GuidedTourBean) {
            startView((GuidedTourBean) data);
        }
    }
    @FXML
    private Text tourTitleText;

    @FXML // fx:id="bookTour"
    private Button bookTour; // Value injected by FXMLLoader

    @FXML // fx:id="flowPaneId"
    private FlowPane flowPaneId; // Value injected by FXMLLoader
    @FXML
    private Text guideNameText;
    @FXML // fx:id="homeGuidedTour"
    private Text homeGuidedTour; // Value injected by FXMLLoader
    @FXML // fx:id="loginAnchorGuidedTour"
    private AnchorPane loginAnchorGuidedTour; // Value injected by FXMLLoader

    @FXML // fx:id="loginGuidedTour"
    private ImageView loginGuidedTour; // Value injected by FXMLLoader

    @FXML // fx:id="logoutGuidedTour"
    private Text logoutGuidedTour; // Value injected by FXMLLoader

    @FXML // fx:id="myAreaGuidedTour"
    private Text myAreaGuidedTour; // Value injected by FXMLLoader

    @FXML // fx:id="tourImage"
    private ImageView tourImage; // Value injected by FXMLLoader

    void startView(GuidedTourBean guidedTourBean) {
        String tourName = guidedTourBean.getTourName();
        Blob photo = guidedTourBean.getPhoto();
        List<String> listOfAttraction = guidedTourBean.getListOfAttraction();
        String touristGuideName = guidedTourBean.getTouristGuideName();
        String touristGuideSurname = guidedTourBean.getTouristGuideSurname();
        tourTitleText.setText(tourName);
        setTextN(0, tourTitleText, 20);
        guideNameText.setText("Hosted by " + touristGuideName + touristGuideSurname);
        setTextN(0, guideNameText, 12);
        tourTitleText.setTextAlignment(TextAlignment.LEFT);
        guideNameText.setTextAlignment(TextAlignment.LEFT);
        tourImage.setImage(convertBlobToImage(photo));
        int numberOfAttractions = listOfAttraction.size();
        VBox vbox = new VBox();
        for (int i = 0; i < numberOfAttractions; i++) {
            Circle circleTappa = new Circle(7);
            circleTappa.setFill(Color.web("#1e90ff"));
            circleTappa.setStroke(Color.BLACK);
            Text nomeTappa = new Text(listOfAttraction.get(i));
            setTextN(0, nomeTappa, 10);
            VBox container = new VBox(circleTappa, nomeTappa);
            if (i < numberOfAttractions - 1) {
                Line lineaTappa = new Line();
                lineaTappa.setEndY(20);
                container.getChildren().add(lineaTappa);
            }
            container.setAlignment(Pos.CENTER);
            vbox.getChildren().add(container);
        }
        flowPaneId.getChildren().add(vbox);
    }

    @FXML
    void bookTour() {
        try {
            TicketBean ticketBean = new TicketBean();
            ticketBean.setGuidedTour(tourTitleText.getText());
            ticketBean.setPrenotationDate(LocalDate.now());
            ticketBean.setStateTicket("waiting for confirmation");
            bookTourControllerApplication.createTicket(ticketBean);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guided Tour");
            alert.setContentText("Guided Tour Booked");
            alert.showAndWait();
        } catch (IOException | DAOException | SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            showErrorDialog("error in booking the tour", "Error guided tour");
        } catch (DuplicateTourException e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog("You have already booked this guided tour", "Error guided tour");
        }
    }

    @FXML
    void logout() {
        goToPage(LOGIN);
    }

    @FXML
    void openHome() {
        goToPage(SEARCHBAR);
    }

    @FXML
    void openLogout() {

    }

    @FXML
    void openMyArea() {
        TicketListBean ticketBeanList = new TicketListBean();
        ticketBeanList.setEmail("user@user.com"); //Da prendere con la Session
        goToPageInit(MYAREA, ticketBeanList);
    }



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert bookTour != null : "fx:id=\"bookTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert flowPaneId != null : "fx:id=\"flowPaneId\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert homeGuidedTour != null : "fx:id=\"homeGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert loginAnchorGuidedTour != null : "fx:id=\"loginAnchorGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert loginGuidedTour != null : "fx:id=\"loginGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert logoutGuidedTour != null : "fx:id=\"logoutGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert myAreaGuidedTour != null : "fx:id=\"myAreaGuidedTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert tourImage != null : "fx:id=\"tourImage\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert tourTitleText != null : "fx:id=\"tourTitleText\" was not injected: check your FXML file 'tourTitleText.fxml'.";
    }
}