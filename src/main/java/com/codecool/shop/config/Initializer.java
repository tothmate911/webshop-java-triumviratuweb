package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier fantasy = new Supplier("Fantasy", "Only Fantasy");
        supplierDataStore.add(fantasy);
        Supplier sciFi = new Supplier("Sci-Fi", "Only Sci-fi");
        supplierDataStore.add(sciFi);

        //setting up a new product category
        ProductCategory swords = new ProductCategory("Sword", "Hardware", "Fantasy Swords!");
        productCategoryDataStore.add(swords);
        ProductCategory vehicle = new ProductCategory("Vehicle", "Hardware", "Fantasy vehicles");
        productCategoryDataStore.add(vehicle);
        ProductCategory guns = new ProductCategory("Guns", "Hardware", "Fantasy Guns!");
        productCategoryDataStore.add(guns);
        ProductCategory accessories = new ProductCategory("Accessories", "Hardware", "Fantasy Accessories!");
        productCategoryDataStore.add(accessories);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", swords, fantasy));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", swords, sciFi));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", swords, fantasy));
        productDataStore.add(new Product("Amazon F", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", vehicle, fantasy));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 0", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", vehicle, sciFi));
        productDataStore.add(new Product("Amazon Fire HD", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", vehicle, fantasy));
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", guns, fantasy));
        productDataStore.add(new Product("Len IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", guns, sciFi));
        productDataStore.add(new Product("Aazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", guns, fantasy));
        productDataStore.add(new Product("Amazoe", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", accessories, fantasy));
        productDataStore.add(new Product("Lo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", accessories, sciFi));
        productDataStore.add(new Product("mazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", accessories, fantasy));
    }
}
