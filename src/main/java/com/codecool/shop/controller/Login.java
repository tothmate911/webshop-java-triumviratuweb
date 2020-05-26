package com.codecool.shop.controller;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import org.mindrot.jbcrypt.BCrypt;
import javax.servlet.http.Cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String userName = req.getParameter("user_name");
      if ( BCrypt.checkpw(req.getParameter("password")),){
            Cookie responseCookie = new Cookie("name",userName);
            resp.addCookie(responseCookie);
        }
      else {}


        resp.sendRedirect("/");
    }}