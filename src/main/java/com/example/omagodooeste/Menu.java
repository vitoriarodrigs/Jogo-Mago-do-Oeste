package com.example.omagodooeste;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("/Combate/Combate.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 720);
        stage.setTitle("Combate");
        stage.setScene(scene);
        stage.show();
        System.out.println(Menu.class.getResource("/Combate/Combate.fxml"));
    }

    public static void main(String[] args) {
        launch();
    }
}