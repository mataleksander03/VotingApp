package model;

import database.CandidateDAO;

public class Candidate extends User {
    private String postulates;
    private int approved; // 0-false, 1-true dla zgodności z DB
    private String phoneNr;
    private String email;
    private String whyMe;

    public Candidate(int id){
        System.out.println("Candidate Constructor");
        this.setId(id);
        loadCandidateDetails();
    }

    private void loadCandidateDetails() {
        String[] details = CandidateDAO.getCandidateDetails(this.getId());
        this.phoneNr = details[0];
        this.email = details[1];
        this.whyMe = details[2];
        this.postulates = details[3];
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWhyMe(String whyMe) {
        this.whyMe = whyMe;
    }

    public void setPostulates(String postulates) {
        this.postulates = postulates;
    }

    public void updatePostulates(String postulates) {
        CandidateDAO.updatePostulates(getId(), postulates);
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public String getEmail() {
        return email;
    }

    public String getWhyMe() {
        return whyMe;
    }

    public String getPostulates() {
        return postulates;
    }

    public Integer getApproved() {
        return approved;
    }

    public void approveCandidate() {

    }

    public void deleteCandidate() {

    }

    @Override
    public String toString() {
        return getName();
    }
}
