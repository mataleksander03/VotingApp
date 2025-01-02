package com.example.schoolvotingapp;

import database.CandidateDAO;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.Candidate;

public class CandidateController {
    public Label welcomeLabel;
    public Label registrationStatusLabel;
    public Label candidateStatusLabel;
    public TextField phoneNumberField;
    public TextField emailField;
    public TextArea reasonTextArea;
    public Button registerButton;
    public TextField updatePhoneNumberField;
    public TextField updateEmailField;
    public Button updateInfoButton;
    public TextArea postulatesTextArea;
    public Button updatePostulatesButton;

    private Candidate loggedInCandidate;

    public void showError(String wiadomosc) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(wiadomosc);
        alert.showAndWait();
    }

    private void updateCandidateStatus() {
        if (loggedInCandidate != null) {
            if (isApproved()) {
                candidateStatusLabel.setText("Status kandydata: Zatwierdzony");
            } else {
                candidateStatusLabel.setText("Status kandydata: Niezatwierdzony");
            }
        }
    }

    private void updateRegistrationStatus() {
        if (loggedInCandidate != null) {
            if (isApproved()) {
                registrationStatusLabel.setText("Status rejestracji: Zarejestrowany");
            } else {
                registrationStatusLabel.setText("Status rejestracji: Niezarejestrowany");
            }
        }
    }

    public void setLoggedInCandidate(Candidate candidate) {
        this.loggedInCandidate = candidate;
        welcomeLabel.setText("Witaj " + candidate.getName() + "!");
        updateCandidateStatus();
        updateRegistrationStatus();

    }

    private boolean isApproved(){
        int approval = CandidateDAO.getApprovalStatus(loggedInCandidate.getId());
        return approval == 1;
    }

    private boolean isRegistered(){
        return CandidateDAO.getRegistartionStatus(loggedInCandidate.getId()) == 1;
    }

    public void onRegisterCandidacy(ActionEvent actionEvent) {

    }

    public void onUpdateInfo(ActionEvent actionEvent) {
    }

    public void onUpdatePostulates(ActionEvent actionEvent) {
    }

    void refresh(){

    }
}
