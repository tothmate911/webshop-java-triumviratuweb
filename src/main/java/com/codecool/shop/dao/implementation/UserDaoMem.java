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

    public static UserDaoMem getInstance() {
        if (userDaoMemInstance == null) {
            userDaoMemInstance = new UserDaoMem();
        }
        return userDaoMemInstance;
    }

    @Override
    public void add(User user) {
        String query = "INSERT INTO web_user (user_name, email, hashed_password, user_is_active) " +
                "VALUES (?, ?, ?, TRUE)";
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
    public User findByName(String username) {
        String query = "SELECT * FROM web_user WHERE user_name = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
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
        String query = "UPDATE web_user\n" +
                "SET user_is_active = FALSE\n" +
                "WHERE user_id = ?;";
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

    @Override
    public Integer getId(String userName) {
        String query = "SELECT user_id FROM web_user WHERE user_name = ?;";
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void upDate(User user) {
        String query = "UPDATE web_user\n" +
                "SET user_name = ?,\n" +
                "    email = ?,\n" +
                "    hashed_password = ?\n" +
                "WHERE user_id = ?;";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmailAddress());
            preparedStatement.setString(3, user.getHashedPassword());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User createUserObject(ResultSet resultSet) throws SQLException {
        User user = new User(resultSet.getString("user_name"),
                resultSet.getString("email"),
                resultSet.getString("hashed_password"));
        user.setId(resultSet.getInt("user_id"));
        return user;
    }
}
