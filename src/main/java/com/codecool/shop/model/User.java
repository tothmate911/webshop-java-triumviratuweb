package com.codecool.shop.model;

public class User {
    private String username;
    private String emailAddress;
    private String hashedPassword;
    private Integer id;

    public User(String username, String emailAddress, String hashedPassword) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }


    public int getId() {
        return id;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
