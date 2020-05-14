package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierDaoMemTest {
    private SupplierDaoMem supplierDaoMem = SupplierDaoMem.getInstance();

    @Test
    public void testFind(){
        Supplier supplier = supplierDaoMem.find(1);
        assertEquals("Fantasy", supplier.getName());
        assertEquals("Only Fantasy", supplier.getDescription());
    }

    @Test
    public void testAdd(){
        Supplier supplier = new Supplier("P&T", "Fantasy weapon dealership");
        supplierDaoMem.add(supplier);
        Supplier foundSupplier = supplierDaoMem.find(3);
        assertEquals("P&T", foundSupplier.getName());
        assertEquals("Fantasy weapon dealership", foundSupplier.getDescription());
    }
    @Test
    public void testRemove(){
        SupplierDaoMem supplierDaoMem = SupplierDaoMem.getInstance();
        supplierDaoMem.remove(3);
    }

    @Test
    public void testGetAll(){


    }

}



