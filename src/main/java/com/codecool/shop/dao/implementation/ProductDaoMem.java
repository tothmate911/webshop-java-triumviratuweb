package com.codecool.shop.dao.implementation;


import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.controller.Util;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {
    private final DataSource dataSource = DbConnect.getDbConnect().getDataSource();
    private final List<Product> data = new ArrayList<>();
    private static ProductDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoMem() {
    }

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        String query = "INSERT INTO product (prod_name, prod_description, price, currency, image_file, category_id, supplier_id) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?);";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)
            ){
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setFloat(3, product.getDefaultPrice());
            statement.setString(4, product.getDefaultCurrency().toString());
            statement.setString(5, product.getImageFile());
            statement.setInt(6, product.getProductCategory().getId());
            statement.setInt(7, product.getSupplier().getId());
            statement.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Product find(int id) {
        Product product = null;
        String query = "SELECT * FROM product WHERE prod_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            product = Util.createProduct(result);
        }catch (Exception e){
            System.out.println(e);
        }
        return product;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM product WHERE prod_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT product.*, pc.*, ps.* FROM product LEFT JOIN prod_category pc on product.category_id = pc.cat_id LEFT JOIN prod_supplier ps on product.supplier_id = ps.sup_id;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery()){
            while (result.next()){
                Product product = Util.createProduct(result);
                products.add(product);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return products;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE supplier_id = ?;";
        Util.searchProductBySupplierOrCategory(dataSource, query, products, supplier);
        return products;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category_id = ?;";
        Util.searchProductBySupplierOrCategory(dataSource, query, products, productCategory);
        return products;
    }
}
