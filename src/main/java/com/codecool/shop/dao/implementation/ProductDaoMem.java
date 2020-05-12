package com.codecool.shop.dao.implementation;


import com.codecool.shop.controller.DbConnect;
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
    private DataSource dataSource = DbConnect.getDbConnect().getDataSource();
    private List<Product> data = new ArrayList<>();
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
        product.setId(data.size() + 1);
        data.add(product);
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT product.*, pc.*, ps.* FROM product LEFT JOIN prod_category pc on product.category_id = pc.cat_id LEFT JOIN prod_supplier ps on product.supplier_id = ps.sup_id;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery()){
            while (result.next()){
                for (int i = 1; i < result.getMetaData().getColumnCount(); i++){
                    System.out.println(result.getMetaData().getColumnName(i));
                }
                ProductCategory prod_cat = new ProductCategory(result.getString("cat_name"),
                        result.getString("department"),
                        result.getString("cat_description"));
                prod_cat.setId(result.getInt("cat_id"));
                Supplier prod_sup = new Supplier(result.getString("sup_name"),
                        result.getString("sup_description"));
                prod_sup.setId(result.getInt("sup_id"));
                Product product = new Product(result.getString("prod_name"),
                        result.getFloat("price"),
                        result.getString("currency"),
                        result.getString("prod_description"),
                        prod_cat, prod_sup);
                product.setId(result.getInt("prod_id"));
                products.add(product);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return products;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return data.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }
}
