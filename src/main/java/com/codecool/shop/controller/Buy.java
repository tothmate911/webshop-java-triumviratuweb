package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.BuyerDataDaoMem;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.PaymentDaoMem;
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
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = {"/buy/*"})
public class Buy extends HttpServlet {
    private final CartDao cartDataStore = CartDaoMem.getInstance();
    private final BuyerDataDaoMem buyerDataDaoMem = BuyerDataDaoMem.getInstance();
    private final PaymentDaoMem paymentDaoMem = PaymentDaoMem.getInstance();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession session = req.getSession(false);
        String userName = Util.userNameFromSession(session);
        int user_id = Util.userIdByUserName(session);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("username",userName);
        context.setVariable("cartList", cartDataStore.getAll(user_id));
        context.setVariable("cartSize", cartDataStore.getSize(user_id));

        engine.process("product/buy.html", context, resp.getWriter());
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException{
        HttpSession session = req.getSession(false);
        int user_id = Util.userIdByUserName(session);

        float fullPrice = cartDataStore.getFullPrice(user_id);
        String payType;
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
        paymentDaoMem.add(user_id, ts, payType, fullPrice, payCurrency, products);
        cartDataStore.removeByUserId(user_id);


        resp.sendRedirect("/");
    }
}
