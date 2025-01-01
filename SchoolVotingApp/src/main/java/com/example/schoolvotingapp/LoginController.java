package com.example.schoolvotingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import database.UserDAO;

import java.io.IOException;


public class LoginController {
    @FXML
    private TextField userIdField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;

    private HelloController helloController;


    public void setHelloController(HelloController controller) {
        this.helloController = controller;
    }

    public void openAdminWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Panel administarcyjny");
        stage.show();
    }

    public void openCandidateWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("candidate-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Panel kandydata");
        stage.show();
    }

    public void openStudentWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("student-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Panel studenta");
        stage.show();
    }

    public void closeHelloAndLoginWindow() {
        Stage loginStage = (Stage) loginButton.getScene().getWindow(); // scena jak przycisk :o
        loginStage.close();

        if (helloController != null) {
            Stage helloStage = helloController.getHelloStage(); // pobieramy scene z hello
            helloStage.close();
        }
    }

    public void onHandleLogin(ActionEvent actionEvent) throws IOException {
        String userID = userIdField.getText();
        User user = UserDAO.login(userID);

        if (user != null) {
            switch (user.getRole()) {
                case "ADMIN":
                    openAdminWindow();
                    closeHelloAndLoginWindow();
                    break;
                case "STUDENT":
                    openStudentWindow();
                    closeHelloAndLoginWindow();
                    break;
                case "CANDIDATE":
                    openCandidateWindow();
                    closeHelloAndLoginWindow();
                    break;
                default:
                    System.out.println("Nieznana rola");
            }
        } else {
            System.out.println("Błąd - brak użytkownika");
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void onStudentId(ActionEvent actionEvent) {
    }
}
