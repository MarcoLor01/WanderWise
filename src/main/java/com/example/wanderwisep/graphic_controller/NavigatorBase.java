package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorBase { //I have to use this Navigator because with a normal class when I use extends the class calls the Constructor and stg = null
    private final Stage stg;
    private static NavigatorBase instance;

    public Stage getStg() {
        return stg;
    }



    // Metodo statico per ottenere l'istanza del Singleton
    public static synchronized NavigatorBase getInstance(Stage stg) {
        // Inizializza l'istanza se non è ancora stata creata
        if (instance == null) {
            instance = new NavigatorBase(stg);
        }
        return instance;
    }

    public static synchronized NavigatorBase getInstance() {
        return instance;
    }

    private NavigatorBase(Stage stage) {
        this.stg = stage;
    }

    public void goToPage(String page) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(page));
        Parent root = loader.load();
        System.out.println("3");
        stg.getScene().setRoot(root);
        System.out.println("4");
    }

    public void goToPageInit(String page, Object controllerData) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(page));
        Parent root = loader.load();

        if (controllerData != null && loader.getController() instanceof InitializableController) {
            InitializableController controller = loader.getController();
            controller.initializeData(controllerData);
        }

        stg.getScene().setRoot(root);
    }

}

