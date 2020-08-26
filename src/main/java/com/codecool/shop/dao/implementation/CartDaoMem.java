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
    public String add(Product product, int user_id) {
        if (getSize(user_id) < 10){
            int quantity = Util.productQuantityInCart(product.getId(),user_id, dataSource);
            if(quantity == 0){
                String query = "INSERT INTO cart (user_id, prod_id, prod_quantity) VALUES (?, ?, 1)";
                try(Connection conn = dataSource.getConnection();
                    PreparedStatement statement = conn.prepareStatement(query)){
                    statement.setInt(1, user_id);
                    statement.setInt(2, product.getId());
                    statement.execute();
                } catch (Exception e){
                    System.out.println("In CartDaoMem add: " + e);
                }
            } else {
                update(product, true, user_id);
            }
            return "not full";
        } else {
            return "full";
        }
    }

    @Override
    public void remove(Product product, int user_id) {
        int quantity = Util.productQuantityInCart(product.getId(),user_id, dataSource);
        if (quantity <= 1){
            String query = "DELETE FROM cart WHERE user_id = ? AND prod_id = ?;";
            try(Connection conn = dataSource.getConnection();
                PreparedStatement statement = conn.prepareStatement(query)){
                statement.setInt(1, user_id);
                statement.setInt(2, product.getId());
                statement.execute();
            } catch (Exception e) {
                System.out.println("In CartDaoMem remove: " + e);
            }
        } else {
            update(product, false, user_id);
        }
    }

    @Override
    public void removeByUserId(int id) {
        String query = "DELETE FROM cart WHERE user_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product, boolean plus, int user_id) {
        int quantity = Util.productQuantityInCart(product.getId(), user_id, dataSource);
        String query = "UPDATE cart SET prod_quantity = ? WHERE prod_id = ? AND user_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            if(plus){
                statement.setInt(1, quantity + 1);
            } else {
                statement.setInt(1, quantity - 1);
            }
            statement.setInt(2, product.getId());
            statement.setInt(3, user_id);
            statement.execute();
        } catch (Exception e){
            System.out.println("In CartDaoMem update: " + e);
        }
    }

    @Override
    public Map<Product, Integer> getAll(int user_id) {
        Map<Product, Integer> cartMap = new HashMap<>();
        String query = "SELECT * FROM cart WHERE user_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, user_id);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                cartMap.put(productDaoMem.find(result.getInt("prod_id")),
                        result.getInt("prod_quantity"));
            }
        } catch (Exception e) {
            System.out.println("In CartDaoMem getAll: " + e);
        }
        return cartMap;
    }

    @Override
    public List<Integer> getAllProductIdByUserId(int id) {
        List<Integer> productIds = new ArrayList<>();
        String query = "SELECT prod_id, prod_quantity FROM cart WHERE user_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)
            ){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                int quantity = result.getInt("prod_quantity");
                for (int i = 0; i < quantity; i++) {
                    productIds.add(result.getInt("prod_id"));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return productIds;
    }

    @Override
    public int getSize(int user_id) {
        Map<Product, Integer> products = getAll(user_id);
        int cartSize = 0;
        for (int i: products.values()){
            cartSize += i;
        }
        return cartSize;
    }

    @Override
    public float getFullPrice(int user_id){
        Map<Product, Integer> products = getAll(user_id);
        float fullPrice = 0;
        for (Map.Entry<Product, Integer> entry: products.entrySet()){
            fullPrice += entry.getKey().getFloatPrice() * entry.getValue();
        }
        return fullPrice;
    }

    @Override
    public int getQuantityByProduct(int productid,int userid) {
        return Util.productQuantityInCart(productid,userid, dataSource);
    }
}
