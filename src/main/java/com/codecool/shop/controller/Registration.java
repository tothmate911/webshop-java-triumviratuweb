package com.codecool.shop.controller;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.BuyerDataDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/registration"})
public class Registration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = createUserObject(req);

        UserDao userDataStore = UserDaoMem.getInstance();
        userDataStore.add(user);

//        BuyerDataDaoMem buyerDataStore = BuyerDataDaoMem.getInstance();
//        Map<String, String> buyerData = createBuyerData(req);
//        buyerDataStore.add(buyerData);

        resp.sendRedirect("/");
    }

    private Map<String, String> createBuyerData(HttpServletRequest req) {
        Map<String, String> buyerData = new HashMap<>();
        buyerData.put("first_name", req.getParameter("user_name"));
        buyerData.put("last_name", req.getParameter("user_name"));
        buyerData.put("email", req.getParameter("email"));
        buyerData.put("phone_number", req.getParameter("phone-number"));
        buyerData.put("billing_address", req.getParameter("billing-address"));
        buyerData.put("shipping_address", req.getParameter("shipping-address"));

        return buyerData;
    }

    private User createUserObject(HttpServletRequest req) {
        String userName = req.getParameter("user_name");
        String emailAddress = req.getParameter("email");
        String hashedPassword = BCrypt.hashpw(req.getParameter("password"), BCrypt.gensalt(12));

        return new User(userName, emailAddress, hashedPassword);
    }
}
