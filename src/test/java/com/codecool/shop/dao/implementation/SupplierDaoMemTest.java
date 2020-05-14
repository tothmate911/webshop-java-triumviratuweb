package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {
    private SupplierDaoMem supplierDaoMem = SupplierDaoMem.getInstance();

    @Test
    public void testFind() {
        SupplierDaoMem supplierDaoMem = SupplierDaoMem.getInstance();
        Supplier supplier = supplierDaoMem.find(1);
        assertEquals("Fantasy", supplier.getName());
        assertEquals("Only Fantasy", supplier.getDescription());
    }

    @Test
    public void testAdd() {
        SupplierDaoMem supplierDaoMem = SupplierDaoMem.getInstance();
        Supplier supplier = new Supplier("P&T", "Fantasy weapon dealership");
        supplierDaoMem.add(supplier);
        Supplier foundSupplier = supplierDaoMem.find(3);
        assertEquals("P&T", foundSupplier.getName());
        assertEquals("Fantasy weapon dealership", foundSupplier.getDescription());
    }

}