package com.example.schoolvotingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {
    @FXML
    private Button exitButton;
    @FXML
    private Button loginButton;

    public Stage getHelloStage() {
        return (Stage) loginButton.getScene().getWindow();
    }

    public void openLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Zaloguj się do systemu.");

        LoginController loginController = loader.getController(); // pobieramy instancję kontrolera logowania
        loginController.setHelloController(this);

        stage.show();
    }

    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void onLogin(ActionEvent actionEvent) throws IOException {
        openLoginWindow();
    }
}