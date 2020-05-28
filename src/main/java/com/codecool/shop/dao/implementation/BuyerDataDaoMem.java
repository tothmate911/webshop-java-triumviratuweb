package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyerDataDaoMem {
    private final DataSource dataSource = DbConnect.getDbConnect().getDataSource();
    private static BuyerDataDaoMem instance = null;

    public static BuyerDataDaoMem getInstance() {
        if (instance == null) {
            instance = new BuyerDataDaoMem();
        }
        return instance;
    }

    public void add(Map<String, String> buyerData) {
        String query = "INSERT INTO buyer_data (user_id, buyer_first_name, buyer_last_name, buyer_email, buyer_phone_number, buyer_billing_address, buyer_shipping_address) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(buyerData.get("id")));
            statement.setString(2, buyerData.get("fname"));
            statement.setString(3, buyerData.get("lname"));
            statement.setString(4, buyerData.get("email"));
            statement.setString(5, buyerData.get("phone_number"));
            statement.setString(6, buyerData.get("billing_address"));
            statement.setString(7, buyerData.get("shipping_address"));
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Object> find(int id) {
        HashMap<String, Object> buyer = new HashMap<>();
        String query = "SELECT * FROM buyer_data WHERE user_id = ?;";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                buyer.put("buyer_id", result.getInt("buyer_id"));
                buyer.put("user_id", result.getInt("user_id"));
                buyer.put("first_name", result.getString("buyer_first_name"));
                buyer.put("last_name", result.getString("buyer_last_name"));
                buyer.put("email", result.getString("buyer_email"));
                buyer.put("phone_number", result.getString("buyer_phone_number"));
                buyer.put("billing_address", result.getString("buyer_billing_address"));
                buyer.put("shipping_address", result.getString("buyer_shipping_address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buyer;
    }

    public void update(Map<String, String> buyerData) {
        String query = "UPDATE buyer_data SET buyer_first_name = ?, buyer_last_name = ?, buyer_email = ?, buyer_phone_number = ?, buyer_billing_address = ?, buyer_shipping_address = ? " +
                "WHERE user_id = ?;";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, buyerData.get("fname"));
            statement.setString(2, buyerData.get("lname"));
            statement.setString(3, buyerData.get("email"));
            statement.setString(4, buyerData.get("phone_number"));
            statement.setString(5, buyerData.get("billing_address"));
            statement.setString(6, buyerData.get("shipping_address"));
            statement.setInt(7, Integer.parseInt(buyerData.get("id")));
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
