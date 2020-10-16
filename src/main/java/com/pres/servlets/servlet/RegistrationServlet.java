package com.pres.servlets.servlet;

import com.pres.database.repository.impl.UserRepository;
import com.pres.exeption.DBException;
import com.pres.model.User;
import com.pres.servlets.ErrorCatchable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet implements ErrorCatchable {
    private static final Logger LOG = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = extractUser(req);
        createUser(req, resp, user);
        req.getRequestDispatcher("/jsp/authorization.jsp").forward(req, resp);
    }

    private User extractUser(HttpServletRequest req) {
        return new User.Builder()
                .setFirstName(req.getParameter("firstName"))
                .setLastName(req.getParameter("lastName"))
                .setPhoneNumber(req.getParameter("phoneNumber"))
                .setLogin(req.getParameter("login"))
                .setPassword(req.getParameter("password"))
                .setPostOffice(Integer.parseInt(req.getParameter("postOffice")))
                .setCity(req.getParameter("city"))
                .build();
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        try {
            UserRepository.getInstance().createUser(user);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }
}
