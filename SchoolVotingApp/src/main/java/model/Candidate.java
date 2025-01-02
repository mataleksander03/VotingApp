package model;

public class Candidate extends User {
    private String postulates;
    private int approved; // 0-false, 1-true dla zgodności z DB
    private String phoneNr;
    private String email;
    private String whyMe;

    public Candidate(){
        System.out.println("Candidate Constructor");
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWhy_me(String whyMe) {
        this.whyMe = whyMe;
    }

    public void setPostulates(String postulates) {
        this.postulates = postulates;
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

    public String getWhMe() {
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


}
