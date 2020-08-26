package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String userName = Util.userNameFromSession(session);
        int user_id = Util.userIdByUserName(session);
        String categoryType = req.getParameter("category");
        String supplierType = req.getParameter("supplier");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        ProductDao productDataStore = ProductDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        CartDao cart = CartDaoMem.getInstance();

        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDataStore.getAll());

        List<Product> products = null;

        if (categoryType != null && supplierType != null) {
            if (categoryType.equals("All") && supplierType.equals("All")) {
                products = productDataStore.getAll();
            } else if (!categoryType.equals("All") && supplierType.equals("All")) {
                products = productDataStore.getBy(productCategoryDataStore.find(Integer.parseInt(categoryType)));
            } else if (categoryType.equals("All") && !supplierType.equals("All")){
                products = productDataStore.getBy(supplierDataStore.find(Integer.parseInt(supplierType)));
            } else if (!categoryType.equals("All") && !supplierType.equals("All")){
                products = productDataStore.getBy(supplierDataStore.find(Integer.parseInt(supplierType)), productCategoryDataStore.find(Integer.parseInt(categoryType)));
            }
            context.setVariable("products", products);
        } else {
            context.setVariable("products", productDataStore.getAll());
        }

        context.setVariable("username",userName);
        context.setVariable("cartList", cart.getAll(user_id));
        context.setVariable("cartSize", cart.getSize(user_id));
        context.setVariable("cartFullPrice", cart.getFullPrice(user_id));
        context.setVariable("categoryIdFiltered", categoryType != null && !categoryType.equals("All") ? categoryType : -1);
        context.setVariable("supplierIdFiltered", supplierType != null && !supplierType.equals("All") ? supplierType : -1);

        engine.process("product/index.html", context, resp.getWriter());

    }
}
