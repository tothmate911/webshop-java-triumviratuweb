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

@WebServlet(urlPatterns = {"/cartEdit/*"})
public class CartEdit extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        CartDao cartDataStore = CartDaoMem.getInstance();

        JSONObject jsonObject = Util.apiRequestReader(req);
        int productId = jsonObject.getInt("id");
        String editType = jsonObject.getString("type");

        Product product = productDataStore.find(productId);
        String cartIsFull = null;
        if (editType.equals("add")) {
            cartIsFull = cartDataStore.add(product);
        } else if (editType.equals("remove")) {
            cartDataStore.remove(product);
        }
        try(PrintWriter out = resp.getWriter()){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            JSONObject response_data = new JSONObject();
            response_data.append("id", product.getId());
            response_data.append("status", cartIsFull);
            response_data.append("fullPrice", cartDataStore.getFullPrice() + " $");
            out.print(response_data.toString());
        }
    }
}
