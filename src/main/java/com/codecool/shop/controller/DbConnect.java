package com.codecool.shop.controller;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DbConnect {
    private final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    private static final DbConnect dbConnect = new DbConnect();

    private DbConnect(){
        dataSource.setDatabaseName(System.getenv("SQL_DB"));
        dataSource.setUser(System.getenv("SQL_USERNAME"));
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));
        System.out.println("Trying to connect...");
        try{
            dataSource.getConnection().close();
        } catch (Exception exc){
            System.out.println(exc);
        }
        System.out.println("Connection OK");
    }

    public static DbConnect getDbConnect(){
        return dbConnect;
    }

    public DataSource getDataSource(){
        return dataSource;
    }
}
