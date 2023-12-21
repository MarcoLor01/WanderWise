package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.GuidedTourBean;
import com.example.wanderwisep.bean.TicketBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
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

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Text tourTitleText;

    @FXML // fx:id="bookTour"
    private Button bookTour; // Value injected by FXMLLoader

    @FXML // fx:id="firstStopText"
    private Text firstStopText; // Value injected by FXMLLoader

    @FXML // fx:id="flowPaneId"
    private FlowPane flowPaneId; // Value injected by FXMLLoader
    @FXML
    private Text guideNameText;
    @FXML // fx:id="homeGuidedTour"
    private Text homeGuidedTour; // Value injected by FXMLLoader
    @FXML
    private Line lineaTappa;
    @FXML
    private Circle circleTappa;
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
        String cityName = guidedTourBean.getCityName();
        String tourName = guidedTourBean.getTourName();
        LocalDate departureDate = guidedTourBean.getDepartureDate();
        LocalDate returnDate = guidedTourBean.getReturnDate();
        Blob photo = guidedTourBean.getPhoto();
        List<String> listOfAttraction = guidedTourBean.getListOfAttraction();
        String touristGuide = guidedTourBean.getTouristGuideName();
        tourTitleText.setText(tourName);
        setTextN(0, tourTitleText, 20);
        guideNameText.setText("Hosted by " + touristGuide);
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
    void bookTour(ActionEvent event) {
        //try{
        TicketBean ticketBean = new TicketBean();
        ticketBean.setGuidedTour(tourTitleText.getText());
        ticketBean.setEmailSender("not implemented yet");
        ticketBean.setPrenotationDate(LocalDate.now());
        bookTourControllerApplication.createTicket(ticketBean);

        //}
    }

    @FXML
    void logout(MouseEvent event) {
        goToPage(LOGIN);
    }

    @FXML
    void openHome(MouseEvent event) {
        goToPage(SEARCHBAR);
    }

    @FXML
    void openLogout(MouseEvent event) {

    }

    @FXML
    void openMyArea(MouseEvent event) {

    }



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert bookTour != null : "fx:id=\"bookTour\" was not injected: check your FXML file 'GuidedTour.fxml'.";
        assert firstStopText != null : "fx:id=\"firstStopText\" was not injected: check your FXML file 'GuidedTour.fxml'.";
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