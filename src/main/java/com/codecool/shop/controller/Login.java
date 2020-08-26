package com.codecool.shop.controller;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.UserDaoMem;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDataStore = UserDaoMem.getInstance();
        WebContext context = new WebContext(req, resp, req.getServletContext());
        String userName = req.getParameter("user_name");
        if (userDataStore.findByName(userName) != null &&
                BCrypt.checkpw(req.getParameter("password"), userDataStore.findByName(userName).getHashedPassword())) {
            HttpSession session = req.getSession();
            session.setAttribute("name", userName);
            context.setVariable("username", userName);
        } else {
            context.setVariable("loginMassage", "Invalid username or password!");
        }
        resp.sendRedirect("/");
    }
}