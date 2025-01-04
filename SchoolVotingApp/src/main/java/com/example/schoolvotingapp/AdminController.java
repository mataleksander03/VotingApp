package com.example.schoolvotingapp;

import database.ElectionDAO;
import database.VoteDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.Admin;

import java.util.List;

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

    private void setLeadingCandidateField(){
        List<String> candidates = VoteDAO.getVoteResults();
        leadingCandidateField.setText(candidates.getFirst()); //tylko pierwszy
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
        ElectionDAO.closeElection();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wybory zakończone");
        alert.setHeaderText(null);
        alert.setContentText("Wybory są zakończone. Wyniki są dostępne dla studentów.");
        alert.showAndWait();
    }
}
