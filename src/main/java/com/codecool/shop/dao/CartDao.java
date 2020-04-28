package com.codecool.shop.dao;

import com.codecool.shop.model.Product;

import java.util.Map;

public interface CartDao {
    void add(Product product);
    void remove(Product product);
    Map<Product, Integer> getAll();
}
