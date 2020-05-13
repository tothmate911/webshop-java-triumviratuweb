package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.controller.Util;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String query = "INSERT INTO prod_supplier (sup_name,sup_description) VALUES (?, ?);";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)
        ){
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());
            statement.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Supplier find(int id) {
        Supplier supplier = null;
        String query = "SELECT * FROM prod_supplier WHERE sup_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
        ){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            supplier = Util.createSupplier(result);
        }catch (Exception e){
            System.out.println(e);
        }
        return supplier;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM prod_supplier WHERE sup_id = ?;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM prod_supplier;";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery()){
            while (result.next()){
                Supplier supplier = Util.createSupplier(result);
                suppliers.add(supplier);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return suppliers;
    }
}
