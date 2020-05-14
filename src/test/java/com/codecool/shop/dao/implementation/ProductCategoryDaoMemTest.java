package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {
    private ProductCategoryDaoMem productCategoryDaoMem;

    public ProductCategoryDaoMemTest() {
        this.productCategoryDaoMem = ProductCategoryDaoMem.getInstance();
    }

    @Test
    public void testFind() {
        ProductCategory productCategory = productCategoryDaoMem.find(1);
        assertEquals("Sword", productCategory.getName());
        assertEquals("Weapon", productCategory.getDepartment());
        assertEquals("Fantasy Swords!", productCategory.getDescription());
    }

    @Test
    public void add_newProductCategory() {
        ProductCategory productCategory = new ProductCategory("pc name", "pc department", "pc description");
        productCategoryDaoMem.add(productCategory);
        ProductCategory foundProductCategory = productCategoryDaoMem.find(5);
        assertEquals("pc name", foundProductCategory.getName());
        assertEquals("pc department", foundProductCategory.getDepartment());
        assertEquals("pc description", foundProductCategory.getDescription());
    }

    @Test
    public void remove_existingSupplier() {
        ProductCategory productCategory = new ProductCategory("pc name", "pc department", "pc description");
        productCategoryDaoMem.add(productCategory);
        productCategoryDaoMem.remove(5);
        assertNull(productCategoryDaoMem.find(5));
    }

    @Test
    public void testGetAllProductionCategory() {
        List<ProductCategory> suppliers = productCategoryDaoMem.getAll();
        assertEquals(4, suppliers.size());
    }

}