package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;

import java.util.List;

public interface UserDao {

    void add(User user);

    User find(int id);

    User findByName(String username);

    void remove(int id);

    List<User> getAll();
}
