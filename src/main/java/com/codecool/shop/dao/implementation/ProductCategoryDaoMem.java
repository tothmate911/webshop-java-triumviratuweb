package com.codecool.shop.dao.implementation;


import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.controller.Util;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoMem implements ProductCategoryDao {
    private final DataSource dataSource = DbConnect.getDbConnect().getDataSource();
    private static ProductCategoryDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoMem() {
    }

    public static ProductCategoryDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoMem();
        }
        return instance;
    }

    @Override
    public void add(ProductCategory category) {
        String query = "INSERT INTO prod_category (cat_name,department,cat_description) VALUES (?,?,?);";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)
        ) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDepartment());
            statement.setString(3, category.getDescription());

            statement.execute();
        } catch (Exception e) {
            System.out.println("In ProductCategoryDaoMem add: " + e);
        }
    }

    @Override
    public ProductCategory find(int id) {
        ProductCategory category = null;
        String query = "SELECT * FROM prod_category WHERE cat_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)
        ){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            category = Util.createProductCategory(result);
        }catch (Exception e){
            System.out.println("In ProductCategoryDaoMem find: " + e);
        }
        return category;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM prod_category WHERE cat_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            System.out.println("In ProductCategoryDaoMem remove: " + e);
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM prod_category;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery()){
            while (result.next()){
                ProductCategory category = Util.createProductCategory(result);
                categories.add(category);
            }
        } catch (Exception e){
            System.out.println("In ProductCategoryDaoMem getAll: " + e);
        }
        return categories;
    }
}
