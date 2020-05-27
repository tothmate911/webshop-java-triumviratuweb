package com.codecool.shop.model;

public class User {
    private String username;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String hashedPassword;
    private Integer id;
    private boolean isActive;

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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
