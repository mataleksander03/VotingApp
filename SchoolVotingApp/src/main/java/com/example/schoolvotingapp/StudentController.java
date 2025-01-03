package com.example.schoolvotingapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Candidate;
import model.Student;

import java.io.IOException;
import java.sql.SQLException;

import database.CandidateDAO;



public class StudentController {
    public Label studentNameLabel;
    public ComboBox<Candidate> electionComboBox;
    public TextArea chosenCandidatePostulateTextArea;
    public Button voteButton;
    public TextField resultTextField;
    public Button checkResultsButton;
    public TextArea candidatePostulatesTextArea;
    public Button submitCandidacyButton;
    public ObservableList<Candidate> approvedCandidates = FXCollections.observableArrayList();

    private Student loggedInStudent;

    public void setLoggedInStudent(Student student) {
        this.loggedInStudent = student;
        studentNameLabel.setText("Witaj " + student.getName() + "!");
        loadCandidates();
    }

    private void loadCandidates(){
        approvedCandidates.addAll(CandidateDAO.getAllApprovedCandidates());
        ObservableList<Candidate> observableCandidates = FXCollections.observableArrayList(approvedCandidates);
        electionComboBox.setItems(observableCandidates);
    }

    public void showError(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }

    public void showAlert(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }

    public void onVote(ActionEvent actionEvent) {
        Candidate chosenCandidate = electionComboBox.getValue();
        if (chosenCandidate == null) {
            showError("Nie wybrano kandydata!");
            return;
        }

        boolean isVoteChanged = loggedInStudent.vote(chosenCandidate.getId());
        if (isVoteChanged) {
            showAlert("Głos został oddany.");
        } else {
            showError("Już zagłosowałeś na tego kandydata!");
        }
    }

    public void onCheckResults(ActionEvent actionEvent) {
    }

    public void onSubmitCandidacy(ActionEvent actionEvent) throws SQLException, IOException {
        String postulates = candidatePostulatesTextArea.getText();
        Candidate loggedInCandidate = loggedInStudent.submitApplication(postulates);
        LoginController loginController = new LoginController();
        loginController.openCandidateWindow(loggedInCandidate);
//        System.out.println(loggedInCandidate.getApproved());
        Stage stage = (Stage) submitCandidacyButton.getScene().getWindow();
        stage.close();
    }

    public void onChooseCandidate(ActionEvent actionEvent) {
        Candidate chosenCandidate = electionComboBox.getValue();
        chosenCandidatePostulateTextArea.setText(chosenCandidate.getPostulates());
    }
}
