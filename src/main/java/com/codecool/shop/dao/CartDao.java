package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.List;
import java.util.Map;

public interface CartDao {
    String add(Product product, int user_id);
    void remove(Product product, int user_id);
    void removeByUserId(int id);
    void update(Product product, boolean plus, int user_id);
    Map<Product, Integer> getAll(int user_id);
    List<Integer> getAllProductIdByUserId(int id);
    int getSize(int user_id);
    float getFullPrice(int user_id);
    int getQuantityByProduct(int prodid,int userid);
}
