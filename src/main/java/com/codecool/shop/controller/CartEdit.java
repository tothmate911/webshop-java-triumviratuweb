package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.json.HTTP;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(urlPatterns = {"/cartEdit/*"})
public class CartEdit extends HttpServlet {
    private final ProductDao productDataStore = ProductDaoMem.getInstance();
    private final CartDao cartDataStore = CartDaoMem.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("id"));
        String editType = req.getParameter("type");

        Product product = productDataStore.find(productId);

        if (editType.equals("add")) {
            cartDataStore.add(product);
        } else if (editType.equals("remove")) {
            cartDataStore.remove(product);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder jb = new StringBuilder();
        String line;
        try(BufferedReader reader = req.getReader()) {
            while((line = reader.readLine()) != null){
                jb.append(line);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        JSONObject jsonObject = new JSONObject(jb.toString());
        int productId = jsonObject.getInt("id");
        String editType = jsonObject.getString("type");

        Product product = productDataStore.find(productId);

        if (editType.equals("add")) {
            cartDataStore.add(product);
        } else if (editType.equals("remove")) {
            cartDataStore.remove(product);
        }

        try(PrintWriter out = resp.getWriter()){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            JSONObject response_data = new JSONObject();
            response_data.append("fullPrice", cartDataStore.getFullPrice() + " $");
            out.print(response_data.toString());
        }
    }
}
