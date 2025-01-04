package com.example.schoolvotingapp;

import database.DatabaseInitializer;
import database.UserDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Inicjalizacja bazy danych
        DatabaseInitializer.initialize();
//        UserDAO.addUser(0, "Mateusz", "ADMIN");
//        for(int i = 101; i <= 112; i++){
//            UserDAO.addUser(i, "Student" + i, "STUDENT");
//        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 400);
        stage.setTitle("System głosowania");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}