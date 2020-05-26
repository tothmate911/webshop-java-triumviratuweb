package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.BuyerDataDaoMem;
import com.codecool.shop.dao.implementation.CartDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/check"})
public class Check extends HttpServlet {
    private final CartDao cartDataStore = CartDaoMem.getInstance();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//        int user_id = Integer.parseInt(req.getParameter("id"));
        int user_id = 1;

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("user_id", user_id);
        context.setVariable("cartList", cartDataStore.getAll());
        context.setVariable("cartSize", cartDataStore.getSize());

        engine.process("product/check.html", context, resp.getWriter());

    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        BuyerDataDaoMem buyerDataDaoMem = BuyerDataDaoMem.getInstance();

        HashMap<String, String> buyerData = new HashMap<>();
        buyerData.put("id", req.getParameter("user_id"));
        buyerData.put("fname", req.getParameter("fname"));
        buyerData.put("lname", req.getParameter("lname"));
        buyerData.put("email", req.getParameter("email"));
        buyerData.put("phone_number", req.getParameter("phonenum"));
        buyerData.put("billing_address", req.getParameter("billing_address"));
        buyerData.put("shipping_address", req.getParameter("shipping_address"));

        HashMap<String, Object> buyer = buyerDataDaoMem.find(Integer.parseInt(buyerData.get("id")));
        if (buyer.get("user_id") == null){
            buyerDataDaoMem.add(buyerData);
        } else {
            buyerDataDaoMem.update(buyerData);
        }

        resp.sendRedirect("/buy/?id=" + buyerData.get("id"));
    }
}
