package com.example.schoolvotingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Candidate;
import model.Student;

import java.io.IOException;
import java.sql.SQLException;


public class StudentController {
    public Label studentNameLabel;
    public ComboBox<Candidate> electionComboBox;
    public Button voteButton;
    public TextField resultTextField;
    public Button checkResultsButton;
    public TextArea candidatePostulatesTextArea;
    public Button submitCandidacyButton;
    public TextArea chosenCandidatePostulateTextArea;

    private Student loggedInStudent;

    public void setLoggedInStudent(Student student) {
        this.loggedInStudent = student;
        studentNameLabel.setText("Witaj " + student.getName() + "!");
    }

    public void onVote(ActionEvent actionEvent) {
    }

    public void onCheckResults(ActionEvent actionEvent) {
    }

    public void onSubmitCandidacy(ActionEvent actionEvent) throws SQLException, IOException {
        String postulates = candidatePostulatesTextArea.getText();
        Candidate loggedInCandidate = loggedInStudent.submitApplication(postulates);
        LoginController loginController = new LoginController();
        loginController.openCandidateWindow(loggedInCandidate);
//        System.out.println(loggenInCandidate.getApproved());
        Stage stage = (Stage) submitCandidacyButton.getScene().getWindow();
        stage.close();
    }
}
