package com.codecool.shop.controller;

import com.codecool.shop.model.BaseModel;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class Util {
    public static ProductCategory createProductCategory(ResultSet result) throws SQLException {
        ProductCategory prodCat = new ProductCategory(result.getString("cat_name"),
                result.getString("department"),
                result.getString("cat_description"));
        prodCat.setId(result.getInt("cat_id"));
        return prodCat;
    }

    public static Supplier createSupplier(ResultSet result) throws SQLException {
        Supplier prod_sup = new Supplier(result.getString("sup_name"),
                result.getString("sup_description"));
        prod_sup.setId(result.getInt("sup_id"));
        return prod_sup;
    }

    public static Product createProduct(ResultSet result) throws SQLException {
        ProductCategory prodCat = createProductCategory(result);
        Supplier supplier = createSupplier(result);
        Product product = new Product(result.getString("prod_name"),
                result.getFloat("price"),
                result.getString("currency"),
                result.getString("prod_description"),
                prodCat, supplier);
        product.setImageFile(result.getString("image_file"));
        product.setId(result.getInt("prod_id"));
        return product;
    }

    public static void searchProductBySupplierOrCategory(DataSource dataSource, String query, List<Product> products, BaseModel object) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, object.getId());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = Util.createProduct(result);
                products.add(product);
            }
        } catch (Exception e) {
            System.out.println("In Util serchProductBySupplierOrCategory: " + e);
        }
    }

    public static int productQuantityInCart(int id, DataSource dataSource) {
        String query = "SELECT prod_quantity FROM cart WHERE prod_id = ? AND user_id = 1;";
        int quantity = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()){
                quantity = result.getInt("prod_quantity");
            }
        } catch (Exception e) {
            System.out.println("In Util productQuantityInCart: " + e);
        }
        return quantity;
    }

    public static JSONObject apiRequestReader(HttpServletRequest req){
        StringBuilder jb = new StringBuilder();
        String line;
        try(BufferedReader reader = req.getReader()) {
            while((line = reader.readLine()) != null){
                jb.append(line);
            }
        } catch (Exception e){
            System.out.println("In Util apiRequestReader: " + e);
        }
        return new JSONObject(jb.toString());
    }
}
