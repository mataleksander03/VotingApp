package com.example.schoolvotingapp;

import database.ElectionDAO;
import database.VoteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Candidate;
import model.Student;
import java.io.IOException;
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
        if(!ElectionDAO.isElectionClosed()) {
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
        } else {
            showError("Wybory są już zakończone!");
        }
    }

    public void onCheckResults(ActionEvent actionEvent) {
        if (ElectionDAO.isElectionClosed()) {
            // Logika do sprawdzania wyników z recyklingiem już stworzonych metod
            String input = VoteDAO.getVoteResults().getFirst();
            // indeks pierwszej spacji
            int startIndex = input.indexOf(" ") + 1;
            // Pobierz 3 znaki po pierwszej spacji (id zwycięzcy)
            String id = input.substring(startIndex, Math.min(startIndex + 3, input.length())); // Zapewnia, że nie przekroczymy długości ciągu znaków
            int CandidateID = Integer.parseInt(id);
            String result = CandidateDAO.getCandidateName(CandidateID);
            resultTextField.setText(result);
        } else {
            showError("Wyniki wyborów będą dostępne po ich zakończeniu.");
        }
    }

    public void onSubmitCandidacy(ActionEvent actionEvent) throws IOException {
        if(!ElectionDAO.isElectionClosed()) {
            if (candidatePostulatesTextArea.getText().isEmpty()) {
                showError("Postulaty nie mogą być puste! Powiedz innym dlaczego powinni na ciebie głosować.");
            } else {
                VoteDAO.deleteVote(loggedInStudent.getId());
                String postulates = candidatePostulatesTextArea.getText();
                Candidate loggedInCandidate = loggedInStudent.submitApplication(postulates);
                LoginController loginController = new LoginController();
                loginController.openCandidateWindow(loggedInCandidate);
                Stage stage = (Stage) submitCandidacyButton.getScene().getWindow();
                stage.close();
            }
        } else {
            showError("Wybory są już zakończone!");
        }
    }

    public void onChooseCandidate(ActionEvent actionEvent) {
        Candidate chosenCandidate = electionComboBox.getValue();
        chosenCandidatePostulateTextArea.setText(chosenCandidate.getPostulates());
    }
}
