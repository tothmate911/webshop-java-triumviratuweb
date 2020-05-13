package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.controller.Util;
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
        if (getSize() < 10){
            int quantity = Util.productQuantityInCart(product.getId(), dataSource);
            if(quantity == 0){
                String query = "INSERT INTO cart (user_id, prod_id, prod_quantity) VALUES (?, ?, 1)";
                try(Connection conn = dataSource.getConnection();
                    PreparedStatement statement = conn.prepareStatement(query)){
                    statement.setInt(1, 1);
                    statement.setInt(2, product.getId());
                    statement.execute();
                } catch (Exception e){
                    System.out.println(e);
                }
            } else {
                update(product, true);
            }
        } else {
            System.out.println("Your cart is Full!");
        }
    }

    @Override
    public void remove(Product product) {
        int quantity = Util.productQuantityInCart(product.getId(), dataSource);
        if (quantity <= 1){
            String query = "DELETE FROM cart WHERE user_id = 1 AND prod_id = ?;";
            try(Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query)){
                statement.setInt(1, product.getId());
                statement.execute();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            update(product, false);
        }
    }

    @Override
    public void update(Product product, boolean plus) {
        int quantity = Util.productQuantityInCart(product.getId(), dataSource);
        String query = "UPDATE cart SET prod_quantity = ? WHERE prod_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            if(plus){
                statement.setInt(1, quantity + 1);
            } else {
                statement.setInt(1, quantity - 1);
            }
            statement.setInt(2, product.getId());
            statement.execute();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public Map<Product, Integer> getAll() {
        Map<Product, Integer> cartMap = new HashMap<>();
        String query = "SELECT * FROM cart WHERE user_id = 1;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery()){
            while (result.next()){
                cartMap.put(productDaoMem.find(result.getInt("prod_id")),
                        result.getInt("prod_quantity"));
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
