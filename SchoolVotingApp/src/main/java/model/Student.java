package model;

import database.CandidateDAO;
import database.UserDAO;
import database.VoteDAO;
import java.sql.SQLException;

public class Student extends User {

    public Student(){ // zeby pokazac
        System.out.println("Student constructor");
    }

    public void checkResults(){

    }

    // zwraca kandydata stworzonego na podstawie studenta - w bazie danych status approved to automatycznie 0
    public Candidate submitApplication(String postulates) throws SQLException {
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

    public void vote(int candidateId){
        boolean rowsAffected =  VoteDAO.updateVote(this.getId(), candidateId);
        if(!rowsAffected){
            VoteDAO.addVote(this.getId(), candidateId);
        }
    }
}
