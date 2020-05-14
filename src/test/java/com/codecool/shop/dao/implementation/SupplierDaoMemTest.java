package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DbConnect;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierDaoMemTest {
    private SupplierDaoMem supplierDaoMem;

    public SupplierDaoMemTest() {
        this.supplierDaoMem = SupplierDaoMem.getInstance();
    }

    @Test
    public void testFind() {
        Supplier supplier = supplierDaoMem.find(1);
        assertEquals("Fantasy", supplier.getName());
        assertEquals("Only Fantasy", supplier.getDescription());
    }

    @Test
    public void add_newSupplier() {
        Supplier supplier = new Supplier("P&T", "Fantasy weapon dealership");
        supplierDaoMem.add(supplier);
        Supplier foundSupplier = supplierDaoMem.find(3);
        assertEquals("P&T", foundSupplier.getName());
        assertEquals("Fantasy weapon dealership", foundSupplier.getDescription());
    }

    @Test
    public void remove_existingSupplier() {
        Supplier supplier = new Supplier("P&T", "Fantasy weapon dealership");
        supplierDaoMem.add(supplier);
        supplierDaoMem.remove(3);
        assertNull(supplierDaoMem.find(3));
    }

    @Test
    public void testGetAll() {
        List<Supplier> suppliers = supplierDaoMem.getAll();
        assertEquals(2, suppliers.size());
    }

}



