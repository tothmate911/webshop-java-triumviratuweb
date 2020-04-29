package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cartEdit/*"})
public class CartEdit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("id"));
        String editType = req.getParameter("type");

        ProductDao productDataStore = ProductDaoMem.getInstance();
        Product product = productDataStore.find(productId);

        CartDao cartDataStore = CartDaoMem.getInstance();

        if (editType.equals("add")) {
            cartDataStore.add(product);
        } else if (editType.equals("remove")) {
            cartDataStore.remove(product);
        }
    }
}
