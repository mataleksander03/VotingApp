package model;

import database.VoteDAO;

public class Student extends User {

    public Student(){ // zeby pokazac
        System.out.println("Student constructor");
    }

    public void checkResults(){

    }

    public void submitApplication(){

    }

    public void vote(int candidateId){
        boolean rowsAffected =  VoteDAO.updateVote(this.getId(), candidateId);
        if(!rowsAffected){
            VoteDAO.addVote(this.getId(), candidateId);
        }
    }
}
