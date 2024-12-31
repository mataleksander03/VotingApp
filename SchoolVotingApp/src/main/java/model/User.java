package model;

public abstract class User {
    private int id;
    private String name;
    private String role;

    public User() { // zeby było widać co sie dzieje
        System.out.println("User created");
        System.out.println(id + " " + name + " " + role);
    }

    // Gettery
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // Settery
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


