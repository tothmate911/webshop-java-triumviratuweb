package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoMem implements UserDao {
    private DataSource dataSource;
    private static UserDaoMem userDaoMemInstance;

    private UserDaoMem() {
        dataSource = DbConnect.getDbConnect().getDataSource();
    }

    public UserDaoMem getInstance() {
        if (userDaoMemInstance == null) {
            userDaoMemInstance = new UserDaoMem();
        }
        return userDaoMemInstance;
    }

    @Override
    public void add(User user) {
        String query = "INSERT INTO web_user (user_name, email, hashed_password, user_is_active) " +
                "VALUES (?, ?, ?, FALSE)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmailAddress());
            preparedStatement.setString(3, user.getHashedPassword());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(int id) {
        String query = "SELECT * FROM web_user WHERE user_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
//                    User user = new User();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


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
