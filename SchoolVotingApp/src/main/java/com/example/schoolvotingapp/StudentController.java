package com.example.schoolvotingapp;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.Candidate;
import model.Student;


public class StudentController {
    public Label studentNameLabel;
    public ComboBox<Candidate> electionComboBox;
    public Button voteButton;
    public TextField resultTextField;
    public Button checkResultsButton;
    public TextArea candidatePostulatesTextArea;
    public Button submitCandidacyButton;

    private Student loggedInStudent;

    public void setLoggedInStudent(Student student) {
        this.loggedInStudent = student;
        studentNameLabel.setText("Witaj " + student.getName() + "!");
    }

    public void onVote(ActionEvent actionEvent) {
    }

    public void onCheckResults(ActionEvent actionEvent) {
    }

    public void onSubmitCandidacy(ActionEvent actionEvent) {
    }
}
