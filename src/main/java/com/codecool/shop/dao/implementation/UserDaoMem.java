package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.util.List;

public class UserDaoMem implements UserDao {
    private DbConnect dbConnect = DbConnect.getDbConnect();
    private static UserDaoMem userDaoMemInstance;

    private UserDaoMem() {
    }

    public UserDaoMem getInstance() {
        if (userDaoMemInstance == null) {
            userDaoMemInstance = new UserDaoMem();
        }
        return userDaoMemInstance;
    }

    @Override
    public void add(User user) {
        String query = "INSERT INTO web_user VALUES (?, ?, )";


    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
