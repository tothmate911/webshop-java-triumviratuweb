package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public void add(Map<String, String> buyerData){
        String query = "INSERT INTO buyer_data (user_id, buyer_first_name, buyer_last_name, buyer_email, buyer_phone_number, buyer_billing_address, buyer_shipping_address) VALUES " +
                "(?, ?, ?, ?, ?, ?);";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, Integer.parseInt(buyerData.get("id")));
            statement.setString(2, buyerData.get("first_name"));
            statement.setString(3, buyerData.get("last_name"));
            statement.setString(4, buyerData.get("email"));
            statement.setString(5, buyerData.get("phone_number"));
            statement.setString(6, buyerData.get("billing_address"));
            statement.setString(7, buyerData.get("shipping_address"));
            statement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    public HashMap<String, String> find(int id){
//        String query = "SELECT * FROM buyer_data WHERE user_id = ?;";
//        try(Connection conn = dataSource.getConnection();
//            PreparedStatement statement = conn.prepareStatement(query)){
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
