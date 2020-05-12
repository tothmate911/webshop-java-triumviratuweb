package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/add-to-cart/*"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("id"));
        ProductDao productDataStore = ProductDaoMem.getInstance();
        Product product = productDataStore.find(productId);

        CartDao cartDataStore = CartDaoMem.getInstance();
        cartDataStore.add(product);
        try(PrintWriter out = resp.getWriter()){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            JSONObject json = new JSONObject();
            json.append("message", "FINISH");
            out.print(json.toString());
        }

        resp.sendRedirect("/");
    }

}
