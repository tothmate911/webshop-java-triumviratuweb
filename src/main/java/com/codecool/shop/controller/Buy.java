package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.BuyerDataDaoMem;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.PaymentDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = {"/buy/*"})
public class Buy extends HttpServlet {
    private final CartDao cartDataStore = CartDaoMem.getInstance();
    private final BuyerDataDaoMem buyerDataDaoMem = BuyerDataDaoMem.getInstance();
    private final PaymentDaoMem paymentDaoMem = PaymentDaoMem.getInstance();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int user_id = Integer.parseInt(req.getParameter("id"));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("user_id", user_id);
        context.setVariable("cartList", cartDataStore.getAll());
        context.setVariable("cartSize", cartDataStore.getSize());

        engine.process("product/buy.html", context, resp.getWriter());
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int user_id = Integer.parseInt(req.getParameter("user_id"));
        HashMap<String, Object> buyerData = buyerDataDaoMem.find(user_id);
        float fullPrice = cartDataStore.getFullPrice();
        String payType = null;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String payCurrency = "USD";
        List<Integer> products = cartDataStore.getAllProductIdByUserId(user_id);
        while (products.size() != 10){
            products.add(null);
        }
        if (req.getParameter("payPal") != null){
            payType = "payPal";
        } else {
            payType = "credit card";
        }
        paymentDaoMem.add((int) buyerData.get("buyer_id"), ts, payType, fullPrice, payCurrency, products);
        cartDataStore.removeByUserId(user_id);


        resp.sendRedirect("/");
    }
}
