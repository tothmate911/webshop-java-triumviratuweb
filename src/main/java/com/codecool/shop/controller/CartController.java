package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.Product;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/add-to-cart/*"})
public class CartController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        CartDao cartDataStore = CartDaoMem.getInstance();
        HttpSession session = req.getSession(false);
        int user_id = Util.userIdByUserName(session);

        JSONObject jsonObject = Util.apiRequestReader(req);

        Product product = productDataStore.find(jsonObject.getInt("id"));
        String cartIsFull = cartDataStore.add(product, user_id);

        try(PrintWriter out = resp.getWriter()){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            JSONObject response_data = new JSONObject();
            response_data.append("status", cartIsFull);
            response_data.append("fullPrice", cartDataStore.getFullPrice(user_id) + " $");
            response_data.append("name", product.getName());
            response_data.append("price", product.getPrice());
            response_data.append("id", product.getId());
            response_data.append("image", product.getImageFile());
            response_data.append("quantity", cartDataStore.getQuantityByProduct(product.getId(),user_id));
            out.print(response_data.toString());
        }
    }
}
