package com.pres.servlets.servlet;

import com.pres.database.repository.impl.UserRepository;
import com.pres.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        User user = new User.Builder()
                .setFirstName(req.getParameter("firstName"))
                .setLastName(req.getParameter("lastName"))
                .setPhoneNumber(req.getParameter("phoneNumber"))
                .setLogin(req.getParameter("login"))
                .setPassword(req.getParameter("password"))
                .setPostOffice(Integer.parseInt(req.getParameter("postOffice")))
                .setCity(req.getParameter("city"))
                .build();

        UserRepository.getInstance().createUser(user);
        req.getRequestDispatcher("/view/authorization.jsp").forward(req, resp);

    }
}
