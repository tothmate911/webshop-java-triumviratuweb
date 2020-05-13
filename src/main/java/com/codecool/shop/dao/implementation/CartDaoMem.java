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
    private DataSource dataSource = DbConnect.getDbConnect().getDataSource();
    private Map<Product, Integer> cartMap = new HashMap<>();
    private static CartDaoMem instance = null;
    private int cartSize = 0;

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        cartMap.put(product, cartMap.getOrDefault(product, 0) + 1);
        cartSize++;
    }

    @Override
    public void remove(Product product) {
        int numberOfProduct = cartMap.getOrDefault(product, 0);

        if (numberOfProduct > 1) {
            cartMap.put(product, numberOfProduct - 1);
        } else {
            cartMap.remove(product);
        }
        cartSize--;
    }

    @Override
    public Map<Product, Integer> getAll() {
        ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
        Map<Product, Integer> cartMap = new HashMap<>();
        String query = "SELECT * FROM cart WHERE cart_is_active = true AND user_id = 1;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery()){
            result.next();
            List<Integer> productIndexes = new ArrayList<>();
            //TODO create stream
            for (int i = 1; i <= 10; i++) {
                productIndexes.add(result.getInt("prod_id" + i));
            }
            productIndexes.removeIf(Objects::isNull);
            Map<Integer, Integer> rawMap = new HashMap<>();
            for (Integer i: productIndexes){
                rawMap.merge(i, 1, Integer::sum);
            }
            for (Integer i: rawMap.keySet()){
                cartMap.put(productDaoMem.find(i), rawMap.get(i));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return cartMap;
    }

    @Override
    public int getSize() {
        return cartSize;
    }

    @Override
    public float getFullPrice(){
        float fullPrice = 0;
        for (Map.Entry<Product, Integer> entry: cartMap.entrySet()){
            fullPrice += entry.getKey().getFloatPrice() * entry.getValue();
        }
        return fullPrice;
    }
}
