package model;

import database.CandidateDAO;
import database.UserDAO;
import database.VoteDAO;

public class Student extends User {

    public Student(){ // zeby pokazac
        System.out.println("Student constructor");
    }

    public void checkResults(){

    }

    // zwraca kandydata stworzonego na podstawie studenta - w bazie danych status approved to automatycznie 0
    public Candidate submitApplication(String postulates) {
        UserDAO.updateToCandidate(this.getId());
        CandidateDAO.addCandidate(this.getId(), this.getName(), postulates);
        int approved = CandidateDAO.getApprovalStatus(this.getId());

        Candidate candidate = new Candidate(this.getId());
        candidate.setId(this.getId());
        candidate.setName(this.getName());
        candidate.setRole("CANDIDATE");
        candidate.setPostulates(postulates);
        candidate.setApproved(approved);

        return candidate;
    }

    public boolean vote(int candidateId){
        // Sprawdzenie, czy student już głosował na tego kandydata
        if (VoteDAO.hasVotedForCandidate(this.getId(), candidateId)) {
            return false; // Student już zagłosował na tego kandydata
        }

        // Próba zmiany głosu, jeśli głos już istnieje na innego kandydata
        boolean rowsAffected = VoteDAO.updateVote(this.getId(), candidateId);
        if (!rowsAffected) {
            // Jeśli student nie miał wcześniej głosu, dodaj nowy
            VoteDAO.addVote(this.getId(), candidateId);
        }
        return true; // Głos został oddany lub zaktualizowany
    }
}
