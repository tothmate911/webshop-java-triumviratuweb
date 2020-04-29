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
        productDataStore.add(new Product("Kylo Ren Lightsaber", 500f, "USD", "You can be the envoy of the dark side with this beauty!", swords, fantasy));
        productDataStore.add(new Product("Zeldas Master Sword", 99.99f, "USD", "Can't miss sword from your Zelda Collection", swords, fantasy));
        productDataStore.add(new Product("Alduril", 300, "USD", "Be a great king like Aragorn with his magical sword!", swords, fantasy));
        productDataStore.add(new Product("Millenium Falcon", 1000, "USD", "Maybe old, maybe not the prettiest, but sure fly like hell!", vehicle, fantasy));
        productDataStore.add(new Product("Enterprise",980.50f , "USD", "Go boldly where no man has ever gone before with you own spaceship!", vehicle, sciFi));
        productDataStore.add(new Product("Gotterdammerung", 760, "USD", "Don't have to be a dictator to look good in this!", vehicle, sciFi));
        productDataStore.add(new Product("The Good Samaritan", 278, "USD", "One HELL of a kickback!", guns, fantasy));
        productDataStore.add(new Product("Usopp's Kabuto Slingshot", 20, "USD", "There is literally only One Piece we have of this!", guns, fantasy));
        productDataStore.add(new Product("Han Solo's Blaster", 430.50f, "USD", "Easily concealed under the table", guns, fantasy));
        productDataStore.add(new Product("Mario's Hat", 15, "USD", "Accessory for plumbers", accessories, fantasy));
        productDataStore.add(new Product("Xenomorph's Tail", 80, "USD", "Long,nimble,deadly!", accessories, sciFi));
        productDataStore.add(new Product("DragonBall Balls", 700, "USD", "You don't need to search the world for a wish", accessories, fantasy));
    }
}
