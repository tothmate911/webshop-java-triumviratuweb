package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.BuyerDataDaoMem;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.PaymentDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/userpage"})
public class UserPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        HttpSession session = req.getSession();

        String userName = null;
        if (session.getAttribute("name") != null) {
            userName = session.getAttribute("name").toString();
        }

        UserDao userDataStore = UserDaoMem.getInstance();
        User user = userDataStore.findByName(userName);

        if (user != null) {
            BuyerDataDaoMem buyerDataStore = BuyerDataDaoMem.getInstance();
            Map<String, Object> buyerData = buyerDataStore.find(user.getId());

            PaymentDaoMem paymentDataStore = PaymentDaoMem.getInstance();
            List<Map<String, Object>> purchases = paymentDataStore.getPurchaseHistory(user.getId());

            for (Map<String, Object> purchase : purchases) {
                for (String key : purchase.keySet()) {
                    System.out.println(key + " " + purchase.get(key));
                }
            }

            context.setVariable("username", user.getUsername());
            context.setVariable("email", user.getEmailAddress());

            context.setVariable("fname", buyerData.get("first_name"));
            context.setVariable("lname", buyerData.get("last_name"));
            context.setVariable("phone_number", buyerData.get("phone_number"));
            context.setVariable("email", buyerData.get("email"));
            context.setVariable("billing_address", buyerData.get("billing_address"));
            context.setVariable("shipping_address", buyerData.get("shipping_address"));

            context.setVariable("purchases", purchases);

            CartDao cart = CartDaoMem.getInstance();
            context.setVariable("cartList", cart.getAll(user.getId()));

            engine.process("userpage.html", context, resp.getWriter());
        }
    }
}
