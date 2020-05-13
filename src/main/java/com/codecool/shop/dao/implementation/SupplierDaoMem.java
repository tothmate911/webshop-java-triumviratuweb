package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoMem implements SupplierDao {
    private DataSource dataSource = DbConnect.getDbConnect().getDataSource();
    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoMem() {
    }

    public static SupplierDaoMem getInstance() {
        if (instance == null) {
            instance = new SupplierDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        supplier.setId(data.size() + 1);
        data.add(supplier);
    }

    @Override
    public Supplier find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM prod_supplier;";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return suppliers;
    }
}
