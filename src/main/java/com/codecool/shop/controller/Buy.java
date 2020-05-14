package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/buy/*"})
public class Buy extends HttpServlet {
    private final CartDao cartDataStore = CartDaoMem.getInstance();

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
        cartDataStore.removeByUserId(user_id);
//        TODO save into pay table


        resp.sendRedirect("/");
    }
}
