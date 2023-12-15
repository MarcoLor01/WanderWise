package com.example.wanderwisep.graphic_controller;

import javafx.scene.control.Alert;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NavigatorController {
    public static final String LOGIN = "view/Login.fxml";
    public static final String SEARCHBAR = "view/SearchBar.fxml";
    public static final String GUIDECONFIRM = "view/GuideConfirm.fxml";

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
    protected void showErrorDialog(String message,String Title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
