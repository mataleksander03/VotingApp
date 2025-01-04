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
            if (isRegistered()) {
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
        autoFillCandidateDetails();
    }

    private void autoFillCandidateDetails() {
        if (loggedInCandidate != null) {
            if (loggedInCandidate.getPhoneNr() != null) {
                phoneNumberField.setText(loggedInCandidate.getPhoneNr());
                phoneNumberField.editableProperty().set(false); // gdy dostępne dane brak możliwości edycji w GUI
            }
            if (loggedInCandidate.getEmail() != null) {
                emailField.setText(loggedInCandidate.getEmail());
                emailField.editableProperty().set(false);
            }
            if (loggedInCandidate.getWhyMe() != null) {
                reasonTextArea.setText(loggedInCandidate.getWhyMe());
                reasonTextArea.editableProperty().set(false);
            }
            if (loggedInCandidate.getPostulates() != null) {
                postulatesTextArea.setText(loggedInCandidate.getPostulates());
            }
        }
    }

    private boolean isApproved(){
        int approval = CandidateDAO.getApprovalStatus(loggedInCandidate.getId());
        return approval == 1;
    }

    private boolean isRegistered(){
        return CandidateDAO.getRegistartionStatus(loggedInCandidate.getId()) == 1;
    }

    public void onRegisterCandidacy(ActionEvent actionEvent) {
        if (isRegistered()) {
            showError("Już jesteś kandydatem!");
            return;
        }

        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        String whyMe = reasonTextArea.getText();

        if (phoneNumber.isEmpty() || email.isEmpty() || whyMe.isEmpty()) {
            showError("Proszę uzupełnić wszystkie pola!");
            return;
        }

        CandidateDAO.registerCandidate(loggedInCandidate.getId(), phoneNumber, email, whyMe);
        loggedInCandidate.setPhoneNr(phoneNumber);
        loggedInCandidate.setEmail(email);
        loggedInCandidate.setWhyMe(whyMe);
        updateRegistrationStatus();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces");
        alert.setHeaderText(null);
        alert.setContentText("Rejestracja zakończona sukcesem!");
        alert.showAndWait();
    }

    public void onUpdateInfo(ActionEvent actionEvent) {
        String newPhoneNumber = updatePhoneNumberField.getText();
        String newEmail = updateEmailField.getText();

        if (newPhoneNumber.isEmpty() || newEmail.isEmpty()) {
            showError("Proszę uzupełnić wszystkie pola!");
            return;
        }

        CandidateDAO.registerCandidate(loggedInCandidate.getId(), newPhoneNumber, newEmail, loggedInCandidate.getWhyMe());
        loggedInCandidate.setPhoneNr(newPhoneNumber);
        loggedInCandidate.setEmail(newEmail);
        refresh();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces");
        alert.setHeaderText(null);
        alert.setContentText("Informacje zostały zaktualizowane!");
        alert.showAndWait();
    }

    public void onUpdatePostulates(ActionEvent actionEvent) {
        String newPostulates = postulatesTextArea.getText();
        if (newPostulates.isEmpty()) {
            showError("Postulaty nie mogą być puste! Daj znać innym dlaczego powinni na Ciebie zagłosować.");
        } else {
            loggedInCandidate.updatePostulates(newPostulates);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukces");
            alert.setHeaderText(null);
            alert.setContentText("Postulaty zostały zaktualizowane!");
            alert.showAndWait();
        }
    }

    void refresh(){
        phoneNumberField.setText(loggedInCandidate.getPhoneNr());
        emailField.setText(loggedInCandidate.getEmail());
    }

}
