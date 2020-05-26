package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
                    return createUserObject(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM web_user WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM web_user";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                users.add(createUserObject(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User createUserObject(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString("user_name"),
                resultSet.getString("email"),
                resultSet.getString("hashed_password"));
    }
}
