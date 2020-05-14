package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.Map;

public interface CartDao {
    String add(Product product);
    void remove(Product product);
    void update(Product product, boolean plus);
    Map<Product, Integer> getAll();
    int getSize();
    float getFullPrice();
    int getQuantityByProduct(int id);
}
