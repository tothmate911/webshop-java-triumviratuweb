package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryType = req.getParameter("category");
        String supplierType = req.getParameter("supplier");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        ProductDao productDataStore = ProductDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDataStore.getAll());

        List<Product> productByCategory = new ArrayList<>();
        List<Product> productBySupplier = new ArrayList<>();

        if (categoryType != null && supplierType != null) {
            if (categoryType.equals("All")) {
                productByCategory = productDataStore.getAll();
            } else {
                productByCategory = productDataStore.getBy(productCategoryDataStore.find(Integer.parseInt(categoryType)));
            }

            if (supplierType.equals("All")) {
                productBySupplier = productDataStore.getAll();
            } else {
                productBySupplier = productDataStore.getBy(supplierDataStore.find(Integer.parseInt(supplierType)));
            }
                productByCategory.retainAll(productBySupplier);
            context.setVariable("products", productByCategory);
        }
        else{
            context.setVariable("products",productDataStore.getAll());
        }
        System.out.println(productByCategory.toString());

        //context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

}
