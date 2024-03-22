package com.example.smd_final_project;

public class User {
    String role,cnic,name,password;
    Boolean isVote;

    public User(String role, String cnic, String name, String password, Boolean isVote) {
        this.role = role;
        this.cnic = cnic;
        this.name = name;
        this.password = password;
        this.isVote = isVote;
    }

    public User() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getVote() {
        return isVote;
    }

    public void setVote(Boolean vote) {
        isVote = vote;
    }
}
