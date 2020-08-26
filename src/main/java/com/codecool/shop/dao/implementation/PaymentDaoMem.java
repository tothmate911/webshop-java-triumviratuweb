package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentDaoMem {
    private final DataSource dataSource = DbConnect.getDbConnect().getDataSource();
    private static PaymentDaoMem instance = null;

    public static PaymentDaoMem getInstance() {
        if (instance == null) {
            instance = new PaymentDaoMem();
        }
        return instance;
    }

    public void add(Integer buyer_id, Timestamp pay_date, String pay_type, float fullPrice, String payCurrency, List<Integer> productIds){
        String query = "INSERT INTO pay (buyer_id, pay_date, pay_type, full_price, pay_currency, prod_id1, prod_id2, prod_id3, prod_id4, prod_id5, prod_id6, prod_id7, prod_id8, prod_id9, prod_id10) VALUES" +
                " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, buyer_id);
            statement.setTimestamp(2, pay_date);
            statement.setString(3, pay_type);
            statement.setFloat(4, fullPrice);
            statement.setString(5, payCurrency);
            if (productIds.get(0) != null){
                statement.setInt(6, productIds.get(0));
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            if (productIds.get(1) != null){
                statement.setInt(7, productIds.get(1));
            } else {
                statement.setNull(7, Types.INTEGER);
            }
            if (productIds.get(2) != null){
                statement.setInt(8, productIds.get(2));
            } else {
                statement.setNull(8, Types.INTEGER);
            }
            if (productIds.get(3) != null){
                statement.setInt(9, productIds.get(3));
            } else {
                statement.setNull(9, Types.INTEGER);
            }
            if (productIds.get(4) != null){
                statement.setInt(10, productIds.get(4));
            } else {
                statement.setNull(10, Types.INTEGER);
            }
            if (productIds.get(5) != null){
                statement.setInt(11, productIds.get(5));
            } else {
                statement.setNull(11, Types.INTEGER);
            }
            if (productIds.get(6) != null){
                statement.setInt(12, productIds.get(6));
            } else {
                statement.setNull(12, Types.INTEGER);
            }
            if (productIds.get(7) != null){
                statement.setInt(13, productIds.get(7));
            } else {
                statement.setNull(13, Types.INTEGER);
            }
            if (productIds.get(8) != null){
                statement.setInt(14, productIds.get(8));
            } else {
                statement.setNull(14, Types.INTEGER);
            }
            if (productIds.get(9) != null){
                statement.setInt(15, productIds.get(9));
            } else {
                statement.setNull(15, Types.INTEGER);
            }
            statement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getPurchaseHistory(int userId) {
        String query = "SELECT * FROM buyer_data\n" +
                "JOIN pay p on buyer_data.buyer_id = p.buyer_id\n" +
                "WHERE user_id = ?;\n";

        List<Map<String, Object>> purchases = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> purchase = new HashMap<>();
                    purchase.put("pay_id", resultSet.getInt("pay_id"));
                    purchase.put("pay_date", resultSet.getDate("pay_date"));
                    purchase.put("pay_type", resultSet.getString("pay_type"));
                    purchase.put("full_price", resultSet.getDouble("full_price"));
                    purchase.put("pay_currency", resultSet.getString("pay_currency"));

                    purchases.add(purchase);
                }
                return purchases;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
