package com.codecool.shop.model;

public class User {
    private String username;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private int id;

    public User(String username, String emailAddress) {
        this.username = username;
        this.emailAddress = emailAddress;
    }
}
