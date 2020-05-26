package com.codecool.shop.controller;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.UserDaoMem;
import com.codecool.shop.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/registration"})
public class Registration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = createUserObject(req);
        UserDao userDataStore = UserDaoMem.getInstance();
        userDataStore.add(user);

        resp.sendRedirect("/");
    }

    private User createUserObject(HttpServletRequest req) {
        String userName = req.getParameter("user_name");
        String emailAddress = req.getParameter("email");
        String hashedPassword = BCrypt.hashpw(req.getParameter("password"), BCrypt.gensalt(userName.charAt(0)));

        return new User(userName, emailAddress, hashedPassword);
    }
}
