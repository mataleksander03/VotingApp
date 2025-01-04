package com.example.schoolvotingapp;

import database.CandidateDAO;
import database.ElectionDAO;
import database.UserDAO;
import database.VoteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.Admin;
import model.Candidate;

import java.util.List;

public class AdminController {
    public Label welcomeLabel;
    public ComboBox<Candidate> approveCandidatesComboBox;
    public Button approveCandidateButton;
    public TextArea whyMeTextArea;
    public ComboBox<Candidate> rejectCandidatesComboBox;
    public Button rejectCandidateButton;
    public ComboBox<Candidate> moderateCandidatesComboBox;
    public TextArea postulatesTextArea;
    public Button saveChangesButton;
    public Button endElectionsButton;
    public TextField votesCountField;
    public TextField studentsCountField;
    public TextField candidatesCountField;
    public TextArea leadingCandidateTextArea;
    public ObservableList<Candidate> notYetApprovedCandidates = FXCollections.observableArrayList();
    public ObservableList<Candidate> allCandidates = FXCollections.observableArrayList();

    private Admin loggedInAdmin;

    public void setLoggedInAdmin(Admin admin){
        this.loggedInAdmin = admin;
        welcomeLabel.setText("Witaj " + admin.getName() + "!");
        updateAdminPanel();
//        loadCandidates();
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

    private void updateAdminPanel(){
        postulatesTextArea.clear();
        setLeadingCandidateField();
        setApprovedCandidatesCountField();
        setVotesCountField();
        setStudentsCountField();
        loadCandidates();
    }

    private void loadCandidates(){
        notYetApprovedCandidates.clear();
        allCandidates.clear();

        List<Candidate> yetToBetApprovedCandidates = CandidateDAO.getAllNotYetApprovedCandidates();
        // Zeby tylko kandydaci zarejestrowani moggli zostac zatwierdzeni
        for (Candidate candidate : yetToBetApprovedCandidates) {
            if (candidate.isRegistered() == 1) { // Sprawdzenie statusu rejestracji
                notYetApprovedCandidates.add(candidate); // Dodanie do listy
            }
        }
        approveCandidatesComboBox.setItems(notYetApprovedCandidates);
        allCandidates.addAll(CandidateDAO.getAllApprovedCandidates());
        allCandidates.addAll(yetToBetApprovedCandidates);
        rejectCandidatesComboBox.setItems(allCandidates);
        moderateCandidatesComboBox.setItems(allCandidates);
    }

    private void setLeadingCandidateField(){
        List<String> candidates = VoteDAO.getVoteResults();
        leadingCandidateTextArea.setText(String.valueOf(candidates));
    }

    private void setStudentsCountField(){
        int students = UserDAO.getStudentsAndCandidatesCount();
        studentsCountField.setText(String.valueOf(students));
    }

    private void setApprovedCandidatesCountField(){
        int approvedCandidates = CandidateDAO.getAllApprovedCandidates().size();
        candidatesCountField.setText(String.valueOf(approvedCandidates));
    }

    private void setVotesCountField(){
        int votesCount = VoteDAO.getTotalVotesCount();
        votesCountField.setText(String.valueOf(votesCount));
    }

    public void onChooseCandidateToApporve(ActionEvent actionEvent) {
        if(approveCandidatesComboBox.getSelectionModel().getSelectedItem() != null){
            Candidate chosenToApproveCandidate = approveCandidatesComboBox.getValue();
            whyMeTextArea.setText(chosenToApproveCandidate.getWhyMe());
        }
    }

    public void onApproveChosenCandidate(ActionEvent actionEvent) {
        if(!ElectionDAO.isElectionClosed()) {
            if (approveCandidatesComboBox.getSelectionModel().getSelectedItem() != null) {
                Candidate chosenToApproveCandidate = approveCandidatesComboBox.getValue();
                chosenToApproveCandidate.approveCandidate();
                whyMeTextArea.clear();
                updateAdminPanel();
            }
        } else {
            showError("Wybory są już zakończone");
        }
    }

    public void onChooseCandidateToReject(ActionEvent actionEvent) {
    }

    public void onRejectChosenCandidate(ActionEvent actionEvent) {
        if(!ElectionDAO.isElectionClosed()) {
            if (rejectCandidatesComboBox.getSelectionModel().getSelectedItem() != null) {
                Candidate chosenToRejectCandidate = rejectCandidatesComboBox.getValue();
                chosenToRejectCandidate.deleteCandidate();
                updateAdminPanel();
            }
        } else {
            showError("Wybory są już zakończone");
        }
    }

    public void onChooseToModerateCandidate(ActionEvent actionEvent) {
        if(moderateCandidatesComboBox.getSelectionModel().getSelectedItem() != null) {
            Candidate chosenToModerateCandidate = moderateCandidatesComboBox.getValue();
            String postulates = chosenToModerateCandidate.getPostulates();
            postulatesTextArea.setText(postulates);
        }
    }

    public void onSaveChanges(ActionEvent actionEvent) {
        if(!ElectionDAO.isElectionClosed()) {
            if (moderateCandidatesComboBox.getSelectionModel().getSelectedItem() != null) {
                Candidate chosenToModerateCandidate = moderateCandidatesComboBox.getValue();
                if (!postulatesTextArea.getText().isEmpty()) {
                    chosenToModerateCandidate.updatePostulates(postulatesTextArea.getText());
                    showAlert("Postulaty zostały zaaktualizowane.");
                } else {
                    showError("Postulty nie mogą być puste!");
                }
            } else {
                showError("Nie wybrałeś kandydata do moderacji!");
            }
        } else {
            showError("Wybrory są już zakończone");
        }
    }

    public void onEndElection(ActionEvent actionEvent) {
        ElectionDAO.closeElection();
        showAlert("Wybory są zakończone. Wyniki są dostępne dla studentów.");
    }
}
