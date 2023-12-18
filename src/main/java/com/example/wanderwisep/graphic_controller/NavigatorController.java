package com.example.wanderwisep.graphic_controller;
import javafx.scene.control.Alert;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class NavigatorController {
    public static final String LOGIN = "view/Login.fxml";
    public static final String SEARCHBAR = "view/SearchBar.fxml";
    public static final String GUIDECONFIRM = "view/GuideConfirm.fxml";
    public static final String GUIDEDTOUR = "view/GuidedTour.fxml";
    public static final String TOURLIST = "view/TourList.fxml";

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
    protected File fromBlobToPng(Blob blob) throws IOException {
        String blobString = blob.toString();
        String imageData = blobString;
        String base64Data = imageData.split(",")[1];

        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
        BufferedImage image = ImageIO.read(bis);

        File outputFile = new File("output.png");
        ImageIO.write(image, "png", outputFile);
        return outputFile;
    }
}
