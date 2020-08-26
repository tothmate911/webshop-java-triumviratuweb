package com.codecool.shop.controller;

public class Environment {
    public String getDbName() {
        return System.getenv("SQL_DB");
    }
}
