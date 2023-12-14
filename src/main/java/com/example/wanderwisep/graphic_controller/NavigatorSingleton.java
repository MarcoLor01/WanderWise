package com.example.wanderwisep.graphic_controller;

import com.example.wanderwisep.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigatorSingleton {
    private Stage stg;

    public Stage getStg() {
        return stg;
    }
    private static NavigatorSingleton instance;

    // Metodo statico per ottenere l'istanza del Singleton
    public static synchronized NavigatorSingleton getInstance(Stage stg) {
        // Inizializza l'istanza se non è ancora stata creata
        if (instance == null) {
            instance = new NavigatorSingleton(stg);
        }
        return instance;
    }
    public static synchronized NavigatorSingleton getInstance() {
        return instance;
    }

    // Costruttore privato per evitare la creazione diretta di istanze
    private NavigatorSingleton(Stage stage) {
        this.stg = stage;
    }
    public void goToPage(String page) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(page));
        Parent root = loader.load();
        stg.getScene().setRoot(root);
    }
}

