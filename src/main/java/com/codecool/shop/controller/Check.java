package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.BuyerDataDaoMem;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.UserDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/check"})
public class Check extends HttpServlet {
    private final CartDao cartDataStore = CartDaoMem.getInstance();
    private final BuyerDataDaoMem buyerDataDaoMem = BuyerDataDaoMem.getInstance();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        String userName = Util.userNameFromSession(session);
        int user_id = Util.userIdByUserName(session);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HashMap<String, Object> buyerData = buyerDataDaoMem.find(user_id);
        if (buyerData.get("buyer_id") != null){
            context.setVariable("fname", buyerData.get("first_name"));
            context.setVariable("lname", buyerData.get("last_name"));
            context.setVariable("phone_number", buyerData.get("phone_number"));
            context.setVariable("email", buyerData.get("email"));
            context.setVariable("billing_address", buyerData.get("billing_address"));
            context.setVariable("shipping_address", buyerData.get("shipping_address"));
        }
        context.setVariable("username",userName);
        context.setVariable("cartList", cartDataStore.getAll(user_id));
        context.setVariable("cartSize", cartDataStore.getSize(user_id));

        engine.process("product/check.html", context, resp.getWriter());

    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        BuyerDataDaoMem buyerDataDaoMem = BuyerDataDaoMem.getInstance();
        HttpSession session = req.getSession(false);
        int user_id = Util.userIdByUserName(session);

        HashMap<String, String> buyerData = new HashMap<>();
        buyerData.put("id", String.valueOf(user_id));
        buyerData.put("fname", req.getParameter("fname"));
        buyerData.put("lname", req.getParameter("lname"));
        buyerData.put("email", req.getParameter("email"));
        buyerData.put("phone_number", req.getParameter("phonenum"));
        buyerData.put("billing_address", req.getParameter("billing_address"));
        buyerData.put("shipping_address", req.getParameter("shipping_address"));

        HashMap<String, Object> buyer = buyerDataDaoMem.find(user_id);
        if (buyer.get("user_id") == null){
            buyerDataDaoMem.add(buyerData);
        } else {
            buyerDataDaoMem.update(buyerData);
        }

        resp.sendRedirect("/buy/?id=" + buyerData.get("id"));
    }
}
