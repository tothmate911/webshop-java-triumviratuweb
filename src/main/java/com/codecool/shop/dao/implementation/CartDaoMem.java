package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartDaoMem implements CartDao {
    private Map<Product, Integer> cartMap = new HashMap<>();
    private static CartDaoMem instance = null;
    private int cartSize = 0;

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

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public Map<Product, Integer> getAll() {
        return cartMap;
    }

    @Override
    public int getSize() {
        return cartSize;
    }

    @Override
    public float getFullPrice(){
        float fullPrice = 0;
        for (Product product: cartMap.keySet()){
            fullPrice = product.getFloatPrice() * cartMap.get(product);
        }
        return fullPrice;
    }
}
