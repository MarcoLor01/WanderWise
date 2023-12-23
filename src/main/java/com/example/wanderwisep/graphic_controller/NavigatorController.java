package com.example.wanderwisep.graphic_controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.InputStream;
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NavigatorController {
    private static final Logger logger = Logger.getLogger(TourListController.class.getName());
    public static final String LOGIN = "view/Login.fxml";
    public static final String SEARCHBAR = "view/SearchBar.fxml";
    public static final String GUIDECONFIRM = "view/GuideConfirm.fxml";
    public static final String GUIDEDTOUR = "view/GuidedTour.fxml";
    public static final String TOURLIST = "view/TourList.fxml";
    public static final String MYAREA = "view/MyArea.fxml";

    protected void goToPage(String page){
        try{
            NavigatorSingleton.getInstance().goToPage(page);
        }
        catch(Exception e){
            Logger.getAnonymousLogger().log(
                    Level.INFO, e.getMessage()
            );
        }
    }
    protected void goToPageInit(String page,Object obj){
        try{
            NavigatorSingleton.getInstance().goToPageInit(page, obj);
        }
        catch(Exception e){
            Logger.getAnonymousLogger().log(
                    Level.INFO, e.getMessage()
            );
        }
    }
    protected void showErrorDialog(String message,String Title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected Image convertBlobToImage(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream()) {
            return new Image(inputStream);
        } catch (Exception e) {
            logger.log(Level.INFO, e.getMessage());
            showErrorDialog("Error in the visualization of the tour", "Visualization tour error");
        }

        return null;
    }

    protected void setTextN(Integer i, Text toSet, Integer fontText) {
        toSet.setTextAlignment(TextAlignment.CENTER);
        toSet.setTranslateY(i);
        toSet.setFont(Font.font("Verdana Pro Cond Semibold", fontText));
    }
}

