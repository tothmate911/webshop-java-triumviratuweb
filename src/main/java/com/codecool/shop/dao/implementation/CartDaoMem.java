package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class CartDaoMem implements CartDao {
    private final ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
    private final DataSource dataSource = DbConnect.getDbConnect().getDataSource();
    private static CartDaoMem instance = null;

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        if (getAll().size() < 10){
            String query = "INSERT INTO cart (user_id, prod_id, cart_is_active) VALUES (?, ?, true)";
            try(Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query)){
                statement.setInt(1, 1);
                statement.setInt(2, product.getId());
                statement.execute();
            } catch (Exception e){
                System.out.println(e);
            }
        } else {
            System.out.println("Your cart is Full!");
        }
    }

    @Override
    public void remove(Product product) {
        String query = "DELETE FROM cart WHERE user_id = 1 AND prod_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, product.getId());
            statement.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Map<Product, Integer> getAll() {
        Map<Product, Integer> cartMap = new HashMap<>();
        String query = "SELECT * FROM cart WHERE cart_is_active = true AND user_id = 1;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery()){
            Map<Integer, Integer> rawMap = new HashMap<>();
            while (result.next()){
                int prod_id = result.getInt("prod_id");
                rawMap.merge(prod_id, 1, Integer::sum);
            }
            for (int i: rawMap.keySet()){
                Product product = productDaoMem.find(i);
                cartMap.put(product, rawMap.get(i));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return cartMap;
    }

    @Override
    public int getSize() {
        Map<Product, Integer> products = getAll();
        int cartSize = 0;
        for (int i: products.values()){
            cartSize += i;
        }
        return cartSize;
    }

    @Override
    public float getFullPrice(){
        Map<Product, Integer> products = getAll();
        float fullPrice = 0;
        for (Map.Entry<Product, Integer> entry: products.entrySet()){
            fullPrice += entry.getKey().getFloatPrice() * entry.getValue();
        }
        return fullPrice;
    }
}
