package com.example.wanderwisep;
import com.example.wanderwisep.graphic_controller.NavigatorSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.wanderwisep.graphic_controller.NavigatorController.LOGIN;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(LOGIN));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        NavigatorSingleton navigator = NavigatorSingleton.getInstance(stage);
        navigator.getStg().setScene(scene);
        navigator.getStg().setTitle("WanderWise");
        navigator.getStg().setResizable(false);
        navigator.getStg().show();
    }
}