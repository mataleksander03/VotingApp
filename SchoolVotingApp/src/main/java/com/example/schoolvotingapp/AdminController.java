package com.example.schoolvotingapp;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.Admin;

public class AdminController {
    public Label welcomeLabel;
    public ComboBox approveCandidatesComboBox;
    public Button approveCandidateButton;
    public TextArea whyMeTextArea;
    public ComboBox rejectCandidatesComboBox;
    public Button rejectCandidateButton;
    public ComboBox moderateCandidatesComboBox;
    public TextArea postulatesTextArea;
    public Button saveChangesButton;
    public Button endElectionsButton;
    public TextField leadingCandidateField;
    public TextField votesCountField;
    public TextField studentsCountField;
    public TextField candidatesCountField;

    private Admin loggedInAdmin;

    public void setLoggedInAdmin(Admin admin){
        this.loggedInAdmin = admin;
        welcomeLabel.setText("Witaj " + admin.getName() + "!");
    }

    public void onChooseCandidateToApporve(ActionEvent actionEvent) {
    }

    public void onApproveChosenCandidate(ActionEvent actionEvent) {
    }

    public void onChooseCandidateToReject(ActionEvent actionEvent) {
    }

    public void onRejectChosenCandidate(ActionEvent actionEvent) {
    }

    public void onChooseToModerateCandidate(ActionEvent actionEvent) {
    }

    public void onSaveChanges(ActionEvent actionEvent) {
    }

    public void onEndElection(ActionEvent actionEvent) {
    }
}
